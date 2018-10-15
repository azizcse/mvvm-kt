package org.workfort.base.util

import android.content.Context
import org.workfort.base.data.AppDatabase
import org.workfort.base.data.contact.ContactRepository
import org.workfort.base.ui.contact.ContactViewModel


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 6:47 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose: Inject view model on UI
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

object ViewModelInjector {
    private fun getContactRepository(context: Context): ContactRepository {
        return ContactRepository.getInstance(AppDatabase.getInstance(context).getContactDao())
    }


    fun provideContactViewModel(context: Context): ContactViewModel {
        return ContactViewModel(getContactRepository(context))
    }
}