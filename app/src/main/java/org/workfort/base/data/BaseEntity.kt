package org.workfort.base.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/10/2018 at 3:20 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/10/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

data class BaseEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        @NonNull
        val id: Long) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseEntity> {
        override fun createFromParcel(parcel: Parcel): BaseEntity {
            return BaseEntity(parcel)
        }

        override fun newArray(size: Int): Array<BaseEntity?> {
            return arrayOfNulls(size)
        }
    }
}