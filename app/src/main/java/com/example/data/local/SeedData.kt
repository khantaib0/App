package com.example.data.local

import com.example.data.model.AiMessage
import com.example.data.model.AutomationWorkflow
import com.example.data.model.Campaign
import com.example.data.model.Customer
import com.example.data.model.FileItem
import com.example.data.model.InvoiceItem
import com.example.data.model.Lead
import com.example.data.model.NotificationItem
import com.example.data.model.PlanTier
import com.example.data.model.TeamMember
import com.example.data.model.WhatsAppMessage
import com.example.data.model.WhatsAppTemplate
import com.example.data.model.Workspace

object SeedData {
    val initialWorkspaces = listOf(
        Workspace(
            id = "ws_1",
            name = "Luxe Apparel Co.",
            logo = "✨",
            category = "E-commerce & Fashion",
            size = "21–50",
            primaryGoal = "Automate marketing & Increase sales",
            planId = "growth"
        ),
        Workspace(
            id = "ws_2",
            name = "Apex Growth Agency",
            logo = "🚀",
            category = "Marketing Agency",
            size = "6–20",
            primaryGoal = "Generate leads & AI automation",
            planId = "pro"
        ),
        Workspace(
            id = "ws_3",
            name = "Prime Estate Properties",
            logo = "🏢",
            category = "Real Estate",
            size = "51–100",
            primaryGoal = "Manage customers & Close deals",
            planId = "enterprise"
        )
    )

    fun getInitialCustomers(workspaceId: String): List<Customer> {
        return listOf(
            Customer(
                id = "${workspaceId}_c1",
                workspaceId = workspaceId,
                name = "Aarav Mehta",
                phone = "+91 98201 44521",
                email = "aarav.mehta@example.com",
                company = "Mehta Global Retail",
                status = "VIP",
                source = "WhatsApp",
                location = "Mumbai, Maharashtra",
                tags = "VIP, Repeat Buyer, High LTV",
                notes = "Always purchases premium line. Prefers WhatsApp notifications on weekends.",
                totalPurchases = 145000.0,
                lastActivity = "Ordered Summer Silk Kurta (₹12,499) 2h ago"
            ),
            Customer(
                id = "${workspaceId}_c2",
                workspaceId = workspaceId,
                name = "Pooja Hegde",
                phone = "+91 97112 88410",
                email = "pooja.h@fashionforward.in",
                company = "Fashion Forward Studio",
                status = "Active",
                source = "Instagram Ads",
                location = "Bengaluru, Karnataka",
                tags = "Wholesale, Seasonal",
                notes = "Looking for festive catalog preview by next week.",
                totalPurchases = 89000.0,
                lastActivity = "Clicked WhatsApp Campaign Link 5h ago"
            ),
            Customer(
                id = "${workspaceId}_c3",
                workspaceId = workspaceId,
                name = "Vikramaditya Roy",
                phone = "+91 99880 12345",
                email = "v.roy@royenterprises.com",
                company = "Roy Enterprises",
                status = "Active",
                source = "Website Organic",
                location = "New Delhi, NCR",
                tags = "B2B, Tier 1",
                notes = "Negotiating annual corporate supply contract.",
                totalPurchases = 320000.0,
                lastActivity = "Downloaded Product Catalog 1d ago"
            ),
            Customer(
                id = "${workspaceId}_c4",
                workspaceId = workspaceId,
                name = "Sneha Patel",
                phone = "+91 94250 99882",
                email = "sneha.p@shreedesigns.co",
                company = "Shree Designer Studio",
                status = "Lead",
                source = "Referral",
                location = "Ahmedabad, Gujarat",
                tags = "Prospect, Hot Lead",
                notes = "Inquired about bespoke bulk orders for wedding season.",
                totalPurchases = 0.0,
                lastActivity = "Filled Lead Capture Form 3h ago"
            ),
            Customer(
                id = "${workspaceId}_c5",
                workspaceId = workspaceId,
                name = "Rohan Deshmukh",
                phone = "+91 91678 33441",
                email = "rohan.d@urbanthreads.io",
                company = "Urban Threads",
                status = "Inactive",
                source = "Google Search",
                location = "Pune, Maharashtra",
                tags = "At Risk, Cart Dropoff",
                notes = "Left cart with items worth ₹18,900. Triggered WhatsApp recovery flow.",
                totalPurchases = 42500.0,
                lastActivity = "Abandoned cart 3d ago"
            )
        )
    }

