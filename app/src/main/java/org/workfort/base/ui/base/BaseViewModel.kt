package org.workfort.base.ui.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 2:58 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

open class BaseViewModel : ViewModel() {
    private val mCompositeDisposable = CompositeDisposable()
    fun getDisposable(): CompositeDisposable {
        return mCompositeDisposable
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }
}