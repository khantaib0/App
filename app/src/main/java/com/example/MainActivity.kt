package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.components.AddCustomerDialog
import com.example.ui.components.AddLeadDialog
import com.example.ui.components.AppBottomNav
import com.example.ui.components.CreateAutomationDialog
import com.example.ui.components.CreateCampaignDialog
import com.example.ui.components.FullDrawerMenuSheet
import com.example.ui.components.InviteMemberDialog
import com.example.ui.components.NotificationsDialog
import com.example.ui.components.TopHeader
import com.example.ui.components.UpgradePlanDialog
import com.example.ui.components.UploadFileDialog
import com.example.ui.components.WorkspaceSwitcherDialog
import com.example.ui.screens.AiAssistantScreen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.AuthAndOnboardingScreen
import com.example.ui.screens.AutomationBuilderScreen
import com.example.ui.screens.BillingScreen
import com.example.ui.screens.CampaignsScreen
import com.example.ui.screens.CustomerCrmScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.FileManagerScreen
import com.example.ui.screens.LandingPageScreen
import com.example.ui.screens.LeadPipelineScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SuperAdminScreen
import com.example.ui.screens.TeamScreen
import com.example.ui.screens.WhatsAppMarketingScreen
import com.example.ui.theme.SaaSAppTheme
import com.example.ui.viewmodel.AuthState
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SaaSViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: SaaSViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by viewModel.themeMode.collectAsState()

            SaaSAppTheme(themeMode = themeMode) {
                SaaSAppRoot(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaaSAppRoot(viewModel: SaaSViewModel) {
    val authState by viewModel.authState.collectAsState()
    val activeTab by viewModel.activeTab.collectAsState()
    val currentWorkspace by viewModel.activeWorkspace.collectAsState()
    val allWorkspaces by viewModel.allWorkspaces.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    var showDrawerSheet by remember { mutableStateOf(false) }

    // Dialog state collections
    val showAddCustomer by viewModel.showAddCustomerDialog.collectAsState()
    val showAddLead by viewModel.showAddLeadDialog.collectAsState()
    val showCreateCampaign by viewModel.showCreateCampaignDialog.collectAsState()
    val showCreateAutomation by viewModel.showCreateAutomationDialog.collectAsState()
    val showInviteMember by viewModel.showInviteMemberDialog.collectAsState()
    val showNotifications by viewModel.showNotificationsSheet.collectAsState()
    val showWorkspaceSwitcher by viewModel.showWorkspaceSwitcher.collectAsState()
    val showAddFile by viewModel.showAddFileDialog.collectAsState()

    val unreadCount = notifications.count { !it.isRead }

    if (authState == AuthState.LOGGED_OUT || authState == AuthState.ONBOARDING) {
        AuthAndOnboardingScreen(viewModel = viewModel)
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (activeTab != NavTab.LANDING_PAGE) {
                    TopHeader(
                        currentWorkspace = currentWorkspace,
                        unreadNotificationsCount = unreadCount,
                        currentThemeMode = themeMode,
                        searchQuery = searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onWorkspaceClick = { viewModel.showWorkspaceSwitcher.value = true },
                        onNotificationsClick = { viewModel.showNotificationsSheet.value = true },
                        onThemeModeChange = { viewModel.setThemeMode(it) },
                        onProfileClick = { viewModel.setNavTab(NavTab.SETTINGS) }
                    )
                }
            },
            bottomBar = {
                AppBottomNav(
                    activeTab = activeTab,
                    onTabSelected = { viewModel.setNavTab(it) },
                    onOpenMoreMenu = { showDrawerSheet = true }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Crossfade(targetState = activeTab, label = "TabCrossfade") { tab ->
                    when (tab) {
                        NavTab.DASHBOARD -> DashboardScreen(viewModel = viewModel)
                        NavTab.AI_ASSISTANT -> AiAssistantScreen(viewModel = viewModel)
                        NavTab.CUSTOMERS -> CustomerCrmScreen(viewModel = viewModel)
                        NavTab.LEADS -> LeadPipelineScreen(viewModel = viewModel)
                        NavTab.WHATSAPP -> WhatsAppMarketingScreen(viewModel = viewModel)
                        NavTab.CAMPAIGNS -> CampaignsScreen(viewModel = viewModel)
                        NavTab.AUTOMATION -> AutomationBuilderScreen(viewModel = viewModel)
                        NavTab.ANALYTICS -> AnalyticsScreen(viewModel = viewModel)
                        NavTab.TEAM -> TeamScreen(viewModel = viewModel)
                        NavTab.FILES -> FileManagerScreen(viewModel = viewModel)
                        NavTab.BILLING -> BillingScreen(viewModel = viewModel)
                        NavTab.SETTINGS -> SettingsScreen(viewModel = viewModel)
                        NavTab.SUPER_ADMIN -> SuperAdminScreen(viewModel = viewModel)
                        NavTab.LANDING_PAGE -> LandingPageScreen(viewModel = viewModel)
                    }
                }
            }
        }

        // Drawer Bottom Sheet
        if (showDrawerSheet) {
            ModalBottomSheet(
                onDismissRequest = { showDrawerSheet = false },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            ) {
                FullDrawerMenuSheet(
                    activeTab = activeTab,
                    onTabSelected = {
                        viewModel.setNavTab(it)
                        showDrawerSheet = false
                    },
                    onDismiss = { showDrawerSheet = false }
                )
            }
        }

        // Global Dialogs
        if (showNotifications) {
            NotificationsDialog(
                notifications = notifications,
                onMarkAllRead = { viewModel.markNotificationsRead() },
                onDismiss = { viewModel.showNotificationsSheet.value = false }
            )
        }

        if (showWorkspaceSwitcher) {
            WorkspaceSwitcherDialog(
                workspaces = if (allWorkspaces.isNotEmpty()) allWorkspaces else listOf(currentWorkspace),
                activeWorkspace = currentWorkspace,
                onSelectWorkspace = { viewModel.selectWorkspace(it) },
                onCreateWorkspace = { name, cat, size, goal ->
                    viewModel.createWorkspace(name, cat, size, goal)
                },
                onDismiss = { viewModel.showWorkspaceSwitcher.value = false }
            )
        }

        if (showAddCustomer) {
            AddCustomerDialog(
                onAdd = { name, phone, email, company, status, source, tags, notes ->
                    viewModel.addCustomer(name, phone, email, company, status, source, tags, notes)
                },
                onDismiss = { viewModel.showAddCustomerDialog.value = false }
            )
        }

        if (showAddLead) {
            AddLeadDialog(
                onAdd = { title, cName, company, phone, email, stage, score, valAmount, assigned, tags ->
                    viewModel.addLead(title, cName, company, phone, email, stage, score, valAmount, assigned, tags)
                },
                onDismiss = { viewModel.showAddLeadDialog.value = false }
            )
        }

        if (showCreateCampaign) {
            CreateCampaignDialog(
                onAdd = { name, type, audience, content ->
                    viewModel.addCampaign(name, type, audience, content)
                },
                onDismiss = { viewModel.showCreateCampaignDialog.value = false }
            )
        }

        if (showCreateAutomation) {
            CreateAutomationDialog(
                onAdd = { title, trigger, desc, steps ->
                    viewModel.addAutomation(title, trigger, desc, steps)
                },
                onDismiss = { viewModel.showCreateAutomationDialog.value = false }
            )
        }

        if (showInviteMember) {
            InviteMemberDialog(
                onAdd = { name, email, role, dept, perm ->
                    viewModel.inviteTeamMember(name, email, role, dept, perm)
                },
                onDismiss = { viewModel.showInviteMemberDialog.value = false }
            )
        }

        if (showAddFile) {
            UploadFileDialog(
                onUpload = { name, type, size, folder ->
                    viewModel.addFile(name, type, size, folder)
                },
                onDismiss = { viewModel.showAddFileDialog.value = false }
            )
        }
    }
}
