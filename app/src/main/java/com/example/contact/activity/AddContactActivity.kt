package com.example.contact.activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contact.R
import com.example.contact.data.Contact
import com.example.contact.data.ContactDatabase
import com.example.contact.databinding.ActivityContactBinding
import com.example.contact.viewmodel.Contactviewmodel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class AddContactActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactBinding
    lateinit var contactviewmodel: Contactviewmodel
    lateinit var database: ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setting view model
        contactviewmodel = ViewModelProvider(this).get(Contactviewmodel::class.java)

        //binding xml
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contact)

        init()
        lstnr()
    }
    private fun init() {

        //database object
        database = ContactDatabase.getDatabase(this)

        //seting up the owner
        binding!!.lifecycleOwner = this

        //binding viewmodel
        binding!!.contactviewmodel = contactviewmodel

    }
    private fun lstnr() {

        //setting up the observer
        contactviewmodel!!.getUser()!!.observe(
            this,
            Observer { user ->

                //// checking for blank fields
                if(user.name.trim().equals("null",ignoreCase = true)){
                    Toast.makeText(this,resources.getString(R.string.enter_name),Toast.LENGTH_SHORT).show()
                }else if (user.phone.equals("null",ignoreCase = true)) {
                    Toast.makeText(this,resources.getString(R.string.enter_phone),Toast.LENGTH_SHORT).show()
                }else if (!user.phnequals10) {
                    Toast.makeText(this,resources.getString(R.string.ten_digit),Toast.LENGTH_SHORT).show()
                }else if (user.email.equals("null",ignoreCase = true)) {
                    Toast.makeText(this,resources.getString(R.string.enter_email),Toast.LENGTH_SHORT).show()
                } else if (!user!!.isEmailValid) {
                    Toast.makeText(this,resources.getString(R.string.enter_valid_email),Toast.LENGTH_SHORT).show()
                }else if (user!!.type == 0) {
                    Toast.makeText(this,resources.getString(R.string.enter_contact_type),Toast.LENGTH_SHORT).show()
                }else {
                   GlobalScope.launch{
                       database.contactDao().insert(user)
                        finish()
                   }

                }
            })
    }


}