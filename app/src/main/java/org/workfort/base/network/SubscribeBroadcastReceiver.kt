package org.workfort.base.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.util.Log
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/16/2018 at 12:33 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/16/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class SubscribeBroadcastReceiver constructor(
        val context: Context,
        val intentFilter: IntentFilter
       ) : ObservableOnSubscribe<Intent> {




    override fun subscribe(emitter: ObservableEmitter<Intent>?) {

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contextm: Context?, intent: Intent?) {
                emitter?.onNext(intent);
            }
        }
        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(object : Disposable {
            override fun dispose() {
                context.unregisterReceiver(broadcastReceiver)
                Log.e("rx_network","Destroy broadcast receiver")
            }

            override fun isDisposed(): Boolean {
                return false
            }
        })
        context.registerReceiver(broadcastReceiver, intentFilter)

    }
}