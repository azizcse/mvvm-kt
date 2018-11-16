package org.workfort.base.network


import android.content.Context
import android.content.Intent
import android.content.IntentFilter

import io.reactivex.Observable



/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/16/2018 at 2:29 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/16/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class ContentObservable {
    private constructor() {
        throw AssertionError("No instances")
    }

    companion object {

        /**
         * Create Observable that wraps BroadcastReceiver and emits received intents.
         *
         * @param filter Selects the Intent broadcasts to be received.
         */
        fun fromBroadcast(context: Context, filter: IntentFilter): Observable<Intent> {
            return Observable.create(SubscribeBroadcastReceiver(context, filter))
        }
    }
}