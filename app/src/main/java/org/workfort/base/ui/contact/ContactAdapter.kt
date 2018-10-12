package org.workfort.base.ui.contact

import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.core.kbasekit.ui.base.BaseAdapter
import com.core.kbasekit.ui.base.BaseViewHolder
import org.workfort.base.R
import org.workfort.base.data.contact.ContactEntity
import org.workfort.base.databinding.ItemContactBinding


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
        return ContactViewHolder(inflate(parent!!, R.layout.item_contact))!!
    }

    private inner class ContactViewHolder(viewDataBinding: ViewDataBinding) : BaseViewHolder<ContactEntity>(viewDataBinding) {
        private val itemContactBinding: ItemContactBinding

        init {
            itemContactBinding = viewDataBinding as ItemContactBinding
        }

        override fun bind(item: ContactEntity) {
            itemContactBinding.contact = item
        }

        override fun onClick(p0: View?) {

        }

    }

}