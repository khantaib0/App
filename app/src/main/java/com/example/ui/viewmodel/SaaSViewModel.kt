package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.SeedData
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
import com.example.data.remote.GeminiService
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

enum class NavTab(val title: String, val iconName: String) {
    DASHBOARD("Dashboard", "dashboard"),
    AI_ASSISTANT("AI Assistant", "auto_awesome"),
    CUSTOMERS("Customers CRM", "people"),
    LEADS("Leads Pipeline", "filter_alt"),
    WHATSAPP("WhatsApp", "chat"),
    CAMPAIGNS("Campaigns", "campaign"),
    AUTOMATION("Automations", "bolt"),
    ANALYTICS("Analytics", "bar_chart"),
    TEAM("Team & Roles", "badge"),
    FILES("File Manager", "folder"),
    BILLING("Billing & Plans", "credit_card"),
    SETTINGS("Settings", "settings"),
    SUPER_ADMIN("Super Admin", "admin_panel_settings"),
    LANDING_PAGE("Product Landing", "public")
}

enum class AuthState {
    LOGGED_IN,
    LOGGED_OUT,
    ONBOARDING,
    LANDING
}

class SaaSViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val dao = db.saasDao()

    // Theme & Preferences
    private val _themeMode = MutableStateFlow(ThemeMode.LIGHT)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    // Auth & Navigation
    private val _authState = MutableStateFlow(AuthState.LOGGED_IN)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _activeTab = MutableStateFlow(NavTab.DASHBOARD)
    val activeTab: StateFlow<NavTab> = _activeTab.asStateFlow()

    private val _onboardingStep = MutableStateFlow(1)
    val onboardingStep: StateFlow<Int> = _onboardingStep.asStateFlow()

    // Workspaces
    val allWorkspaces: StateFlow<List<Workspace>> = dao.getAllWorkspaces()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeWorkspace = MutableStateFlow(SeedData.initialWorkspaces[0])
    val activeWorkspace: StateFlow<Workspace> = _activeWorkspace.asStateFlow()

    // Timeframe filter for analytics/charts
    private val _timeFilter = MutableStateFlow("30 Days")
    val timeFilter: StateFlow<String> = _timeFilter.asStateFlow()

    // Search & Selection State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCustomer = MutableStateFlow<Customer?>(null)
    val selectedCustomer: StateFlow<Customer?> = _selectedCustomer.asStateFlow()

    private val _selectedLead = MutableStateFlow<Lead?>(null)
    val selectedLead: StateFlow<Lead?> = _selectedLead.asStateFlow()

    // AI Generation
    private val _isAiGenerating = MutableStateFlow(false)
    val isAiGenerating: StateFlow<Boolean> = _isAiGenerating.asStateFlow()

    // Plans & Invoices
    val plans: List<PlanTier> = SeedData.defaultPlans
    val invoices: List<InvoiceItem> = SeedData.sampleInvoices

    // Dialog Visibility States
    val showAddCustomerDialog = MutableStateFlow(false)
    val showAddLeadDialog = MutableStateFlow(false)
    val showCreateCampaignDialog = MutableStateFlow(false)
    val showCreateAutomationDialog = MutableStateFlow(false)
    val showInviteMemberDialog = MutableStateFlow(false)
    val showNotificationsSheet = MutableStateFlow(false)
    val showWorkspaceSwitcher = MutableStateFlow(false)
    val showUpgradePlanDialog = MutableStateFlow(false)
    val showNewWhatsAppTemplateDialog = MutableStateFlow(false)
    val showAddFileDialog = MutableStateFlow(false)

    // Data streams based on active workspace
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val customers: StateFlow<List<Customer>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getCustomersByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val leads: StateFlow<List<Lead>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getLeadsByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val campaigns: StateFlow<List<Campaign>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getCampaignsByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val whatsappTemplates: StateFlow<List<WhatsAppTemplate>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getTemplatesByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val whatsappMessages: StateFlow<List<WhatsAppMessage>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getMessagesByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val automations: StateFlow<List<AutomationWorkflow>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getAutomationsByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val teamMembers: StateFlow<List<TeamMember>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getTeamMembersByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val files: StateFlow<List<FileItem>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getFilesByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val notifications: StateFlow<List<NotificationItem>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getNotificationsByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val aiMessages: StateFlow<List<AiMessage>> = _activeWorkspace.flatMapLatest { ws ->
        dao.getAiMessagesByWorkspace(ws.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        seedInitialData()
    }

    private fun seedInitialData() {
        viewModelScope.launch {
            dao.insertWorkspaces(SeedData.initialWorkspaces)
            for (ws in SeedData.initialWorkspaces) {
                dao.insertCustomers(SeedData.getInitialCustomers(ws.id))
                dao.insertLeads(SeedData.getInitialLeads(ws.id))
                dao.insertCampaigns(SeedData.getInitialCampaigns(ws.id))
                dao.insertTemplates(SeedData.getInitialWhatsAppTemplates(ws.id))
                dao.insertMessages(SeedData.getInitialWhatsAppMessages(ws.id))
                dao.insertAutomations(SeedData.getInitialAutomations(ws.id))
                dao.insertTeamMembers(SeedData.getInitialTeamMembers(ws.id))
                dao.insertFiles(SeedData.getInitialFiles(ws.id))
                dao.insertNotifications(SeedData.getInitialNotifications(ws.id))
                dao.insertAiMessages(SeedData.getInitialAiMessages(ws.id))
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    fun setTimeFilter(filter: String) {
        _timeFilter.value = filter
    }

    fun setNavTab(tab: NavTab) {
        _activeTab.value = tab
    }

    fun setAuthState(state: AuthState) {
        _authState.value = state
    }

    fun setOnboardingStep(step: Int) {
        _onboardingStep.value = step
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectWorkspace(workspace: Workspace) {
        _activeWorkspace.value = workspace
        showWorkspaceSwitcher.value = false
    }

    fun createWorkspace(name: String, category: String, size: String, goal: String) {
        viewModelScope.launch {
            val newWs = Workspace(
                id = "ws_${UUID.randomUUID().toString().take(6)}",
                name = name,
                category = category,
                size = size,
                primaryGoal = goal,
                planId = "starter"
            )
            dao.insertWorkspace(newWs)
            dao.insertCustomers(SeedData.getInitialCustomers(newWs.id))
            dao.insertLeads(SeedData.getInitialLeads(newWs.id))
            dao.insertCampaigns(SeedData.getInitialCampaigns(newWs.id))
            dao.insertTemplates(SeedData.getInitialWhatsAppTemplates(newWs.id))
            dao.insertMessages(SeedData.getInitialWhatsAppMessages(newWs.id))
            dao.insertAutomations(SeedData.getInitialAutomations(newWs.id))
            dao.insertTeamMembers(SeedData.getInitialTeamMembers(newWs.id))
            dao.insertFiles(SeedData.getInitialFiles(newWs.id))
            dao.insertNotifications(SeedData.getInitialNotifications(newWs.id))
            dao.insertAiMessages(SeedData.getInitialAiMessages(newWs.id))
            _activeWorkspace.value = newWs
        }
    }

    fun selectCustomer(customer: Customer?) {
        _selectedCustomer.value = customer
    }

    fun selectLead(lead: Lead?) {
        _selectedLead.value = lead
    }

    fun addCustomer(name: String, phone: String, email: String, company: String, status: String, source: String, tags: String, notes: String) {
        viewModelScope.launch {
            val customer = Customer(
                id = "${_activeWorkspace.value.id}_c_${UUID.randomUUID().toString().take(6)}",
                workspaceId = _activeWorkspace.value.id,
                name = name,
                phone = phone,
                email = email,
                company = company,
                status = status,
                source = source,
                tags = tags,
                notes = notes,
                totalPurchases = 0.0,
                lastActivity = "Added just now"
            )
            dao.insertCustomer(customer)
            showAddCustomerDialog.value = false
        }
    }

    fun updateCustomer(customer: Customer) {
        viewModelScope.launch {
            dao.updateCustomer(customer)
            _selectedCustomer.value = customer
        }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            dao.deleteCustomer(customer)
            if (_selectedCustomer.value?.id == customer.id) {
                _selectedCustomer.value = null
            }
        }
    }

    fun addLead(title: String, customerName: String, company: String, phone: String, email: String, stage: String, score: Int, value: Double, assignedTo: String, tags: String) {
        viewModelScope.launch {
            val lead = Lead(
                id = "${_activeWorkspace.value.id}_l_${UUID.randomUUID().toString().take(6)}",
                workspaceId = _activeWorkspace.value.id,
                title = title,
                customerName = customerName,
                company = company,
                phone = phone,
                email = email,
                stage = stage,
                score = score,
                estimatedValue = value,
                assignedTo = assignedTo,
                tags = tags
            )
            dao.insertLead(lead)
            showAddLeadDialog.value = false
        }
    }

    fun updateLeadStage(lead: Lead, newStage: String) {
        viewModelScope.launch {
            val updated = lead.copy(stage = newStage)
            dao.updateLead(updated)
            if (_selectedLead.value?.id == lead.id) {
                _selectedLead.value = updated
            }
        }
    }

    fun deleteLead(lead: Lead) {
        viewModelScope.launch {
            dao.deleteLead(lead)
            if (_selectedLead.value?.id == lead.id) {
                _selectedLead.value = null
            }
        }
    }

    fun addCampaign(name: String, type: String, audience: String, messageContent: String) {
        viewModelScope.launch {
            val campaign = Campaign(
                id = "${_activeWorkspace.value.id}_cmp_${UUID.randomUUID().toString().take(6)}",
                workspaceId = _activeWorkspace.value.id,
                name = name,
                type = type,
                status = "Active",
                audience = audience,
                sent = 1000,
                delivered = 980,
                opened = 750,
                clicked = 420,
                converted = 95,
                revenue = 115000.0,
                scheduleTime = "Triggered Just Now",
                messageContent = messageContent
            )
            dao.insertCampaign(campaign)
            showCreateCampaignDialog.value = false
        }
    }

    fun addAutomation(title: String, triggerType: String, description: String, stepsJson: String) {
        viewModelScope.launch {
            val automation = AutomationWorkflow(
                id = "${_activeWorkspace.value.id}_aut_${UUID.randomUUID().toString().take(6)}",
                workspaceId = _activeWorkspace.value.id,
                title = title,
                triggerType = triggerType,
                description = description,
                isEnabled = true,
                stepsJson = stepsJson,
                totalTriggered = 0,
                conversionRate = "0.0%"
            )
            dao.insertAutomation(automation)
            showCreateAutomationDialog.value = false
        }
    }

    fun toggleAutomation(automation: AutomationWorkflow) {
        viewModelScope.launch {
            dao.updateAutomation(automation.copy(isEnabled = !automation.isEnabled))
        }
    }

    fun addWhatsAppTemplate(name: String, category: String, content: String, ctaText: String, ctaUrl: String) {
        viewModelScope.launch {
            val template = WhatsAppTemplate(
                id = "${_activeWorkspace.value.id}_tpl_${UUID.randomUUID().toString().take(6)}",
                workspaceId = _activeWorkspace.value.id,
                name = name,
                category = category,
                status = "Approved",
                content = content,
                ctaText = ctaText,
                ctaUrl = ctaUrl
            )
            dao.insertTemplate(template)
            showNewWhatsAppTemplateDialog.value = false
        }
    }

    fun sendWhatsAppMessage(contactName: String, contactPhone: String, text: String) {
        viewModelScope.launch {
            val msg = WhatsAppMessage(
                id = "${_activeWorkspace.value.id}_msg_${UUID.randomUUID().toString().take(6)}",
                workspaceId = _activeWorkspace.value.id,
                contactName = contactName,
                contactPhone = contactPhone,
                text = text,
                isOutbound = true,
                timestamp = "Just now",
                status = "delivered"
            )
            dao.insertMessage(msg)
        }
    }

    fun inviteTeamMember(name: String, email: String, role: String, department: String, permissions: String) {
        viewModelScope.launch {
            val initials = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("").uppercase()
            val member = TeamMember(
                id = "${_activeWorkspace.value.id}_tm_${UUID.randomUUID().toString().take(6)}",
                workspaceId = _activeWorkspace.value.id,
                name = name,
                email = email,
                role = role,
                department = department,
                avatarInitials = if (initials.isNotBlank()) initials else "TM",
                permissions = permissions
            )
            dao.insertTeamMember(member)
            showInviteMemberDialog.value = false
        }
    }

    fun addFile(name: String, type: String, size: String, folder: String) {
        viewModelScope.launch {
            val file = FileItem(
                id = "${_activeWorkspace.value.id}_f_${UUID.randomUUID().toString().take(6)}",
                workspaceId = _activeWorkspace.value.id,
                name = name,
                type = type,
                size = size,
                folder = folder,
                uploadedAt = "Just now"
            )
            dao.insertFile(file)
            showAddFileDialog.value = false
        }
    }

    fun deleteFile(file: FileItem) {
        viewModelScope.launch {
            dao.deleteFile(file)
        }
    }

    fun markNotificationsRead() {
        viewModelScope.launch {
            dao.markAllNotificationsAsRead(_activeWorkspace.value.id)
        }
    }

    fun sendAiPrompt(prompt: String) {
        if (prompt.isBlank() || _isAiGenerating.value) return
        val currentWs = _activeWorkspace.value
        viewModelScope.launch {
            val userMsg = AiMessage(
                id = "${currentWs.id}_aimsg_${UUID.randomUUID().toString().take(6)}",
                workspaceId = currentWs.id,
                sender = "user",
                content = prompt
            )
            dao.insertAiMessage(userMsg)
            _isAiGenerating.value = true

            val aiResponseText = GeminiService.generateResponse(
                prompt = prompt,
                workspaceName = currentWs.name,
                category = currentWs.category
            )

            val assistantMsg = AiMessage(
                id = "${currentWs.id}_aimsg_${UUID.randomUUID().toString().take(6)}",
                workspaceId = currentWs.id,
                sender = "assistant",
                content = aiResponseText
            )
            dao.insertAiMessage(assistantMsg)
            _isAiGenerating.value = false
        }
    }

    fun clearAiChat() {
        viewModelScope.launch {
            dao.clearAiMessages(_activeWorkspace.value.id)
            dao.insertAiMessage(SeedData.getInitialAiMessages(_activeWorkspace.value.id)[0])
        }
    }

    fun upgradePlan(planId: String) {
        viewModelScope.launch {
            val updatedWs = _activeWorkspace.value.copy(planId = planId)
            dao.insertWorkspace(updatedWs)
            _activeWorkspace.value = updatedWs
            showUpgradePlanDialog.value = false
        }
    }
}
