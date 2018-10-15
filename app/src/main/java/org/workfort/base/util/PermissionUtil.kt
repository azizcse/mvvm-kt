package org.workfort.base.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.util.ArrayList


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/15/2018 at 5:14 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/15/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class PermissionUtil {
    private val REQUEST_CODE: Int = 100

    companion object {
        @Volatile
        private var instance: PermissionUtil? = null
        @Volatile
        private var context: Context? = null;

        fun on(context: Context): PermissionUtil {
            this.context = context
            return instance ?: synchronized(this) {
                instance ?: PermissionUtil().also { instance = it }
            }
        }
    }

    fun request(vararg values: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        val finalArgs = ArrayList<String>()
        for (item: String in values) {
            if (context!!.checkSelfPermission(item) != PackageManager.PERMISSION_GRANTED) {
                finalArgs.add(item)
            }
        }

        if (finalArgs.isEmpty()) {
            return true
        }

        (context as Activity).requestPermissions(finalArgs.toTypedArray(), REQUEST_CODE)

        return false
    }

    fun isAllowed(str: String): Boolean {
        if (context == null) return false

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        return if (context!!.checkSelfPermission(str) == PackageManager.PERMISSION_GRANTED) {
            true
        } else false

    }
}