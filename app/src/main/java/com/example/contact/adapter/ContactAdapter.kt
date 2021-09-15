package com.example.contact.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.R
import com.example.contact.data.Contact
import com.example.contact.data.ContactDatabase
import kotlinx.android.synthetic.main.contact_inflator.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactAdapter(
    val arrayList: ArrayList<Contact>,
    val context: Context,
    val database: ContactDatabase
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    var short_list: ArrayList<Contact> = arrayList
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ContactViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.contact_inflator, parent, false)
        return ContactViewHolder(root)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(arrayList[position],position)
    }

    override fun getItemCount(): Int {
//        if (arrayList.size == 0) {
//            Toast.makeText(context, "List is empty", Toast.LENGTH_SHORT).show()
//        }
        return short_list.size
    }




    inner class ContactViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {

        fun bind(contact: Contact, position: Int) {
            binding.txt_name.text = contact.name
            binding.txt_phn.text = contact.phone
            if (contact.type == 1)
                binding.txt_phn_type.text = "Personal Contact"
            else
                binding.txt_phn_type.text = "Business Contact"
            binding.delete.setOnClickListener {
                GlobalScope.launch{
                    withContext(Dispatchers.Main) {
                        database.contactDao().delete(arrayList[position])
                        arrayList.removeAt(position)
                        notifyDataSetChanged()
                    }
                }


            }
        }

    }


    fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(char: CharSequence?): FilterResults {
                val char_pos = char.toString().toInt()
                val filtr_lst :ArrayList<Contact> = ArrayList()
                if (char_pos == 0){
                    short_list = arrayList
                }else{
                    for (row in arrayList){
                        if (row.type == char_pos){
                            filtr_lst.add(row)
                        }
                    }
                    short_list = filtr_lst
                }
                val  filter_result = FilterResults()
                filter_result.values = short_list
                return  filter_result
            }

            override fun publishResults(char: CharSequence?, results: FilterResults?) {
                short_list = results!!.values as ArrayList<Contact>
                notifyDataSetChanged()
            }

        }
    }

}