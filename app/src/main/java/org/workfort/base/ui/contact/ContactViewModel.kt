package org.workfort.base.ui.contact

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.database.Cursor
import io.reactivex.Emitter
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import org.workfort.base.data.contact.ContactEntity
import org.workfort.base.data.contact.ContactRepository
import org.workfort.base.ui.base.BaseViewModel
import java.util.concurrent.Callable

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
        private val contactRepository: ContactRepository):BaseViewModel(){

    fun getAllUsers():LiveData<List<ContactEntity>>{
        return LiveDataReactiveStreams.fromPublisher(contactRepository.getContacts())
    }

}


