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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

import impl.underdark.logging.Logger;
import io.underdark.BuildConfig;
import io.underdark.util.dispatch.DispatchQueue;

public class WifiDetector {
    public interface Listener {
        void onWifiEnabled(InetAddress address);

        void onWifiDisabled();

        void onHotspotEnabled(InetAddress address);

        void onHotspotDisabled();
    }

    private boolean running;
    private Listener listener;
    private DispatchQueue queue;
    private Context context;

    private boolean connected;
    private BroadcastReceiver receiver;
    private final String LOCAL_IP_FIRST_PORTION = "/192";

    public WifiDetector(Listener listener, DispatchQueue queue, Context context) {
        this.listener = listener;
        this.queue = queue;
        this.context = context.getApplicationContext();
    }

    public void start() {
        if (running)
            return;

        running = true;
        Logger.debug("WifiDetector Started ");
        if (isConnectedViaWifi()|| isWifiConnected()) {

            final InetAddress address = determineAddress();

            if (address != null) {
                connected = true;
                queue.dispatch(new Runnable() {
                    @Override
                    public void run() {
                        listener.onWifiEnabled(address);
                    }
                });
            }
        } // if
        else if (isHotspotEnabled()) {

            InetAddress tempAddress = null;

            final InetAddress myAddress = BnjUtil.getMyDeviceInetAddress(true);

            if(myAddress != null && myAddress.toString().contains(LOCAL_IP_FIRST_PORTION)){
                tempAddress = myAddress;
            }else{
                tempAddress =  BnjUtil.getLocalIpAddress();;
            }

            final  InetAddress finalAddress = tempAddress;

            Logger.debug("isHotspotEnabled in Address " + myAddress);
            if (finalAddress != null) {
                connected = true;
                queue.dispatch(new Runnable() {
                    @Override
                    public void run() {
                        listener.onHotspotEnabled(finalAddress);
                    }
                });
            }
        }//End hotspot region

        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                WifiDetector.this.onReceive(context, intent);
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
        context.registerReceiver(receiver, intentFilter, null, queue.getHandler());
    } // start()

    public void stop() {
        if (!running)
            return;

        running = false;

        context.unregisterReceiver(receiver);
        receiver = null;
    } // stop

    private boolean isConnectedViaWifi() {
       ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        //Logger.debug("wifi isConnectedViaWifi() {}", info.isConnected());
        return info.isConnected();
    }


    private boolean isWifiConnected() {

        if (context == null) return false;

        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if (wifiInfo.getNetworkId() == -1) {
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        } else {
            return false;  // Wi-Fi adapter is OFF
        }
    }

    //Returning true if hotspot is enabled. #MITHUN#
    private boolean isHotspotEnabled() {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();
        for (Method method : wmMethods) {
            if (method.getName().equals("isWifiApEnabled")) {
                try {
                    return (boolean) method.invoke(wifi);
                } catch (IllegalArgumentException e) {
                    logDark("Wifi Detector IllegalArgumentException " + e.getMessage());
                } catch (IllegalAccessException e) {
                    logDark("Wifi Detector IllegalAccessException " + e.getMessage());
                } catch (InvocationTargetException e) {
                    logDark("Wifi Detector InvocationTargetException " + e.getMessage());
                }
            }
        }
        return false;
    }

    private void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            onReceive_NETWORK_STATE_CHANGED_ACTION(context, intent);
        } else if (action.equals("android.net.wifi.WIFI_AP_STATE_CHANGED")) {
            onReceive_NETWORK_AP_STATE_CHANGED_ACTION(context, intent);
        }

    }

    private void onReceive_NETWORK_STATE_CHANGED_ACTION(Context context, Intent intent) {
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        Logger.debug("-----STATE_CHANGED Fired");



        if (NetworkInfo.State.CONNECTED.equals(info.getState())) {
            // Wi-Fi connection established
            //Logger.debug("wifi NETWORK_STATE_CHANGED_ACTION connected");

            if (connected)
                return;

            final InetAddress address = determineAddress();

            if (address == null)
                return;

            this.connected = true;

            queue.dispatch(new Runnable() {
                @Override
                public void run() {
                    Logger.debug("WifiDetector onWifiEnabled(address) Fired");
                    listener.onWifiEnabled(address);
                }
            });

            return;
        }

        if (NetworkInfo.State.DISCONNECTED.equals(info.getState())) {
            // Wi-Fi connection was lost
            //Logger.debug("wifi NETWORK_STATE_CHANGED_ACTION disconnected");

            if (!connected)
                return;

            this.connected = false;

            queue.dispatch(new Runnable() {
                @Override
                public void run() {
                    Logger.debug("WifiDetector onWifiDisabled() Fired");
                    listener.onWifiDisabled();
                }
            });
        }
    } // onReceive_SUPPLICANT_CONNECTION_CHANGE_ACTION()

    private void onReceive_NETWORK_AP_STATE_CHANGED_ACTION(Context context, Intent intent) {

        Logger.debug(">>>>>>>AP_STATE_CHANGED Fired");

        if (isConnectedViaWifi() || isWifiConnected()) return;

        int apState = intent.getIntExtra("wifi_state", 0);
        if (apState == 13) {
            if (connected)
                return;

            InetAddress tempAddress = null;

            final InetAddress myAddress = BnjUtil.getMyDeviceInetAddress(true);

            if(myAddress != null && myAddress.toString().contains(LOCAL_IP_FIRST_PORTION)){
                tempAddress = myAddress;
            }else{
                tempAddress =  BnjUtil.getLocalIpAddress();;
            }

            final  InetAddress finalAddress = tempAddress;

            if (finalAddress == null)
                return;

            this.connected = true;

            queue.dispatch(new Runnable() {
                @Override
                public void run() {
                    Logger.debug("WifiDetector onHotspotEnabled(address) Fired");
                    listener.onHotspotEnabled(finalAddress);
                }
            });

            return;
        } else {
            if (!connected)
                return;

            this.connected = false;

            queue.dispatch(new Runnable() {
                @Override
                public void run() {
                    Logger.debug("WifiDetector onHotspotDisabled() Fired");
                    listener.onHotspotDisabled();
                }
            });
        }
    }

    private InetAddress determineAddress() {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        if (wifiInfo == null)
            return null;

        String hostname = Formatter.formatIpAddress(wifiInfo.getIpAddress());
        //byte[] byteaddr = new byte[] {
        // (byte) (intaddr & 0xff),
        // (byte) (intaddr >> 8 & 0xff),
        // (byte) (intaddr >> 16 & 0xff),
        // (byte) (intaddr >> 24 & 0xff) };

        final InetAddress address;

        try {
            address = InetAddress.getByName(hostname);
        } catch (UnknownHostException ex) {
            Logger.error("jmd failed to get local address.");
            return null;
        }

        return address;
    } // determineAddress()

    private static final String TAG_DARK = "underdark";

    public static void logDark(String log) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG_DARK, log);
        }

        //System.out.println(TAG_DARK + " > " + log);
    }
} // WifiDetector