    fun getInitialLeads(workspaceId: String): List<Lead> {
        return listOf(
            Lead(
                id = "${workspaceId}_l1",
                workspaceId = workspaceId,
                title = "Enterprise Boutique Contract",
                customerName = "Sneha Patel",
                company = "Shree Designer Studio",
                phone = "+91 94250 99882",
                email = "sneha.p@shreedesigns.co",
                stage = "Proposal",
                score = 92,
                estimatedValue = 180000.0,
                assignedTo = "Ananya Sharma",
                followUpDate = "Tomorrow, 11:00 AM",
                tags = "B2B, High Budget, Fast Close",
                notes = "Sent proposal deck V2 with 10% volume discount."
            ),
            Lead(
                id = "${workspaceId}_l2",
                workspaceId = workspaceId,
                title = "Diwali Pre-Order Bulk Lot",
                customerName = "Kunal Verma",
                company = "Verma Retail Chains",
                phone = "+91 98450 11223",
                email = "kunal@vermagroup.in",
                stage = "Qualified",
                score = 84,
                estimatedValue = 350000.0,
                assignedTo = "Rahul Kapoor",
                followUpDate = "Today, 4:30 PM",
                tags = "Retail, Multi-Store",
                notes = "Validated credit history and store locations. Demo scheduled."
            ),
            Lead(
                id = "${workspaceId}_l3",
                workspaceId = workspaceId,
                title = "Export Line Sampling",
                customerName = "David Chen",
                company = "Global Lifestyle HK",
                phone = "+852 9123 4567",
                email = "david.chen@globallifestyle.hk",
                stage = "Interested",
                score = 78,
                estimatedValue = 500000.0,
                assignedTo = "Ananya Sharma",
                followUpDate = "Friday, 2:00 PM",
                tags = "International, Export",
                notes = "Requested swatches and ISO certification documents."
            ),
            Lead(
                id = "${workspaceId}_l4",
                workspaceId = workspaceId,
                title = "Franchise Inward Lead",
                customerName = "Manish Gupta",
                company = "Gupta & Sons",
                phone = "+91 99110 33445",
                email = "manish@guptasons.com",
                stage = "Contacted",
                score = 65,
                estimatedValue = 95000.0,
                assignedTo = "Rahul Kapoor",
                followUpDate = "Tomorrow, 5:00 PM",
                tags = "Franchise, Tier 2",
                notes = "First introductory WhatsApp call completed."
            ),
            Lead(
                id = "${workspaceId}_l5",
                workspaceId = workspaceId,
                title = "Web Form Inquiry - Summer Collec",
                customerName = "Ritu Singhania",
                company = "Singhania Elegance",
                phone = "+91 97722 55667",
                email = "ritu@singhaniaelegance.com",
                stage = "New",
                score = 55,
                estimatedValue = 60000.0,
                assignedTo = "Ananya Sharma",
                followUpDate = "Today, 6:00 PM",
                tags = "Organic Inbound",
                notes = "Direct landing page submission via AI Lead Bot."
            ),
            Lead(
                id = "${workspaceId}_l6",
                workspaceId = workspaceId,
                title = "Annual Vendor Retainer",
                customerName = "Aarav Mehta",
                company = "Mehta Global Retail",
                phone = "+91 98201 44521",
                email = "aarav.mehta@example.com",
                stage = "Won",
                score = 99,
                estimatedValue = 450000.0,
                assignedTo = "Ananya Sharma",
                followUpDate = "Completed",
                tags = "Contract Signed, Closed Won",
                notes = "Signed contract for 12 months. Onboarded to VIP Portal."
            )
        )
    }

