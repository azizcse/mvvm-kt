package org.workfort.base.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.workfort.base.R
import org.workfort.base.util.ScreenState

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun showValue(screenState: ScreenState) = when (screenState) {
        is ScreenState.ERROR -> print("error")

        is ScreenState.LOADING->print("Loading")

        is ScreenState.DataValue ->{
            screenState.name
        }

    }

}
