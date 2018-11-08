package org.workfort.base.util.search

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.core.kbasekit.ui.base.BaseAdapter
import com.core.kbasekit.ui.base.BaseViewHolder
import org.workfort.base.util.collection.Product


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/8/2018 at 11:29 AM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/8/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

class SearchAdapter : BaseAdapter<Product>(),Filterable {
    var filterList = ArrayList<Product>()
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                if(charSequence.isNullOrEmpty()){
                    filterList = getItems() as ArrayList<Product>
                }else{
                    val list = ArrayList<Product>()
                    for (item : Product in getItems()) {
                        //put your condition here
                    }
                    filterList.addAll(list)
                }

                val result = FilterResults()
                result.values = filterList

                return result
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                //Put the search result to actual
                notifyDataSetChanged()
            }

        }
    }

    override fun newViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<Product> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEqual(leftItem: Product, rightItem: Product): Boolean {
        return false
    }
}