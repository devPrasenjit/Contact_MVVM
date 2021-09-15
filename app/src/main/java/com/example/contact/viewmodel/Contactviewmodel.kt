package com.example.contact.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contact.data.Contact
class Contactviewmodel :ViewModel() {

    //Mutable live data for all fields
    var Name = MutableLiveData<String>()
    var Phn = MutableLiveData<String>()
    var EmailAddress = MutableLiveData<String>()
    var phn_type = MutableLiveData<Int>()

    private var userMutableLiveData: MutableLiveData<Contact>? = null

    fun getUser(): MutableLiveData<Contact>? {
        if (userMutableLiveData == null) {
            userMutableLiveData = MutableLiveData<Contact>()
        }
        return userMutableLiveData
    }

    // Onclick function to save contact
    fun onClick(view: View?) {

            val nm: String = Name.value.toString()
            val pn_no: String = Phn.value.toString()
            val email: String = EmailAddress.value.toString()
            val type: Int = phn_type.value!!.toInt()
            val cntct = Contact(0,nm,pn_no,email,type)
                userMutableLiveData!!.value = cntct
    }

}