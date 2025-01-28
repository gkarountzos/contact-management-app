package com.example.contactmanagementapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// room database for managing contacts
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactManagementDB : RoomDatabase() {
    abstract fun contactManagementDAO(): ContactManagementDAO // DAO for database access

    companion object {
        @Volatile
        private var INSTANCE: ContactManagementDB? = null

        // retrieves or creates the database instance
        fun getDatabase(context: Context): ContactManagementDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactManagementDB::class.java,
                    "contact_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
