package com.example.contact.data

import android.util.Patterns
import androidx.room.Entity
import androidx.room.PrimaryKey

//table name and data class
@Entity(tableName = "contact")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name :String ,
    val phone : String,
    val email :String,
    val type : Int
){
    val isEmailValid: Boolean
        get() = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val phnequals10: Boolean
        get() = phone.length == 10
}