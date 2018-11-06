package org.workfort.base.ui.main

import android.Manifest
import android.content.Context
import android.media.AudioRecord
import android.media.AudioTrack
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import io.reactivex.subscribers.DefaultSubscriber
import io.underdark.Underdark
import io.underdark.transport.Link
import io.underdark.transport.Transport
import io.underdark.transport.TransportKind
import io.underdark.transport.TransportListener
import org.workfort.base.R
import org.workfort.base.task.RecorderTask
import org.workfort.base.util.*
import java.util.*

class MainActivity : AppCompatActivity(), TransportListener {

    private val MIN_BUFFER_SIZE = 2048
    private val RECORD_BUFFER_SIZE = AudioRecord.getMinBufferSize(RECORDER_RATE, RECORDER_CHANNEL_IN, RECORDER_AUDIO_ENCODING)
    private var RECEIVE_BUFFER_SIZE = AudioTrack.getMinBufferSize(RECORDER_RATE, RECORDER_CHANNEL_OUT, RECORDER_AUDIO_ENCODING)

    private var mRecordTask: RecorderTask? = null
    //private var mReceiveTask: ReceiveTask? = null
    private var isRecording = false

    var connectedLinked = ArrayList<Link>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("MainActivity", "onCreate()");
        setContentView(R.layout.activity_main)
        initLibrary()

    }

    fun onClickButton(view: View) {
        /* if(PermissionUtil.on(this).request(Manifest.permission.RECORD_AUDIO)) {
             startRecording()
         }*/
        sendData()
    }


    fun startRecording() {
        isRecording = true
        mRecordTask = RecorderTask(RECORD_BUFFER_SIZE)

        mRecordTask?.execute(object : DefaultSubscriber<ByteArray>() {

            override fun onNext(bytes: ByteArray) {
                Log.d("Received_bytes", "bytes : ${bytes.size}")
            }

            override fun onError(t: Throwable) {
                Log.d("Received_bytes", "onUpdateSubscriber: $t")
            }

            override fun onComplete() {

            }
        })


    }


    override fun transportNeedsActivity(transport: Transport?, callback: TransportListener.ActivityCallback?) {

    }

    override fun transportLinkConnected(transport: Transport?, link: Link?) {
        Log.d("Received_bytes", "Link connected : ${link!!.nodeId}")
        connectedLinked.add(link!!)
    }

    override fun transportLinkDisconnected(transport: Transport?, link: Link?) {
        Log.d("Received_bytes", "Link disconnected : ${link!!.nodeId}")
        connectedLinked.remove(link)
    }

    override fun transportLinkDidReceiveFrame(transport: Transport?, link: Link?, frameData: ByteArray?) {
        runOnUiThread({
           val receiveValue = String(frameData!!)
            Toast.makeText(this, receiveValue, Toast.LENGTH_LONG).show()
        })
    }


    fun initLibrary() {
        val dark = Underdark.configureTransport(10621, toUuid(),
                "",
                this,
                null,
                this,
                EnumSet.of(TransportKind.WIFI))
        dark.start()
    }

    fun sendData() {
        for (link: Link in connectedLinked) {
            val value = "Hello " + System.currentTimeMillis();
            link.sendFrame(value.toByteArray())
        }
    }


    fun toUuid(): Long {

        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val hashCode = deviceId.hashCode().toLong()

        return if (hashCode < 0) -hashCode else hashCode

    }


}



