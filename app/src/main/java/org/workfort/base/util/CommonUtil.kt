package org.workfort.base.util

import android.media.AudioFormat
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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

fun ImageView.loadWithCash(url: String) {
    Glide.with(App.getContext())
            .load(url)
            .apply(RequestOptions()
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(this)
}

fun ImageView.loadWithoutCash(url : String){
    Glide.with(App.getContext())
            .load(url)
            .apply(RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE))
            .into(this)
}


val RECEIVE_PORT = 8888

val GO_IP = "192.168.49.1"

val RECORDER_RATE = 8000
val RECORDER_CHANNEL_OUT = AudioFormat.CHANNEL_OUT_MONO
val RECORDER_CHANNEL_IN = AudioFormat.CHANNEL_IN_MONO
val RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT



