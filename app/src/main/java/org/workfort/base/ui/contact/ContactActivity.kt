package org.workfort.base.ui.contact

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import org.workfort.base.R
import org.workfort.base.data.contact.ContactEntity
import org.workfort.base.databinding.ActivityContactBinding
import org.workfort.base.ui.base.BaseActivity
import org.workfort.base.util.ViewInjector


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 10/11/2018 at 3:56 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 10/11/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class ContactActivity : BaseActivity() {
    private lateinit var binding: ActivityContactBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contactAdapter: ContactAdapter
    override val getLayoutId: Int
        get() = R.layout.activity_contact
    override val getMenuId: Int
        get() = 0
    override val getToolbarId: Int
        get() = 0

    override fun startView() {
        binding = getViewBinding() as ActivityContactBinding
        binding!!.buttonSave.setOnClickListener(this)
        binding!!.buttonDelete.setOnClickListener(this)
        contactViewModel = getViewModel()
        initRecyclerView()
        subscribForData()
    }

    private fun initRecyclerView() {
        contactAdapter = ContactAdapter()
        binding.recyclerView.adapter = contactAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun subscribForData() {
        contactViewModel.getAllUsers().observe(this, object : Observer<List<ContactEntity>> {
            override fun onChanged(userEntities: List<ContactEntity>?) {
                contactAdapter.clear()
                contactAdapter.addItems(userEntities!!)
            }
        })

        contactViewModel.getString().observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                Log.e("Live_data","Value from ld ="+t)
            }
        })
    }

    override fun stopView() {}

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.button_save->{
              contactViewModel.saveContact()
            }

            R.id.button_delete->{
                contactViewModel.deleteItem(contactAdapter.getItem(contactAdapter.itemCount-1))
            }
        }
    }

    private fun getViewModel(): ContactViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViewInjector.provideContactViewModel(this@ContactActivity) as T
            }
        }).get(ContactViewModel::class.java!!)
    }
}