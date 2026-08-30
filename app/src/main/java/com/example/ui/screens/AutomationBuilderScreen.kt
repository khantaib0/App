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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.data.model.AutomationWorkflow
import com.example.ui.theme.DeepPink
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftPink
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SaaSViewModel

@Composable
fun AutomationBuilderScreen(
    viewModel: SaaSViewModel,
    modifier: Modifier = Modifier
) {
    val automations by viewModel.automations.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }

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
                    Text("AI Automations", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Visual multi-step trigger workflows", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = { showCreateDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PureBlack, contentColor = PureWhite)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New Workflow", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        items(automations) { aut ->
            AutomationCardItem(
                automation = aut,
                onToggle = { viewModel.toggleAutomation(aut) },
                onAskAiFlow = {
                    viewModel.setNavTab(NavTab.AI_ASSISTANT)
                    viewModel.sendAiPrompt("Help me optimize this automation workflow: '${aut.title}' (Trigger: ${aut.triggerType}, Current Conversion: ${aut.conversionRate}). How can I make it convert higher?")
                }
            )
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }

    if (showCreateDialog) {
        CreateAutomationDialog(
            onAdd = { title, trigger, desc, steps ->
                viewModel.addAutomation(title, trigger, desc, steps)
                showCreateDialog = false
            },
            onDismiss = { showCreateDialog = false }
        )
    }
}

@Composable
fun AutomationCardItem(
    automation: AutomationWorkflow,
    onToggle: () -> Unit,
    onAskAiFlow: () -> Unit
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
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (automation.isEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = if (automation.isEnabled) DeepPink else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(automation.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Trigger: ${automation.triggerType}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Switch(
                    checked = automation.isEnabled,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = DeepPink,
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(automation.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

            // Visual Step Flow
            if (automation.stepsJson.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                val steps = automation.stepsJson.split("->").map { it.trim() }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text("Workflow Sequence:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = DeepPink)
                    Spacer(modifier = Modifier.height(6.dp))
                    steps.forEachIndexed { idx, step ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(DeepPink),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${idx + 1}", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(step, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                        }
                        if (idx < steps.size - 1) {
                            Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp).padding(start = 3.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${automation.totalTriggered} triggered • ${automation.conversionRate} conv.",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = DeepPink
                )

                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onAskAiFlow() },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = DeepPink, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("AI Flow Optimization", color = DeepPink, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CreateAutomationDialog(
    onAdd: (title: String, trigger: String, desc: String, steps: String) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var trigger by remember { mutableStateOf("New Lead Capture") }
    var desc by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("Trigger -> Wait 5m -> Send WhatsApp -> Follow-up") }

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
                    Text("New Automation Workflow", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = null) }
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Workflow Title *") }, modifier = Modifier.fillMaxWidth(), singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description & Goals *") }, modifier = Modifier.fillMaxWidth(), maxLines = 2, shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = steps, onValueChange = { steps = it }, label = { Text("Steps sequence (separated by ->)") }, modifier = Modifier.fillMaxWidth(), maxLines = 3, shape = RoundedCornerShape(12.dp))

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (title.isNotBlank() && desc.isNotBlank()) {
                            onAdd(title, trigger, desc, steps)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepPink)
                ) {
                    Text("Activate Workflow", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
