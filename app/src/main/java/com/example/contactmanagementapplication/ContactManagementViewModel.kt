package com.example.contactmanagementapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// viewmodel for managing contact data and interaction between UI and database
class ContactManagementViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = ContactManagementDB.getDatabase(application).contactManagementDAO() // DAO for database ops
    val allContacts: LiveData<List<Contact>> = dao.getAllContacts() // livedata to observe all contacts

    // inserts a new contact into the database
    fun insert(contact: Contact) = performDatabaseOperation { dao.insert(contact) }

    // updates an existing contact in the database
    fun update(contact: Contact) = performDatabaseOperation { dao.update(contact) }

    // deletes a contact from the database
    fun delete(contact: Contact) = performDatabaseOperation { dao.delete(contact) }

    // searches for contacts based on the query (name or phone)
    fun search(query: String): LiveData<List<Contact>> = dao.searchContacts(query)

    // helper function to perform database operations in a coroutine
    private fun performDatabaseOperation(action: suspend () -> Unit) {
        viewModelScope.launch { action() }
    }
}