    fun getInitialCampaigns(workspaceId: String): List<Campaign> {
        return listOf(
            Campaign(
                id = "${workspaceId}_cmp1",
                workspaceId = workspaceId,
                name = "Summer Glow Flash Sale ⚡",
                type = "WhatsApp",
                status = "Active",
                audience = "VIP Customers + High LTV (4,500 contacts)",
                sent = 4500,
                delivered = 4420,
                opened = 3890,
                clicked = 2140,
                converted = 480,
                revenue = 485000.0,
                scheduleTime = "Triggered Today, 10:00 AM",
                messageContent = "✨ Exclusive Summer VIP Drop! Get 30% off our handcrafted linen collection with code SUMMERVIP. Free express delivery on orders today only! Tap to shop 🛍️"
            ),
            Campaign(
                id = "${workspaceId}_cmp2",
                workspaceId = workspaceId,
                name = "Abandoned Cart WhatsApp Nudge 🛒",
                type = "AI Campaign",
                status = "Active",
                audience = "Users leaving cart > 30 mins",
                sent = 840,
                delivered = 830,
                opened = 760,
                clicked = 510,
                converted = 186,
                revenue = 192000.0,
                scheduleTime = "Automated Real-time",
                messageContent = "Hey there! We saved the items in your bag. Complete your purchase now and enjoy an extra ₹500 discount with code BAG500. 🎁"
            ),
            Campaign(
                id = "${workspaceId}_cmp3",
                workspaceId = workspaceId,
                name = "New Festive Catalog Launch 🌸",
                type = "Email",
                status = "Scheduled",
                audience = "All Registered Leads & Customers (12,450 contacts)",
                sent = 0,
                delivered = 0,
                opened = 0,
                clicked = 0,
                converted = 0,
                revenue = 0.0,
                scheduleTime = "Tomorrow, 9:00 AM",
                messageContent = "Discover our signature Autumn-Winter lookbook curated with handwoven textures and royal pastels. View digital catalog now."
            ),
            Campaign(
                id = "${workspaceId}_cmp4",
                workspaceId = workspaceId,
                name = "VIP Repeat Loyalty Reward 💎",
                type = "SMS",
                status = "Completed",
                audience = "Customers with > 3 orders (1,200 contacts)",
                sent = 1200,
                delivered = 1190,
                opened = 1050,
                clicked = 680,
                converted = 210,
                revenue = 168000.0,
                scheduleTime = "Last Sunday",
                messageContent = "You're our Top 1% VIP! Here is your exclusive ₹1,000 gift voucher for your next checkout. Valid till midnight."
            )
        )
    }

    fun getInitialWhatsAppTemplates(workspaceId: String): List<WhatsAppTemplate> {
        return listOf(
            WhatsAppTemplate(
                id = "${workspaceId}_tpl1",
                workspaceId = workspaceId,
                name = "summer_collection_promo",
                category = "Marketing",
                language = "en",
                status = "Approved",
                content = "Hello {{1}}! 🌸 Step into elegance with our latest Summer Luxe Collection. Enjoy {{2}}% off on orders above ₹2,999. Use Code: {{3}} at checkout.",
                ctaText = "Shop Summer Drop",
                ctaUrl = "https://luxeapparel.com/summer"
            ),
            WhatsAppTemplate(
                id = "${workspaceId}_tpl2",
                workspaceId = workspaceId,
                name = "abandoned_cart_recovery",
                category = "Marketing",
                language = "en",
                status = "Approved",
                content = "Hi {{1}}, you left {{2}} items waiting in your shopping bag! 🛍️ They are selling fast. Checkout in next 2 hours and get Free Express Shipping.",
                ctaText = "Complete Order",
                ctaUrl = "https://luxeapparel.com/cart"
            ),
            WhatsAppTemplate(
                id = "${workspaceId}_tpl3",
                workspaceId = workspaceId,
                name = "order_dispatched_update",
                category = "Utility",
                language = "en",
                status = "Approved",
                content = "Great news {{1}}! Your Order #{{2}} has been dispatched via BlueDart. Track package live using tracking number {{3}}.",
                ctaText = "Track Order",
                ctaUrl = "https://luxeapparel.com/track"
            ),
            WhatsAppTemplate(
                id = "${workspaceId}_tpl4",
                workspaceId = workspaceId,
                name = "lead_welcome_intro",
                category = "Marketing",
                language = "en",
                status = "Approved",
                content = "Welcome to {{1}}! Thanks for expressing interest in our bespoke B2B catalog. Would you like to schedule a 10-minute video walkthrough with our styling director?",
                ctaText = "Book Video Call",
                ctaUrl = "https://calendly.com/luxe-demo"
            )
        )
    }

