package org.workfort.base.network


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Observable;
import io.reactivex.functions.Function
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.util.Log


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/16/2018 at 2:38 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/16/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

object NetworkState {

    fun getConnectivityStatus(context: Context): Boolean {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.getActiveNetworkInfo()
        return null != activeNetwork && activeNetwork.isConnected();

    }

    fun stream(context: Context): Observable<Boolean> {
        val applicationContext = context.getApplicationContext()
        val action = IntentFilter(CONNECTIVITY_ACTION)
        return ContentObservable.fromBroadcast(context, action)
                .map(object : Function<Intent, Boolean>{
                    override fun apply(t: Intent): Boolean {
                        Log.e("rx_network","Received intent")
                       return getConnectivityStatus(applicationContext)
                    }

                }).distinctUntilChanged()
    }

}