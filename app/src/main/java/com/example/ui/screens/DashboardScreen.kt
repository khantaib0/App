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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.LeadFunnelCard
import com.example.ui.components.RevenueBarChart
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AccentPink
import com.example.ui.theme.DeepPink
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftPink
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SaaSViewModel

@Composable
fun DashboardScreen(
    viewModel: SaaSViewModel,
    modifier: Modifier = Modifier
) {
    val currentWorkspace by viewModel.activeWorkspace.collectAsState()
    val timeFilter by viewModel.timeFilter.collectAsState()
    val customers by viewModel.customers.collectAsState()
    val leads by viewModel.leads.collectAsState()
    val campaigns by viewModel.campaigns.collectAsState()
    val automations by viewModel.automations.collectAsState()

    val timeFilters = listOf("Today", "7 Days", "30 Days", "3 Months", "1 Year")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Hero Welcome Banner with Pink Gradient Accent
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Welcome back, Aisha! ✨",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${currentWorkspace.name} is running at +18.4% growth this month.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Button(
                                onClick = { viewModel.setNavTab(NavTab.AI_ASSISTANT) },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PureBlack,
                                    contentColor = PureWhite
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = PrimaryPink,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Ask AI", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Timeframe Pill Selector
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(timeFilters) { filter ->
                                val isSelected = timeFilter == filter
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .clickable { viewModel.setTimeFilter(filter) },
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (isSelected) DeepPink else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = filter,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Key Metric Cards (2x2 Grid)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Total Customers",
                    value = "12,450",
                    trend = "+12.8%",
                    isPositive = true,
                    subtitle = "vs last month",
                    icon = Icons.Default.People,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.setNavTab(NavTab.CUSTOMERS) }
                )
                StatCard(
                    title = "New Leads",
                    value = "1,240",
                    trend = "+24.5%",
                    isPositive = true,
                    subtitle = "92 High score",
                    icon = Icons.Default.FilterAlt,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.setNavTab(NavTab.LEADS) }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Conversions",
                    value = "342",
                    trend = "+18.2%",
                    isPositive = true,
                    subtitle = "34.2% WhatsApp ROI",
                    icon = Icons.Default.TrendingUp,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.setNavTab(NavTab.CAMPAIGNS) }
                )
                StatCard(
                    title = "Revenue",
                    value = "₹8,45,000",
                    trend = "+18.4%",
                    isPositive = true,
                    subtitle = "₹2,470 avg order",
                    icon = Icons.Default.CurrencyRupee,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.setNavTab(NavTab.ANALYTICS) }
                )
            }
        }

        // Charts Section
        item {
            RevenueBarChart(timeFilter = timeFilter)
        }

        item {
            LeadFunnelCard()
        }

        // Quick Actions Grid
        item {
            SectionHeader(title = "Quick Actions", subtitle = "One-tap business workflows")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionItem(
                    title = "New Lead",
                    icon = Icons.Default.Add,
                    color = DeepPink,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.showAddLeadDialog.value = true
                }
                QuickActionItem(
                    title = "Broadcast",
                    icon = Icons.Default.Chat,
                    color = Color(0xFF25D366),
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.setNavTab(NavTab.WHATSAPP)
                }
                QuickActionItem(
                    title = "Campaign",
                    icon = Icons.Default.Campaign,
                    color = Color(0xFF3B82F6),
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.showCreateCampaignDialog.value = true
                }
                QuickActionItem(
                    title = "AI Copilot",
                    icon = Icons.Default.AutoAwesome,
                    color = DeepPink,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.setNavTab(NavTab.AI_ASSISTANT)
                }
            }
        }

        // Recent Activity & High-Value Leads
        item {
            SectionHeader(
                title = "High-Score Leads",
                subtitle = "Deals ready for proposal close",
                actionText = "View All",
                onActionClick = { viewModel.setNavTab(NavTab.LEADS) }
            )
        }

        items(leads.take(3)) { lead ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { viewModel.selectLead(lead) },
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
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = lead.customerName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            StatusBadge(status = lead.stage)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${lead.title} • ${lead.company}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "₹${lead.estimatedValue.toInt()}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DeepPink
                        )
                        Text(
                            text = "Score: ${lead.score}/100",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (lead.score >= 80) DeepPink else Color.Gray
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun QuickActionItem(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 11.sp
            )
        }
    }
}