    fun getInitialWhatsAppMessages(workspaceId: String): List<WhatsAppMessage> {
        return listOf(
            WhatsAppMessage(
                id = "${workspaceId}_msg1",
                workspaceId = workspaceId,
                contactName = "Aarav Mehta",
                contactPhone = "+91 98201 44521",
                text = "✨ Exclusive Summer VIP Drop! Get 30% off our handcrafted linen collection with code SUMMERVIP.",
                isOutbound = true,
                timestamp = "10:00 AM",
                status = "read"
            ),
            WhatsAppMessage(
                id = "${workspaceId}_msg2",
                workspaceId = workspaceId,
                contactName = "Aarav Mehta",
                contactPhone = "+91 98201 44521",
                text = "Hey! Loved the pastel pink linen shirt. Can I order 3 in custom sizes for my team?",
                isOutbound = false,
                timestamp = "10:15 AM",
                status = "read"
            ),
            WhatsAppMessage(
                id = "${workspaceId}_msg3",
                workspaceId = workspaceId,
                contactName = "Aarav Mehta",
                contactPhone = "+91 98201 44521",
                text = "Absolutely Aarav! Our concierge team has reserved those for you. Invoice sent to your email.",
                isOutbound = true,
                timestamp = "10:18 AM",
                status = "read"
            ),
            WhatsAppMessage(
                id = "${workspaceId}_msg4",
                workspaceId = workspaceId,
                contactName = "Pooja Hegde",
                contactPhone = "+91 97112 88410",
                text = "Hi! Just saw your Instagram ad. Do you offer wholesale distributor pricing in Bengaluru?",
                isOutbound = false,
                timestamp = "11:30 AM",
                status = "read"
            ),
            WhatsAppMessage(
                id = "${workspaceId}_msg5",
                workspaceId = workspaceId,
                contactName = "Pooja Hegde",
                contactPhone = "+91 97112 88410",
                text = "Hello Pooja! Yes, we have specialized wholesale tiers. Let me share our B2B price catalog.",
                isOutbound = true,
                timestamp = "11:32 AM",
                status = "delivered"
            )
        )
    }

    fun getInitialAutomations(workspaceId: String): List<AutomationWorkflow> {
        return listOf(
            AutomationWorkflow(
                id = "${workspaceId}_aut1",
                workspaceId = workspaceId,
                title = "New Lead Instant WhatsApp & Nudge",
                triggerType = "New Lead Capture",
                description = "Triggered when form submitted -> Wait 2 mins -> Send WhatsApp Intro -> Wait 1 Day -> Send Follow-up -> If Interested -> Assign Sales Agent",
                isEnabled = true,
                stepsJson = "Form Submission -> Wait 2m -> Send WhatsApp Template -> Wait 24h -> AI Lead Qualification -> Assign Rep",
                totalTriggered = 412,
                conversionRate = "34.2%"
            ),
            AutomationWorkflow(
                id = "${workspaceId}_aut2",
                workspaceId = workspaceId,
                title = "Smart Abandoned Cart Recovery Bot",
                triggerType = "Abandoned Cart",
                description = "Cart inactive for 30m -> AI generates custom discount coupon -> Send WhatsApp message with preview -> Track conversion",
                isEnabled = true,
                stepsJson = "Cart Idle (30m) -> Fetch Cart Items -> AI Generate Custom Offer -> Send WhatsApp -> Update CRM",
                totalTriggered = 890,
                conversionRate = "28.5%"
            ),
            AutomationWorkflow(
                id = "${workspaceId}_aut3",
                workspaceId = workspaceId,
                title = "VIP Post-Purchase Upsell & Review",
                triggerType = "Purchase Completed",
                description = "Order delivered -> Wait 3 days -> Request product review via WhatsApp -> If 5-star -> Offer VIP 20% loyalty voucher",
                isEnabled = true,
                stepsJson = "Order Delivered -> Wait 3d -> WhatsApp Feedback Flow -> Conditional Branch (Rating >= 4) -> Send VIP Voucher",
                totalTriggered = 630,
                conversionRate = "41.0%"
            ),
            AutomationWorkflow(
                id = "${workspaceId}_aut4",
                workspaceId = workspaceId,
                title = "Inactivity Win-Back AI Campaign",
                triggerType = "Inactivity (60 Days)",
                description = "No purchase in 60 days -> AI summarizes past preferences -> Send personalized catalog recommendation",
                isEnabled = false,
                stepsJson = "Customer Inactive 60d -> AI Preference Analysis -> Send Email + WhatsApp -> Add 'Win-back' Tag",
                totalTriggered = 180,
                conversionRate = "14.2%"
            )
        )
    }

