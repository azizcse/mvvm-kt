package org.workfort.base.ui.contact

import android.view.ViewGroup
import com.core.kbasekit.ui.base.BaseAdapter
import com.core.kbasekit.ui.base.BaseViewHolder
import org.workfort.base.data.contact.ContactEntity


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/11/2018 at 4:58 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/11/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class ContactAdapter : BaseAdapter<ContactEntity>() {
    override fun isEqual(leftItem: ContactEntity, rightItem: ContactEntity): Boolean {
        return leftItem.name.equals(rightItem.name)
    }

    override fun newViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<ContactEntity> {
        return null!!
    }


}