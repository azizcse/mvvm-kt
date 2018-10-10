package org.workfort.base.data.contact


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 5:18 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class ContactRepository private constructor(private val contactDao: ContactDao) {
    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: ContactRepository? = null

        fun getInstance(contactDao: ContactDao) =
                instance ?: synchronized(this) {
                    instance ?: ContactRepository(contactDao).also { instance = it }
                }
    }

    fun getContacts() = contactDao.getAllContacts()
}