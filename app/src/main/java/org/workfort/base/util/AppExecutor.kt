package org.workfort.base.util

import java.util.concurrent.Executors


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 6:41 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun runOnIoThread(t: () -> Unit) {
    IO_EXECUTOR.execute(t)
}