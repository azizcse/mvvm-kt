package org.workfort.base.data.contact

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import org.workfort.base.data.TableName


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 5:11 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/
@Dao
interface ContactDao {
    @Insert
    fun insert(contactEntity: ContactEntity)

    @Query("SELECT * FROM "+TableName.TABLE_CONTACT)
    fun getAllContacts():Flowable<List<ContactEntity>>
}