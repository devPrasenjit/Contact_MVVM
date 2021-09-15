package com.example.contact.data

import androidx.lifecycle.LiveData
import androidx.room.*

//dao interface
@Dao
interface ContactDAO {

    @Insert
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)


    @Delete
    suspend fun delete(contact: Contact)

    @Query("Select * from contact")
    fun getContact(): LiveData<List<Contact>>
}