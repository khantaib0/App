package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.UpgradePlanDialog
import com.example.ui.theme.DeepPink
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftPink
import com.example.ui.viewmodel.SaaSViewModel

@Composable
fun BillingScreen(
    viewModel: SaaSViewModel,
    modifier: Modifier = Modifier
) {
    val currentWorkspace by viewModel.activeWorkspace.collectAsState()
    val plans = viewModel.plans
    val invoices = viewModel.invoices
    val context = LocalContext.current

    val currentPlan = plans.find { it.id == currentWorkspace.planId } ?: plans[1]

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text("Billing & Subscriptions", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Manage your plan tiers, usage credits & invoices", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        // Active Plan Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Surface(shape = RoundedCornerShape(8.dp), color = DeepPink) {
                                    Text("CURRENT ACTIVE PLAN", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(currentPlan.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                                Text("${currentPlan.price}${currentPlan.period}", style = MaterialTheme.typography.titleMedium, color = DeepPink, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { viewModel.showUpgradePlanDialog.value = true },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PureBlack, contentColor = PureWhite)
                            ) {
                                Text("Upgrade Plan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(currentPlan.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(12.dp))
                        // Quota usage progress bars
                        UsageProgressBar(label = "WhatsApp Messages (8,420 / 10,000)", ratio = 0.84f)
                        Spacer(modifier = Modifier.height(6.dp))
                        UsageProgressBar(label = "AI Copilot Generations (320 / 1,000)", ratio = 0.32f)
                        Spacer(modifier = Modifier.height(6.dp))
                        UsageProgressBar(label = "Team Seats (4 / 10 Active)", ratio = 0.40f)
                    }
                }
            }
        }

        // Available Plans Section
        item {
            Text("Available SaaS Plans", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        items(plans) { plan ->
            val isCurrent = plan.id == currentWorkspace.planId
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(
                    if (plan.isPopular) 2.dp else 1.dp,
                    if (plan.isPopular) DeepPink else MaterialTheme.colorScheme.outline
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(plan.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            if (plan.isPopular) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(shape = RoundedCornerShape(8.dp), color = DeepPink) {
                                    Text("POPULAR", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }
                        Text("${plan.price}${plan.period}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = DeepPink)
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(plan.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(10.dp))
                    plan.features.forEach { feat ->
                        Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = DeepPink, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(feat, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.upgradePlan(plan.id) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCurrent) Color.Gray else if (plan.isPopular) DeepPink else PureBlack
                        ),
                        enabled = !isCurrent
                    ) {
                        Text(if (isCurrent) "Current Plan" else "Select ${plan.name}", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Invoices History
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text("Invoice History", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        items(invoices) { inv ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(inv.invoiceNumber, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        Text("${inv.planName} • ${inv.date}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(inv.amount, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = DeepPink)
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { Toast.makeText(context, "Downloaded ${inv.invoiceNumber}.pdf", Toast.LENGTH_SHORT).show() }) {
                            Icon(Icons.Default.Download, contentDescription = "Download", tint = DeepPink, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }

    if (viewModel.showUpgradePlanDialog.collectAsState().value) {
        UpgradePlanDialog(
            plans = plans,
            currentPlanId = currentWorkspace.planId,
            onSelectPlan = { viewModel.upgradePlan(it) },
            onDismiss = { viewModel.showUpgradePlanDialog.value = false }
        )
    }
}

@Composable
fun UsageProgressBar(label: String, ratio: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
            Text("${(ratio * 100).toInt()}%", fontSize = 11.sp, color = DeepPink, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(3.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(ratio)
                    .height(6.dp)
                    .background(DeepPink, RoundedCornerShape(3.dp))
            )
        }
    }
}
