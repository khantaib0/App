package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
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
import kotlinx.coroutines.flow.Flow

@Dao
interface SaaSDao {
    // Workspaces
    @Query("SELECT * FROM workspaces ORDER BY createdAt ASC")
    fun getAllWorkspaces(): Flow<List<Workspace>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkspace(workspace: Workspace)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkspaces(workspaces: List<Workspace>)

    // Customers
    @Query("SELECT * FROM customers WHERE workspaceId = :workspaceId ORDER BY createdAt DESC")
    fun getCustomersByWorkspace(workspaceId: String): Flow<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(customers: List<Customer>)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    // Leads
    @Query("SELECT * FROM leads WHERE workspaceId = :workspaceId ORDER BY score DESC")
    fun getLeadsByWorkspace(workspaceId: String): Flow<List<Lead>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLead(lead: Lead)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeads(leads: List<Lead>)

    @Update
    suspend fun updateLead(lead: Lead)

    @Delete
    suspend fun deleteLead(lead: Lead)

    // Campaigns
    @Query("SELECT * FROM campaigns WHERE workspaceId = :workspaceId ORDER BY createdAt DESC")
    fun getCampaignsByWorkspace(workspaceId: String): Flow<List<Campaign>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaign(campaign: Campaign)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaigns(campaigns: List<Campaign>)

    // WhatsApp Templates
    @Query("SELECT * FROM whatsapp_templates WHERE workspaceId = :workspaceId")
    fun getTemplatesByWorkspace(workspaceId: String): Flow<List<WhatsAppTemplate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: WhatsAppTemplate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<WhatsAppTemplate>)

    // WhatsApp Messages
    @Query("SELECT * FROM whatsapp_messages WHERE workspaceId = :workspaceId")
    fun getMessagesByWorkspace(workspaceId: String): Flow<List<WhatsAppMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: WhatsAppMessage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<WhatsAppMessage>)

    // Automations
    @Query("SELECT * FROM automations WHERE workspaceId = :workspaceId ORDER BY updatedAt DESC")
    fun getAutomationsByWorkspace(workspaceId: String): Flow<List<AutomationWorkflow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAutomation(automation: AutomationWorkflow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAutomations(automations: List<AutomationWorkflow>)

    @Update
    suspend fun updateAutomation(automation: AutomationWorkflow)

    // Team Members
    @Query("SELECT * FROM team_members WHERE workspaceId = :workspaceId")
    fun getTeamMembersByWorkspace(workspaceId: String): Flow<List<TeamMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamMember(member: TeamMember)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamMembers(members: List<TeamMember>)

    // Files
    @Query("SELECT * FROM files WHERE workspaceId = :workspaceId")
    fun getFilesByWorkspace(workspaceId: String): Flow<List<FileItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFile(file: FileItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiles(files: List<FileItem>)

    @Delete
    suspend fun deleteFile(file: FileItem)

    // Notifications
    @Query("SELECT * FROM notifications WHERE workspaceId = :workspaceId ORDER BY id DESC")
    fun getNotificationsByWorkspace(workspaceId: String): Flow<List<NotificationItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationItem>)

    @Query("UPDATE notifications SET isRead = 1 WHERE workspaceId = :workspaceId")
    suspend fun markAllNotificationsAsRead(workspaceId: String)

    // AI Messages
    @Query("SELECT * FROM ai_messages WHERE workspaceId = :workspaceId ORDER BY timestamp ASC")
    fun getAiMessagesByWorkspace(workspaceId: String): Flow<List<AiMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiMessage(message: AiMessage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiMessages(messages: List<AiMessage>)

    @Query("DELETE FROM ai_messages WHERE workspaceId = :workspaceId")
    suspend fun clearAiMessages(workspaceId: String)
}
