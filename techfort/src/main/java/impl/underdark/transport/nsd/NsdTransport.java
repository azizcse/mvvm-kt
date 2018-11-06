/*
 * Copyright (c) 2016 Vladimir L. Shabanov <virlof@gmail.com>
 *
 * Licensed under the Underdark License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://underdark.io/LICENSE.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package impl.underdark.transport.nsd;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import impl.underdark.logging.Logger;
import impl.underdark.transport.nsd.manager.BonjourResolver;
import impl.underdark.transport.nsd.manager.JmdResolver;
import io.underdark.BuildConfig;
import io.underdark.transport.Transport;
import io.underdark.transport.TransportListener;
import io.underdark.util.dispatch.DispatchQueue;

public class NsdTransport implements
        Transport, WifiDetector.Listener, BonjourResolver.Listener, NsdServer.Listener {


    enum Mode {NONE, WIFI, HOTSPOT}

    Mode mode = Mode.NONE;

    private final int appId;
    private final long nodeId;
    private final String nodeJson;
    private TransportListener listener;
    private DispatchQueue listenerQueue;
    private Context context;

    private boolean running;
    private boolean restarting;
    private DispatchQueue queue;

    private String serviceType;
    private WifiDetector wifiDetector;
    private BonjourResolver bonjourResolver;
    private NsdServer server;

    private List<NsdLink> links = new ArrayList<>();

    private WifiManager.WifiLock wifiLock;

    public NsdTransport(
            int appId,
            long nodeId,
            String nodeJson,
            TransportListener listener,
            DispatchQueue listenerQueue,
            Context context
    ) {
        this.queue = new DispatchQueue();

        this.appId = appId;
        this.nodeId = nodeId;
        this.nodeJson = nodeJson;
        this.listener = listener;
        this.listenerQueue = listenerQueue;
        this.context = context.getApplicationContext();

        serviceType = "_mesh" + appId + "._tcp.";

        wifiDetector = new WifiDetector(this, queue, context);

        bonjourResolver = new JmdResolver(
                serviceType,
                Long.toString(nodeId),
                nodeJson,
                this,
                queue,
                context);

        server = new NsdServer(nodeId, nodeJson, this, queue);
    } // BnjTransport()

    //region Transport
    @Override
    public void start() {
        queue.dispatch(new Runnable() {
            @Override
            public void run() {
                startInternal();
            }
        });
    }

    @Override
    public void stop() {
        queue.dispatch(new Runnable() {
            @Override
            public void run() {
                stopInternal();
            }
        });
    }

    @Override
    public void forceStop() {
        queue.dispatch(new Runnable() {
            @Override
            public void run() {
                forceStopInternal();
            }
        });
    }

    @Override
    public void restart() {
        queue.dispatch(new Runnable() {
            @Override
            public void run() {
                restartInternal();
            }
        });
    }

    private void startInternal() {
        Logger.debug("NsdTransport StartInternal: " + running);
        if (running)
            return;

        running = true;

		/*WifiManager wMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiLock = wMgr.createWifiLock(WifiManager.WIFI_MODE_FULL, "UnderdarkWifiLock");
		wifiLock.acquire();*/

        wifiDetector.start();
    } // startInternal()

    private void stopInternal() {
        if (!running)
            return;

        running = false;

        wifiDetector.stop();

        //wifiLock.release();
    } // stopInternal()


    private void forceStopInternal() {
        Logger.debug("NsdTransport ForceStopInternal: " + running);
        if (!running)
            return;

        running = false;

        wifiDetector.stop();

        if (mode == Mode.NONE || mode == Mode.WIFI) {
            onWifiDisabled();
        } else if (mode == Mode.HOTSPOT) {
            onHotspotDisabled();
        }

        //wifiLock.release();
    }

    private void restartInternal() {
        Logger.debug("NsdTransport Restarting " + restarting);

        if (restarting) {
           return;
        }

        restarting = true;

        wifiDetector.stop();
        if (mode == Mode.NONE || mode == Mode.WIFI) {
            onWifiDisabled();
        } else if (mode == Mode.HOTSPOT) {
            onHotspotDisabled();
        }

        /*if (!running) {
            running = true;

            wifiDetector.start();
        }*/


    }

    @Override
    public void onMainActivityResumed() {

    }

    @Override
    public void onMainActivityPaused() {

    }
    //endregion

    //region WifiDetector.Listener
    @Override
    public void onWifiEnabled(InetAddress address) {
        Logger.debug("bnj wifi enabled {}", address.toString());


        if (mode == Mode.HOTSPOT) {
            onWifiDisabled();
        }

        server.startAccepting(address);
        mode = Mode.WIFI;

    } // onWifiEnabled

    @Override
    public void onWifiDisabled() {
        Logger.debug("WiFi Disabled");

        server.stopAccepting();
        bonjourResolver.stop();
        mode = Mode.NONE;

        if (restarting) {
            restarting = false;
            wifiDetector.start();
        }
    } // onWifiDisabled

    @Override
    public void onHotspotEnabled(InetAddress address) {
        if (mode == Mode.WIFI) {
            onHotspotDisabled();
        }

        server.startAccepting(address);
        mode = Mode.HOTSPOT;
    }

    @Override
    public void onHotspotDisabled() {
        Logger.debug("Hotspot Disabled");

        server.stopAccepting();
        bonjourResolver.stop();
        mode = Mode.NONE;

        if (restarting) {
            restarting = false;
            wifiDetector.start();
        }
    }
    //endregion

    //region BonjourResolver
    @Override
    public void onBonjourServiceResolved(String name, String address, int port) {
        if (!running)
            return;

        Logger.debug("bnj service resolved '{}' {}:{}", name, address, port);

        Logger.debug("To Resolve Node > " + name);

        try {
            long linkNodeId = Long.parseLong(name);

            for (NsdLink link : links) {
                if (link.getNodeId() == linkNodeId)
                    return;
            }

            //   logDark("Resolved Node > " + name);

            server.connect(linkNodeId, InetAddress.getByName(address), port);
        } catch (NumberFormatException ex) {
            Logger.error("bnj failed to parse link nodeId '{}'", name);
            return;
        } catch (UnknownHostException ex) {
            Logger.error("bnj failed to parse link address '{}'", address);
            return;
        }
    }
    //endregion

    //region BnjServer.Listener
    @Override
    public void onServerAccepting(InetAddress address, int port) {
        if (!running)
            return;

        bonjourResolver.start(address, port);
        //bonjourResolver.startPublishOnly(address, port);
        //bonjourResolver.startResolveOnly(address);
    }

    @Override
    public void onServerError(InetAddress address) {
        if (!running)
            return;

        bonjourResolver.startResolveOnly(address);
    }

    @Override
    public void linkConnected(final NsdLink link) {
        // Queue.
        if (!running) {
            link.disconnect();
            return;
        }

        links.add(link);

        listenerQueue.dispatch(new Runnable() {
            @Override
            public void run() {
                listener.transportLinkConnected(NsdTransport.this, link);
            }
        });
    }

    @Override
    public void linkDisconnected(final NsdLink link) {
        // Queue.
        links.remove(link);

        listenerQueue.dispatch(new Runnable() {
            @Override
            public void run() {
                listener.transportLinkDisconnected(NsdTransport.this, link);
            }
        });
    }

    @Override
    public void linkDidReceiveFrame(final NsdLink link, final byte[] frameData) {
        // Queue.
        if (!running)
            return;

        listenerQueue.dispatch(new Runnable() {
            @Override
            public void run() {
                listener.transportLinkDidReceiveFrame(NsdTransport.this, link, frameData);
            }
        });
    }

    private static final String TAG_DARK = "LogDark";

    public static void logDark(String log) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG_DARK, log);
        }

        //System.out.println(TAG_DARK + " > " + log);
    }

    //endregion
} // NsdTransport
