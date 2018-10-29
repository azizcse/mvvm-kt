package org.workfort.base.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 6:41 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose: Run task both on UI and bask end thread
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
private val handler: Handler = Handler(Looper.getMainLooper())

fun runOnIoThread(t: () -> Unit) {
    IO_EXECUTOR.execute(t)
}


fun runOnUiThread(t: () -> Unit) {
    handler.post(t)
}

fun runOnUiThread(delay: Long, t: () -> Unit) {
    handler.postDelayed(t, delay)
}