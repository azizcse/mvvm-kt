package org.workfort.base

import android.content.Context
import android.support.multidex.MultiDexApplication
import org.workfort.base.data.AppDatabase


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/12/2018 at 4:26 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/12/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class App : MultiDexApplication() {
    companion object {
        private lateinit var context:Context
        fun getContext():Context{
            return context
        }
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        AppDatabase.getInstance(context)
    }

}