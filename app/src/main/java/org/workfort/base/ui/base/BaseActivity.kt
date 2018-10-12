package org.workfort.base.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import java.lang.RuntimeException


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/9/2018 at 6:40 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:common property hold
*  *
*  * Last edited by : Md. Azizul Islam on 10/9/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {
    abstract val getLayoutId: Int
    abstract val getMenuId :Int
    abstract val getToolbarId: Int
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var viewDataBinding: ViewDataBinding
    private lateinit var toolbar: Toolbar
    private final val DEFAULT_VALUE = 0
    protected var mBaseCurrentFragment: Fragment? = null

    //Abstract method

    abstract fun startView();
    abstract fun stopView();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = getLayoutId
        if(layout > DEFAULT_VALUE){
            viewDataBinding = DataBindingUtil.setContentView(this, layout)
        }else throw RuntimeException("Please set databinding enabled layout")

        val toolbarId = getToolbarId
        if(toolbarId > DEFAULT_VALUE){
            toolbar = findViewById(toolbarId)
            setSupportActionBar(toolbar)
        }
        startView()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopView()
    }

    fun getToolbar():Toolbar{
        return toolbar
    }
    fun getDisposable():CompositeDisposable{
        return compositeDisposable
    }
    fun getViewBinding():ViewDataBinding{
        return viewDataBinding
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(getMenuId > DEFAULT_VALUE){
            menuInflater.inflate(getLayoutId, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    protected fun setClickListener(vararg views: View) {
        for (view in views) {
            view.setOnClickListener(this)
        }
    }

    protected fun commitFragment(viewId: Int, fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(viewId, fragment, fragment.javaClass.name)
                .commit()

        setCurrentFragment(fragment)
    }
    /*
   * Get current running fragment
   * */
    protected fun getCurrentFragment(): Fragment? {
        return mBaseCurrentFragment
    }

    private fun setCurrentFragment(fragment: Fragment) {
        this.mBaseCurrentFragment = fragment
    }


}