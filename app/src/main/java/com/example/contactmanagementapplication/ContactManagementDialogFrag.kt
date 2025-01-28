package com.example.contactmanagementapplication

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.contactmanagementapplication.databinding.DialogContactBinding

// dialogfragment for adding or editing a contact
class ContactManagementDialogFrag : DialogFragment() {

    private var _binding: DialogContactBinding? = null
    private val binding get() = _binding!!
    private var contact: Contact? = null
    private var listener: ((Contact) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogContactBinding.inflate(LayoutInflater.from(context))
        return Dialog(requireContext()).apply {
            setContentView(binding.root)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        // autopopulat fields if editing an existing contact
        contact?.let {
            with(binding) {
                firstNameEditText.setText(it.firstName)
                lastNameEditText.setText(it.lastName)
                phoneEditText.setText(it.phoneNumber)
                emailEditText.setText(it.email)
            }
        }

        setupListeners()
    }

    // sets up button click listeners
    private fun setupListeners() = with(binding) {
        saveButton.setOnClickListener {
            listener?.invoke(
                Contact(
                    id = contact?.id ?: 0, // use existing ID when editing
                    firstName = firstNameEditText.text.toString(),
                    lastName = lastNameEditText.text.toString(),
                    phoneNumber = phoneEditText.text.toString(),
                    email = emailEditText.text.toString()
                )
            )
            dismiss()
        }

        cancelButton.setOnClickListener { dismiss() }
    }

    fun setContactListener(listener: (Contact) -> Unit) {
        this.listener = listener // callback for saving a contact
    }

    companion object {
        // creates a new instance of the dialog with an optional contact
        fun newInstance(contact: Contact?): ContactManagementDialogFrag =
            ContactManagementDialogFrag().apply { this.contact = contact }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // to avoid memory leaks
    }
}
