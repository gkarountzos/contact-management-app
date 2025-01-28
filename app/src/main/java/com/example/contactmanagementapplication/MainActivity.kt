package com.example.contactmanagementapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanagementapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val searchQuery = MutableLiveData<String>("")
    private val contactViewModel: ContactManagementViewModel by viewModels()
    private val contactAdapter = ContactAdapter { contact, action -> handleContactAction(contact, action) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeSearchQuery()
        observeContacts()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
            addItemDecoration(SpaceItemDecoration(32))
        }

        binding.addContactButton.setOnClickListener { showContactDialog(null) }

        binding.searchEditText.addTextChangedListener(createSearchTextWatcher())
    }

    // observes search queries and updates the displayed contact list
    private fun observeSearchQuery() {
        searchQuery.observe(this) { query ->
            contactViewModel.search(query).observe(this) { contacts ->
                contactAdapter.submitList(contacts)
            }
        }
    }

    // observes all contacts and updates the recylerview
    private fun observeContacts() {
        contactViewModel.allContacts.observe(this) { contacts ->
            contactAdapter.submitList(contacts)
        }
    }

    // handles actions on a contact
    private fun handleContactAction(contact: Contact, action: ContactAction) {
        when (action) {
            ContactAction.EDIT -> showContactDialog(contact)
            ContactAction.DELETE -> {
                contactViewModel.delete(contact)
                showToast("Contact deleted")
            }
        }
    }

    // dialog for adding or editing a contact
    private fun showContactDialog(contact: Contact?) {
        val dialog = ContactManagementDialogFrag.newInstance(contact)
        dialog.setContactListener { newContact ->
            if (contact == null) {
                contactViewModel.insert(newContact)
                showToast("Contact added")
            } else {
                contactViewModel.update(newContact)
                showToast("Contact updated")
            }
        }
        dialog.show(supportFragmentManager, "ContactDialog")
    }

    // toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // textwatcher to handle search input
    private fun createSearchTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // updates the search query livedata when text changes
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery.value = "%${s.toString()}%"
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }
}
