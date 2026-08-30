package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class UserRole {
    SUPER_ADMIN,
    ORG_OWNER,
    ADMIN,
    MANAGER,
    TEAM_MEMBER,
    CUSTOMER
}

@Entity(tableName = "workspaces")
data class Workspace(
    @PrimaryKey val id: String,
    val name: String,
    val logo: String = "🏢",
    val category: String = "E-commerce",
    val size: String = "6-20",
    val primaryGoal: String = "Increase sales & Automate marketing",
    val planId: String = "pro",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val name: String,
    val phone: String,
    val email: String,
    val company: String,
    val status: String, // Active, Inactive, VIP, Lead
    val source: String, // Website, WhatsApp, Referral, Ads
    val location: String = "Mumbai, India",
    val tags: String = "High-Value, Retail", // Comma-separated
    val notes: String = "Interested in luxury summer line",
    val totalPurchases: Double = 0.0,
    val lastActivity: String = "Visited store 2h ago",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "leads")
data class Lead(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val title: String,
    val customerName: String,
    val company: String,
    val phone: String,
    val email: String,
    val stage: String, // New, Contacted, Interested, Qualified, Proposal, Won, Lost
    val score: Int = 85, // 0-100
    val estimatedValue: Double = 25000.0,
    val assignedTo: String = "Ananya Sharma",
    val followUpDate: String = "Tomorrow, 3:00 PM",
    val tags: String = "High Intent, WhatsApp Lead",
    val notes: String = "Requested demo of AI catalog",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "campaigns")
data class Campaign(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val name: String,
    val type: String, // WhatsApp, Email, SMS, Social Media, AI Campaign
    val status: String, // Active, Scheduled, Completed, Draft
    val audience: String, // All VIPs, Abandoned Carts, New Leads
    val sent: Int = 1200,
    val delivered: Int = 1180,
    val opened: Int = 940,
    val clicked: Int = 620,
    val converted: Int = 142,
    val revenue: Double = 185000.0,
    val scheduleTime: String = "Instant / Ongoing",
    val messageContent: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "whatsapp_templates")
data class WhatsAppTemplate(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val name: String,
    val category: String, // Marketing, Utility, Authentication
    val language: String = "en",
    val status: String = "Approved", // Approved, Pending, Rejected
    val content: String,
    val ctaText: String = "Shop Now",
    val ctaUrl: String = "https://yourbrand.com/summer"
)

@Entity(tableName = "whatsapp_messages")
data class WhatsAppMessage(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val contactName: String,
    val contactPhone: String,
    val text: String,
    val isOutbound: Boolean,
    val timestamp: String,
    val status: String = "read" // sent, delivered, read
)

@Entity(tableName = "automations")
data class AutomationWorkflow(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val title: String,
    val triggerType: String, // New Lead, Abandoned Cart, Purchase, Form Submission
    val description: String,
    val isEnabled: Boolean = true,
    val stepsJson: String = "", // e.g. "Wait 10m -> Send WhatsApp -> Wait 1d -> Send Follow-up"
    val totalTriggered: Int = 340,
    val conversionRate: String = "28.5%",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "team_members")
data class TeamMember(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val name: String,
    val email: String,
    val role: String, // Admin, Manager, Member
    val department: String = "Sales & Marketing",
    val avatarInitials: String = "AS",
    val status: String = "Active",
    val permissions: String = "CRM, Leads, Campaigns" // summary
)

@Entity(tableName = "files")
data class FileItem(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val name: String,
    val type: String, // Image, PDF, CSV, Document
    val size: String,
    val folder: String = "Campaign Creatives",
    val uploadedAt: String = "Today, 11:30 AM"
)

@Entity(tableName = "notifications")
data class NotificationItem(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val title: String,
    val description: String,
    val timeAgo: String,
    val type: String, // lead, campaign, payment, automation, team
    val isRead: Boolean = false
)

@Entity(tableName = "ai_messages")
data class AiMessage(
    @PrimaryKey val id: String,
    val workspaceId: String,
    val sender: String, // "user" or "assistant"
    val content: String,
    val campaignJson: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class PlanTier(
    val id: String,
    val name: String,
    val price: String,
    val period: String = "/month",
    val description: String,
    val isPopular: Boolean = false,
    val features: List<String>
)

data class InvoiceItem(
    val id: String,
    val invoiceNumber: String,
    val date: String,
    val amount: String,
    val planName: String,
    val status: String
)
