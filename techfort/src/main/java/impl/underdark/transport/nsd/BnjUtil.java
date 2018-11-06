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

import android.util.Log;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import impl.underdark.logging.Logger;

public class BnjUtil {

    public static InetAddress getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                     enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (inetAddress.isLoopbackAddress())
                        continue;

                    /*if (!InetAddressUtils.isIPv4Address(inetAddress.getHostAddress()))
                        continue;*/

                    //if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress() ) {
                    //if (!inetAddress.isLoopbackAddress()
                    //		&& InetAddressUtils.isIPv4Address(inetAddress.getHostAddress()) )

                    return inetAddress;
                } // for
            } // for
        } catch (SocketException ex) {
            Logger.error("nsd server failed to get local address");
        }
        return null;
    } // getLocalIpAddress()


    /**
     * This method generate InetAddress for both GETWAY and DEVICE
     * When we need to get device Inetaddress just pass isLocalAddress is true,
     *
     * @param isLocalAddress (required true for Device InetAddress and false for Getway InetAddress)
     * @return InetAddress
     */
    public static InetAddress getMyDeviceInetAddress(boolean isLocalAddress) {
        String my_ip = null;
        InetAddress inetAddress = null;
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    inetAddress = enumInetAddress.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        if (isLocalAddress) {
                            my_ip = inetAddress.getHostAddress();
                        }
                    }else {
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                            my_ip = inetAddress.getHostAddress();
                        }
                    }
                }
            }
            //Generate Inetaddress from host Ip address

            if(my_ip != null) {
                InetAddress myInetAddress = InetAddress.getByName(my_ip);
                return myInetAddress;
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} // BnjUtil
