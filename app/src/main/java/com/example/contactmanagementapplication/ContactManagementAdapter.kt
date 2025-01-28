package com.example.contactmanagementapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactmanagementapplication.databinding.ItemContactBinding

// adapter for managing contact data in a recyclerview
class ContactAdapter(
    private val onAction: (Contact, ContactAction) -> Unit
) : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ContactViewHolder(binding, onAction)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position)) // binds contact data to the viewholder
    }

    // viewholder for displaying contact details
    class ContactViewHolder(
        private val binding: ItemContactBinding,
        private val onAction: (Contact, ContactAction) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        // binds contact data to the UI elements
        fun bind(contact: Contact) = with(binding) {
            contactName.text = "${contact.firstName} ${contact.lastName}"
            contactPhone.text = contact.phoneNumber

            // handles user actions
            editButton.setOnClickListener { onAction(contact, ContactAction.EDIT) }
            deleteButton.setOnClickListener { onAction(contact, ContactAction.DELETE) }
        }
    }

    // diffutil for efficient list updates
    companion object ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem == newItem
    }
}
