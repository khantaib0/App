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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Person
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
import com.example.data.model.Lead
import com.example.ui.components.ScoreBadge
import com.example.ui.components.StatusBadge
import com.example.ui.theme.DeepPink
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftPink
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SaaSViewModel

@Composable
fun LeadPipelineScreen(
    viewModel: SaaSViewModel,
    modifier: Modifier = Modifier
) {
    val leads by viewModel.leads.collectAsState()
    val selectedLead by viewModel.selectedLead.collectAsState()

    var activeStage by remember { mutableStateOf("All") }
    val stages = listOf("All", "New", "Contacted", "Interested", "Qualified", "Proposal", "Won", "Lost")

    val filteredLeads = leads.filter { lead ->
        activeStage == "All" || lead.stage.equals(activeStage, ignoreCase = true)
    }

    val totalPipelineValue = leads.filter { it.stage != "Lost" }.sumOf { it.estimatedValue }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(6.dp))
                // Pipeline Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Leads Pipeline",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Total Pipeline: ₹${totalPipelineValue.toInt()} (${leads.size} deals)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { viewModel.showAddLeadDialog.value = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PureBlack, contentColor = PureWhite)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Lead", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Kanban Stages Horizontal Bar
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(stages) { stage ->
                        val count = if (stage == "All") leads.size else leads.count { it.stage.equals(stage, ignoreCase = true) }
                        val isSelected = activeStage == stage

                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { activeStage = stage },
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) DeepPink else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stage,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color.White.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "$count",
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Leads List
            items(filteredLeads) { lead ->
                LeadCardItem(
                    lead = lead,
                    onClick = { viewModel.selectLead(lead) },
                    onAdvanceStage = {
                        val nextStage = when (lead.stage) {
                            "New" -> "Contacted"
                            "Contacted" -> "Interested"
                            "Interested" -> "Qualified"
                            "Qualified" -> "Proposal"
                            "Proposal" -> "Won"
                            else -> "Won"
                        }
                        viewModel.updateLeadStage(lead, nextStage)
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(60.dp)) }
        }

        // Lead Details Dialog
        if (selectedLead != null) {
            LeadDetailDialog(
                lead = selectedLead!!,
                onDismiss = { viewModel.selectLead(null) },
                onUpdateStage = { newStage ->
                    viewModel.updateLeadStage(selectedLead!!, newStage)
                },
                onDelete = {
                    viewModel.deleteLead(selectedLead!!)
                },
                onAskAiStrategy = {
                    viewModel.selectLead(null)
                    viewModel.setNavTab(NavTab.AI_ASSISTANT)
                    viewModel.sendAiPrompt("Analyze lead deal '${selectedLead!!.title}' for ${selectedLead!!.customerName} (${selectedLead!!.company}, Value: ₹${selectedLead!!.estimatedValue.toInt()}) and draft a winning close strategy & WhatsApp follow-up.")
                }
            )
        }
    }
}

@Composable
fun LeadCardItem(
    lead: Lead,
    onClick: () -> Unit,
    onAdvanceStage: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lead.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${lead.customerName} • ${lead.company}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                ScoreBadge(score = lead.score)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "₹${lead.estimatedValue.toInt()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepPink
                    )
                    Text(
                        text = "Assigned: ${lead.assignedTo}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusBadge(status = lead.stage)
                    if (lead.stage != "Won" && lead.stage != "Lost") {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onAdvanceStage() },
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Next", color = DeepPink, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(2.dp))
                                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = DeepPink, modifier = Modifier.size(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LeadDetailDialog(
    lead: Lead,
    onDismiss: () -> Unit,
    onUpdateStage: (String) -> Unit,
    onDelete: () -> Unit,
    onAskAiStrategy: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(lead.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("${lead.customerName} (${lead.company})", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(10.dp))

                DetailRow(label = "Estimated Value", value = "₹${lead.estimatedValue.toInt()}")
                DetailRow(label = "Lead AI Score", value = "⚡ ${lead.score} / 100")
                DetailRow(label = "Current Stage", value = lead.stage)
                DetailRow(label = "Assigned Rep", value = lead.assignedTo)
                DetailRow(label = "Follow-up", value = lead.followUpDate)
                DetailRow(label = "Contact Phone", value = lead.phone)

                Spacer(modifier = Modifier.height(8.dp))
                Text("Lead Notes:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Text(lead.notes, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(modifier = Modifier.height(14.dp))

                // Stage Advancement Buttons
                Text("Move Pipeline Stage:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    items(listOf("New", "Contacted", "Interested", "Qualified", "Proposal", "Won", "Lost")) { st ->
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onUpdateStage(st) },
                            shape = RoundedCornerShape(8.dp),
                            color = if (lead.stage == st) DeepPink else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = st,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                color = if (lead.stage == st) Color.White else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onAskAiStrategy,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PureBlack)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("AI Strategy", color = PureWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onDelete,
                        modifier = Modifier.weight(0.8f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Delete", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
