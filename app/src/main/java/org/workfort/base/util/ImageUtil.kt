package org.workfort.base.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import org.workfort.base.App


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/31/2018 at 3:18 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/31/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

object ImageUtil {
    /**
     * ImageView extension function
     */
    fun ImageView.show(load:(imView : ImageView)->Unit){
        load(this)
    }

    fun showImage(){

    }
}