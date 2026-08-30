package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepPink
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftPink
import com.example.ui.viewmodel.AuthState
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SaaSViewModel

@Composable
fun AuthAndOnboardingScreen(
    viewModel: SaaSViewModel,
    modifier: Modifier = Modifier
) {
    val authState by viewModel.authState.collectAsState()
    val onboardingStep by viewModel.onboardingStep.collectAsState()

    var authTab by remember { mutableStateOf(0) } // 0: Login, 1: Sign Up
    var email by remember { mutableStateOf("aisha.khan@company.com") }
    var password by remember { mutableStateOf("••••••••") }
    var fullName by remember { mutableStateOf("Aisha Khan") }

    // Onboarding fields
    var wsName by remember { mutableStateOf("Luxe Boutique Global") }
    var wsCategory by remember { mutableStateOf("E-commerce & Retail") }
    var wsSize by remember { mutableStateOf("6–20 members") }
    var wsGoal by remember { mutableStateOf("Automate WhatsApp Marketing & Scale Sales") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (authState == AuthState.LOGGED_OUT) {
            // Login / Sign up Card
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            Brush.linearGradient(listOf(DeepPink, PrimaryPink))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "AI SaaS OS",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "The Modern Business Operating System",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        TabRow(
                            selectedTabIndex = authTab,
                            containerColor = Color.Transparent,
                            indicator = { tabPositions ->
                                TabRowDefaults.SecondaryIndicator(
                                    Modifier.tabIndicatorOffset(tabPositions[authTab]),
                                    color = DeepPink
                                )
                            },
                            divider = {}
                        ) {
                            Tab(
                                selected = authTab == 0,
                                onClick = { authTab = 0 },
                                text = { Text("Sign In", fontWeight = FontWeight.Bold, color = if (authTab == 0) DeepPink else Color.Gray) }
                            )
                            Tab(
                                selected = authTab == 1,
                                onClick = { authTab = 1 },
                                text = { Text("Create Account", fontWeight = FontWeight.Bold, color = if (authTab == 1) DeepPink else Color.Gray) }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (authTab == 1) {
                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                label = { Text("Full Name") },
                                modifier = Modifier.fillMaxWidth(),
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = DeepPink) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Work Email") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = DeepPink) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = DeepPink) },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                if (authTab == 1) {
                                    viewModel.setAuthState(AuthState.ONBOARDING)
                                    viewModel.setOnboardingStep(1)
                                } else {
                                    viewModel.setAuthState(AuthState.LOGGED_IN)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PureBlack, contentColor = PureWhite)
                        ) {
                            Text(if (authTab == 0) "Sign In to Dashboard" else "Start 14-Day Free Trial", color = PureWhite, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = {
                                viewModel.setAuthState(AuthState.LOGGED_IN)
                                viewModel.setNavTab(NavTab.LANDING_PAGE)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("View Public Product Landing Page", fontSize = 12.sp)
                        }
                    }
                }
            }
        } else if (authState == AuthState.ONBOARDING) {
            // 5-Step Onboarding Wizard
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Step Indicator Dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { step ->
                        Box(
                            modifier = Modifier
                                .size(if (step == onboardingStep) 24.dp else 12.dp)
                                .clip(CircleShape)
                                .background(if (step <= onboardingStep) DeepPink else Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            if (step == onboardingStep) {
                                Text("$step", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        when (onboardingStep) {
                            1 -> {
                                Text("Step 1: Name Your Organization", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("What is your company or brand name?", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(16.dp))
                                OutlinedTextField(value = wsName, onValueChange = { wsName = it }, label = { Text("Brand / Business Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true, shape = RoundedCornerShape(12.dp))
                            }
                            2 -> {
                                Text("Step 2: Choose Industry", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("We personalize AI prompts for your niche.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(14.dp))
                                listOf("E-commerce & Retail", "Marketing Agency", "B2B SaaS / Tech", "Real Estate", "Healthcare / Clinics", "Hospitality / Food").forEach { cat ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable { wsCategory = cat },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (wsCategory == cat) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, if (wsCategory == cat) DeepPink else Color.Transparent)
                                    ) {
                                        Text(cat, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Medium, color = if (wsCategory == cat) DeepPink else MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                            3 -> {
                                Text("Step 3: Team Scale", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("How many seats do you need?", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(14.dp))
                                listOf("Solo Founder (1 seat)", "2–5 team members", "6–20 team members", "21–50 scaling", "50+ Enterprise").forEach { sz ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable { wsSize = sz },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (wsSize == sz) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, if (wsSize == sz) DeepPink else Color.Transparent)
                                    ) {
                                        Text(sz, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Medium, color = if (wsSize == sz) DeepPink else MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                            4 -> {
                                Text("Step 4: Primary Goal", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("What is your #1 growth focus?", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(14.dp))
                                listOf(
                                    "Scale WhatsApp broadcast sales & recovery",
                                    "Lead qualification & CRM deal pipeline",
                                    "Automated multi-step marketing funnels",
                                    "All-in-one unified dashboard"
                                ).forEach { gl ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable { wsGoal = gl },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (wsGoal == gl) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, if (wsGoal == gl) DeepPink else Color.Transparent)
                                    ) {
                                        Text(gl, modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Medium, color = if (wsGoal == gl) DeepPink else MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                            5 -> {
                                Text("Step 5: Ready to Launch! 🚀", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("Your AI workspace is ready for $wsName", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(16.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp))
                                        .padding(16.dp)
                                ) {
                                    Column {
                                        Text("✨ Workspace: $wsName", fontWeight = FontWeight.Bold, color = DeepPink)
                                        Text("🏢 Industry: $wsCategory", style = MaterialTheme.typography.bodySmall)
                                        Text("👥 Scale: $wsSize", style = MaterialTheme.typography.bodySmall)
                                        Text("🎯 Focus: $wsGoal", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (onboardingStep > 1) {
                                OutlinedButton(
                                    onClick = { viewModel.setOnboardingStep(onboardingStep - 1) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Back")
                                }
                            }
                            Button(
                                onClick = {
                                    if (onboardingStep < 5) {
                                        viewModel.setOnboardingStep(onboardingStep + 1)
                                    } else {
                                        viewModel.createWorkspace(wsName, wsCategory, wsSize, wsGoal)
                                        viewModel.setAuthState(AuthState.LOGGED_IN)
                                        viewModel.setNavTab(NavTab.DASHBOARD)
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = DeepPink)
                            ) {
                                Text(if (onboardingStep < 5) "Continue" else "Launch Workspace 🚀", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
