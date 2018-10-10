package org.workfort.base.data.contact

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import org.workfort.base.data.BaseEntity
import org.workfort.base.data.ColumnName
import org.workfort.base.data.TableName


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 3:18 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

@Entity(tableName = TableName.TABLE_CONTACT)
data class ContactEntity(
        @ColumnInfo(name = ColumnName.COLUMN_NAME)
        val name :String,
        @ColumnInfo(name=ColumnName.COLUMN_NUMBER)
        val number : String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactEntity> {
        override fun createFromParcel(parcel: Parcel): ContactEntity {
            return ContactEntity(parcel)
        }

        override fun newArray(size: Int): Array<ContactEntity?> {
            return arrayOfNulls(size)
        }
    }
}