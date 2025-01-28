package com.example.contactmanagementapplication

import androidx.lifecycle.LiveData
import androidx.room.*

// DAO for database operations
@Dao
interface ContactManagementDAO {

    // retrieves all contacts ordered alphabetically by first name
    @Query("SELECT * FROM contacts ORDER BY firstName ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    // inserts a contact and ignores conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact): Long

    // updates an existing contact
    @Update
    suspend fun update(contact: Contact)

    // deletes a contact
    @Delete
    suspend fun delete(contact: Contact)

    // searches for contacts by name or phone number
    @Query("SELECT * FROM contacts WHERE firstName LIKE :query OR phoneNumber LIKE :query ORDER BY firstName ASC")
    fun searchContacts(query: String): LiveData<List<Contact>>
}
