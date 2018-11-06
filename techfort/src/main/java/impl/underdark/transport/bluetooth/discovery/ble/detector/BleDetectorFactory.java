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

package impl.underdark.transport.bluetooth.discovery.ble.detector;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;

import io.underdark.util.dispatch.DispatchQueue;

public class BleDetectorFactory
{
	public static BleDetector create(
			BluetoothAdapter adapter,
			BleDetector.Listener listener,
			DispatchQueue queue)
	{
		if(Build.VERSION.SDK_INT >= 21)
		{
			return new BleLollipopDetector(adapter, listener, queue);
		}
		else if(Build.VERSION.SDK_INT >= 18)
		{
			return new BleKitKatDetector(adapter, listener, queue);
		}

		return null;
	}
} // BleDetectorFactory
