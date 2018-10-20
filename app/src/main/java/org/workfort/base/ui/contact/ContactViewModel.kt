package org.workfort.base.ui.contact

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.workfort.base.data.contact.ContactEntity
import org.workfort.base.data.contact.ContactRepository
import org.workfort.base.ui.base.BaseViewModel
import org.workfort.base.util.runOnIoThread
import org.workfort.base.util.runOnUiThread
import java.util.*

/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 5:24 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class ContactViewModel internal constructor(
        private val contactRepository: ContactRepository) : BaseViewModel() {
    private lateinit var otherTask: OtherTask

    init {
        otherTask = OtherTask()
    }

    fun getAllUsers(): LiveData<List<ContactEntity>> {
        return LiveDataReactiveStreams.fromPublisher(contactRepository.getContacts())
    }

    fun getString(): LiveData<String> {
        return otherTask
    }

    fun saveContact(){
        val number = UUID.randomUUID().toString()
        val contactEntity= ContactEntity("Aziz",number)
        getDisposable().add(contactRepository.saveContact(contactEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun deleteItem(contactEntity :ContactEntity?) {
        if(contactEntity == null) return

        getDisposable().add(contactRepository.deleteItem(contactEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    private inner class OtherTask : LiveData<String> {
        constructor() {
            loadData()
        }

        private fun loadData() {
            runOnIoThread {
                Thread.sleep(4000)
                runOnUiThread {
                    value = "Hello from BT"
                }

            }

        }
    }

}


