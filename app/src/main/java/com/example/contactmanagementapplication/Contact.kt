package com.example.contactmanagementapplication
import androidx.room.Entity
import androidx.room.PrimaryKey

// entity class for the room database for a contact
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // autogenerated uid
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String
)
