package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.AiMessage
import com.example.data.model.AutomationWorkflow
import com.example.data.model.Campaign
import com.example.data.model.Customer
import com.example.data.model.FileItem
import com.example.data.model.Lead
import com.example.data.model.NotificationItem
import com.example.data.model.TeamMember
import com.example.data.model.WhatsAppMessage
import com.example.data.model.WhatsAppTemplate
import com.example.data.model.Workspace

@Database(
    entities = [
        Workspace::class,
        Customer::class,
        Lead::class,
        Campaign::class,
        WhatsAppTemplate::class,
        WhatsAppMessage::class,
        AutomationWorkflow::class,
        TeamMember::class,
        FileItem::class,
        NotificationItem::class,
        AiMessage::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun saasDao(): SaaSDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "saas_platform_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
