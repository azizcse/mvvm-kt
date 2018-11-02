package org.workfort.base.util

import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import org.workfort.base.App


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/1/2018 at 10:26 AM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/1/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/


/**
 * Image view extension function
 */
fun ImageView.load(url: String) {
    Glide.with(App.getContext()).load(url).into(this)
}

fun String.isNull(){

}

