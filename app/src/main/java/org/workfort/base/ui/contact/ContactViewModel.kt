package org.workfort.base.ui.contact

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import org.workfort.base.data.contact.ContactEntity
import org.workfort.base.data.contact.ContactRepository
import org.workfort.base.ui.base.BaseViewModel
import org.workfort.base.util.runOnIoThread

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

    private inner class OtherTask : LiveData<String> {
        constructor() {
            loadData()
        }

        private fun loadData() {
            runOnIoThread {
                Thread.sleep(4000)
            }
            value = "Hello from BT"
        }
    }

}