    fun getInitialTeamMembers(workspaceId: String): List<TeamMember> {
        return listOf(
            TeamMember(
                id = "${workspaceId}_tm1",
                workspaceId = workspaceId,
                name = "Aisha Khan",
                email = "aisha.khan@company.com",
                role = "Org Owner",
                department = "Executive & Strategy",
                avatarInitials = "AK",
                status = "Active",
                permissions = "Full Admin Access, Billing, Settings, Super Admin"
            ),
            TeamMember(
                id = "${workspaceId}_tm2",
                workspaceId = workspaceId,
                name = "Ananya Sharma",
                email = "ananya.s@company.com",
                role = "Admin",
                department = "Sales & Customer Success",
                avatarInitials = "AS",
                status = "Active",
                permissions = "CRM, Leads Pipeline, WhatsApp, Campaigns, Analytics"
            ),
            TeamMember(
                id = "${workspaceId}_tm3",
                workspaceId = workspaceId,
                name = "Rahul Kapoor",
                email = "rahul.k@company.com",
                role = "Manager",
                department = "Marketing & Automation",
                avatarInitials = "RK",
                status = "Active",
                permissions = "Campaigns, Automation Builder, Files, Reports"
            ),
            TeamMember(
                id = "${workspaceId}_tm4",
                workspaceId = workspaceId,
                name = "Devika Nair",
                email = "devika.n@company.com",
                role = "Member",
                department = "Customer Support",
                avatarInitials = "DN",
                status = "Active",
                permissions = "View CRM, Reply to WhatsApp Chats"
            )
        )
    }

    fun getInitialFiles(workspaceId: String): List<FileItem> {
        return listOf(
            FileItem(
                id = "${workspaceId}_f1",
                workspaceId = workspaceId,
                name = "Summer_Lookbook_2026_HD.pdf",
                type = "PDF",
                size = "8.4 MB",
                folder = "Product Catalogs",
                uploadedAt = "Today, 10:45 AM"
            ),
            FileItem(
                id = "${workspaceId}_f2",
                workspaceId = workspaceId,
                name = "Campaign_Banner_PinkLinen_1080x1080.png",
                type = "Image",
                size = "2.1 MB",
                folder = "Campaign Creatives",
                uploadedAt = "Yesterday, 3:20 PM"
            ),
            FileItem(
                id = "${workspaceId}_f3",
                workspaceId = workspaceId,
                name = "Q2_Customer_Leads_Export.csv",
                type = "CSV",
                size = "450 KB",
                folder = "Lead Database",
                uploadedAt = "2 days ago"
            ),
            FileItem(
                id = "${workspaceId}_f4",
                workspaceId = workspaceId,
                name = "WhatsApp_Broadcast_Copy_Template.docx",
                type = "Document",
                size = "120 KB",
                folder = "Marketing Guidelines",
                uploadedAt = "May 12, 2026"
            )
        )
    }

    fun getInitialNotifications(workspaceId: String): List<NotificationItem> {
        return listOf(
            NotificationItem(
                id = "${workspaceId}_notif1",
                workspaceId = workspaceId,
                title = "New High-Score Lead Inbound 🔥",
                description = "Sneha Patel scored 92/100 for Enterprise Boutique Contract (₹1,80,000).",
                timeAgo = "10m ago",
                type = "lead",
                isRead = false
            ),
            NotificationItem(
                id = "${workspaceId}_notif2",
                workspaceId = workspaceId,
                title = "WhatsApp Campaign Broadcast Finished 🚀",
                description = "Summer Glow Flash Sale delivered to 4,420 contacts with 88% open rate.",
                timeAgo = "1h ago",
                type = "campaign",
                isRead = false
            ),
            NotificationItem(
                id = "${workspaceId}_notif3",
                workspaceId = workspaceId,
                title = "Subscription Payment Successful 💳",
                description = "Growth Tier plan renewed successfully for ₹2,499. Invoice #INV-2026-8902 generated.",
                timeAgo = "4h ago",
                type = "payment",
                isRead = false
            ),
            NotificationItem(
                id = "${workspaceId}_notif4",
                workspaceId = workspaceId,
                title = "Smart Automation Triggered ⚡",
                description = "14 abandoned carts recovered automatically in the last 6 hours.",
                timeAgo = "6h ago",
                type = "automation",
                isRead = true
            ),
            NotificationItem(
                id = "${workspaceId}_notif5",
                workspaceId = workspaceId,
                title = "Team Member Joined 👥",
                description = "Devika Nair joined the workspace as Customer Support specialist.",
                timeAgo = "1d ago",
                type = "team",
                isRead = true
            )
        )
    }

