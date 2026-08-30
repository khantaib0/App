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
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Campaign
import com.example.ui.components.StatusBadge
import com.example.ui.theme.DeepPink
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftPink
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SaaSViewModel

@Composable
fun CampaignsScreen(
    viewModel: SaaSViewModel,
    modifier: Modifier = Modifier
) {
    val campaigns by viewModel.campaigns.collectAsState()
    var selectedChannel by remember { mutableStateOf("All") }
    val channels = listOf("All", "WhatsApp", "Email", "SMS", "AI Campaign")

    var showCreateDialog by remember { mutableStateOf(false) }

    val filteredCampaigns = campaigns.filter {
        selectedChannel == "All" || it.type.equals(selectedChannel, ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Marketing Campaigns", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Multi-channel broadcast & AI triggers", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = { showCreateDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PureBlack, contentColor = PureWhite)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Create Campaign", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Channel filter chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(channels) { channel ->
                    val isSelected = selectedChannel == channel
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { selectedChannel = channel },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) DeepPink else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = channel,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Campaign Cards
        items(filteredCampaigns) { cmp ->
            CampaignCardItem(
                campaign = cmp,
                onAskAiOptimization = {
                    viewModel.setNavTab(NavTab.AI_ASSISTANT)
                    viewModel.sendAiPrompt("Analyze campaign metrics for '${cmp.name}' (Sent: ${cmp.sent}, Opened: ${cmp.opened}, Clicked: ${cmp.clicked}, Revenue: ₹${cmp.revenue.toInt()}) and give 3 actionable steps to increase conversions.")
                }
            )
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }

    if (showCreateDialog) {
        CreateCampaignDialog(
            onAdd = { name, type, audience, content ->
                viewModel.addCampaign(name, type, audience, content)
                showCreateDialog = false
            },
            onDismiss = { showCreateDialog = false }
        )
    }
}

@Composable
fun CampaignCardItem(
    campaign: Campaign,
    onAskAiOptimization: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val icon = when (campaign.type) {
                        "WhatsApp" -> Icons.Default.Chat
                        "Email" -> Icons.Default.Email
                        "SMS" -> Icons.Default.Message
                        else -> Icons.Default.AutoAwesome
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = DeepPink, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(campaign.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("${campaign.type} • ${campaign.audience}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                StatusBadge(status = campaign.status)
            }

            if (campaign.messageContent.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = campaign.messageContent,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(10.dp))

            // Metrics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricPill(label = "Sent", value = "${campaign.sent}")
                val openRate = if (campaign.sent > 0) "${(campaign.opened * 100 / campaign.sent)}%" else "0%"
                MetricPill(label = "Open Rate", value = openRate)
                val ctr = if (campaign.opened > 0) "${(campaign.clicked * 100 / campaign.opened)}%" else "0%"
                MetricPill(label = "CTR", value = ctr)
                MetricPill(label = "Revenue", value = "₹${campaign.revenue.toInt()}", isHighlight = true)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onAskAiOptimization() },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = DeepPink, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("AI Optimize Copy", color = DeepPink, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun MetricPill(label: String, value: String, isHighlight: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = if (isHighlight) DeepPink else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun CreateCampaignDialog(
    onAdd: (name: String, type: String, audience: String, content: String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("WhatsApp") }
    var audience by remember { mutableStateOf("All VIP Customers (4,500)") }
    var content by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Create Campaign", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Campaign Name *") }, modifier = Modifier.fillMaxWidth(), singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Campaign Message Copy *") }, modifier = Modifier.fillMaxWidth(), maxLines = 4, shape = RoundedCornerShape(12.dp))

                Spacer(modifier = Modifier.height(10.dp))
                Text("Channel:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                    items(listOf("WhatsApp", "Email", "SMS", "AI Campaign")) { ch ->
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { type = ch },
                            shape = RoundedCornerShape(8.dp),
                            color = if (type == ch) DeepPink else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(ch, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp), color = if (type == ch) Color.White else MaterialTheme.colorScheme.onSurface, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (name.isNotBlank() && content.isNotBlank()) {
                            onAdd(name, type, audience, content)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepPink)
                ) {
                    Text("Launch Campaign", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
