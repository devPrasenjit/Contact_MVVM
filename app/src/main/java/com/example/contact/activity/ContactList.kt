package com.example.contact.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.adapter.ContactAdapter
import com.example.contact.R
import com.example.contact.data.Contact
import com.example.contact.data.ContactDatabase
import kotlinx.android.synthetic.main.activity_listvw.*


class ContactList : AppCompatActivity(){
    lateinit var database: ContactDatabase
    var cntctadapter: ContactAdapter?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listvw)
        //Database object
        database = ContactDatabase.getDatabase(this)
        lstnr()
    }

    private fun lstnr() {
        btn_add.setOnClickListener{
            startActivity(Intent(this@ContactList,AddContactActivity::class.java))
        }

        spnr_contact.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                // for getting the personal/business contact
                if (cntctadapter!=null){
                    cntctadapter!!.getFilter().filter(position.toString())
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

    }

    override fun onResume() {
        super.onResume()
        set_recyclerview()
    }

    private fun set_recyclerview() {

        //Setting the observer to get the list details
        database.contactDao().getContact().observe(this,
            Observer<List<Contact>> {contactlst->

                //  Setting the adapter of the contact list
                cntctadapter = ContactAdapter(
                    contactlst as ArrayList<Contact>,
                    this,
                    database
                )
                val layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
                rv_contact.setHasFixedSize(true)
                rv_contact.layoutManager = layoutManager
                rv_contact.adapter = cntctadapter
            })

    }
}