package org.workfort.base.task

import android.util.Log
import io.reactivex.FlowableEmitter


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/6/2018 at 10:41 AM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/6/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class RecorderTask : BaseTask<ByteArray> {
    private var record: AudioRecorder? = null
    private val RECORD_BUFFER_SIZE: Int

    constructor(RECORD_BUFFER_SIZE : Int){
        this.RECORD_BUFFER_SIZE = RECORD_BUFFER_SIZE
        Log.d("Received_bytes", "constructor()")
    }
    override fun executeTask(subscribe: FlowableEmitter<ByteArray>) {
        Log.d("Received_bytes", "executeTask()")
        record = AudioRecorder(RECORD_BUFFER_SIZE, subscribe)
        record?.executeRecording()
    }
}