    fun getInitialAiMessages(workspaceId: String): List<AiMessage> {
        return listOf(
            AiMessage(
                id = "${workspaceId}_aimsg1",
                workspaceId = workspaceId,
                sender = "assistant",
                content = "👋 Hello! I am your AI Business Copilot. I can help you draft high-converting WhatsApp broadcasts, build marketing automations, analyze revenue trends, score CRM leads, or write campaign strategies in seconds. Try asking me or tap one of the suggested prompts below!"
            )
        )
    }

    val defaultPlans = listOf(
        PlanTier(
            id = "free",
            name = "Free Tier",
            price = "₹0",
            description = "For testing features and small side projects.",
            isPopular = false,
            features = listOf(
                "Up to 100 Customers & Leads",
                "Basic CRM & Lead Pipeline",
                "50 AI Prompt Generations / mo",
                "1 Team Member Seat",
                "Community Support"
            )
        ),
        PlanTier(
            id = "starter",
            name = "Starter",
            price = "₹999",
            description = "For solo founders & small boutique businesses.",
            isPopular = false,
            features = listOf(
                "Up to 2,500 Customers",
                "1,000 WhatsApp Broadcasts / mo",
                "Full Kanban Lead Scoring",
                "500 AI Copilot Generations / mo",
                "3 Team Member Seats",
                "Email & Chat Support"
            )
        ),
        PlanTier(
            id = "growth",
            name = "Growth",
            price = "₹2,499",
            description = "For growing D2C brands & scaling retail stores.",
            isPopular = true,
            features = listOf(
                "Up to 15,000 Customers",
                "10,000 WhatsApp Broadcasts / mo",
                "Visual Multi-Step Automation Builder",
                "Unlimited AI Campaign Generations",
                "10 Team Member Seats + Roles",
                "Real-time Analytics & Export",
                "Priority WhatsApp Support"
            )
        ),
        PlanTier(
            id = "pro",
            name = "Pro Studio",
            price = "₹4,999",
            description = "For established marketing agencies & large enterprises.",
            isPopular = false,
            features = listOf(
                "Unlimited Customers & Workspaces",
                "50,000 WhatsApp Messages / mo",
                "Custom AI Models & Fine-Tuning",
                "Custom Roles & Granular Permissions",
                "Dedicated Account Manager",
                "Custom Domain & API Access",
                "99.9% SLA Guarantee"
            )
        ),
        PlanTier(
            id = "enterprise",
            name = "Enterprise",
            price = "Custom",
            period = "",
            description = "For large multi-chain organizations & white-label platforms.",
            isPopular = false,
            features = listOf(
                "Unlimited Everything",
                "Dedicated Cloud VPC Instance",
                "Custom ERP & SAP Integrations",
                "Enterprise Security & 2FA SSO",
                "24/7 Phone Concierge"
            )
        )
    )

    val sampleInvoices = listOf(
        com.example.data.model.InvoiceItem(
            id = "inv_1",
            invoiceNumber = "INV-2026-089",
            date = "Aug 01, 2026",
            amount = "₹2,499",
            planName = "Growth Plan (Monthly)",
            status = "Paid"
        ),
        com.example.data.model.InvoiceItem(
            id = "inv_2",
            invoiceNumber = "INV-2026-072",
            date = "Jul 01, 2026",
            amount = "₹2,499",
            planName = "Growth Plan (Monthly)",
            status = "Paid"
        ),
        com.example.data.model.InvoiceItem(
            id = "inv_3",
            invoiceNumber = "INV-2026-054",
            date = "Jun 01, 2026",
            amount = "₹2,499",
            planName = "Growth Plan (Monthly)",
            status = "Paid"
        )
    )
}
