package org.workfort.base.ui.main

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import org.workfort.base.R
import org.workfort.base.util.ImageUtil
import org.workfort.base.util.PrefUtil
import org.workfort.base.util.ScreenState
import org.workfort.base.util.load

class MainActivity : AppCompatActivity() {


    override fun onStart() {
        super.onStart()
        Log.e("MainActivity", "onStart()");
    }


    override fun onRestart() {
        super.onRestart()
        Log.e("MainActivity", "onRestart()");
    }

    override fun onResume() {
        super.onResume()
        Log.e("MainActivity", "onResume()");
    }

    override fun onPause() {
        super.onPause()
        Log.e("MainActivity", "onPause()");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "onDestroy()");
    }


    override fun onPostResume() {
        Log.e("MainActivity", "onPostResume()");
        super.onPostResume()
    }

    override fun onStop() {
        Log.e("MainActivity", "onStop()");
        super.onStop()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("MainActivity", "onCreate()");
        setContentView(R.layout.activity_main)
        val imageView = findViewById(R.id.image_view) as ImageView
        imageView.load("")
        val value = PrefUtil.get("","")
    }


}



