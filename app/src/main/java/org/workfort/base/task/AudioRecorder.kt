package org.workfort.base.task

import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import io.reactivex.FlowableEmitter
import org.workfort.base.util.RECORDER_AUDIO_ENCODING
import org.workfort.base.util.RECORDER_CHANNEL_IN
import org.workfort.base.util.RECORDER_RATE


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/6/2018 at 10:50 AM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/6/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class AudioRecorder {
    private val RECORD_BUFFER_SIZE: Int
    private var isRecording: Boolean = false
    private var mAudioRecord: AudioRecord? = null
    private val mSubscriber: FlowableEmitter<ByteArray>
    lateinit var audioRecorder : AudioRecord

    constructor(RECORD_BUFFER_SIZE : Int,  subscriber: FlowableEmitter<ByteArray>){
        mSubscriber = subscriber
        this.RECORD_BUFFER_SIZE = RECORD_BUFFER_SIZE
    }

    fun executeRecording(){
        audioRecorder =  AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                RECORDER_RATE,
                RECORDER_CHANNEL_IN,
                RECORDER_AUDIO_ENCODING,
                RECORD_BUFFER_SIZE)

        mAudioRecord?.startRecording()
        isRecording = true

        val byteData = ByteArray(RECORD_BUFFER_SIZE)
        isRecording = true
        Log.d("Received_bytes", "executeRecording()")
        while (isRecording) {
            mAudioRecord?.read(byteData, 0, byteData.size)
            mSubscriber.onNext(byteData)
            //Log.d("Received_bytes", "Inside loop()")
        }

    }

    fun stopRecording() {
        mSubscriber.onComplete()
        if (mAudioRecord != null) {
            isRecording = false
            mAudioRecord?.stop()
            mAudioRecord?.release()
            mAudioRecord = null
        }
    }
}