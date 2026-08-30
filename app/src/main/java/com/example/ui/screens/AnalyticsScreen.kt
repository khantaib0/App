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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Download
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.RevenueBarChart
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatCard
import com.example.ui.theme.AccentPink
import com.example.ui.theme.DeepPink
import com.example.ui.theme.PrimaryPink
import com.example.ui.theme.PureBlack
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SoftPink
import com.example.ui.viewmodel.NavTab
import com.example.ui.viewmodel.SaaSViewModel

@Composable
fun AnalyticsScreen(
    viewModel: SaaSViewModel,
    modifier: Modifier = Modifier
) {
    val timeFilter by viewModel.timeFilter.collectAsState()
    val context = LocalContext.current

    val channels = listOf(
        Triple("WhatsApp Broadcasts", "58% (₹4,90,000)", 0.58f),
        Triple("AI Abandoned Cart Recovery", "23% (₹1,94,000)", 0.23f),
        Triple("Organic Website Inbound", "12% (₹1,01,000)", 0.12f),
        Triple("Instagram & Meta Ads", "7% (₹60,000)", 0.07f)
    )

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
                    Text("Analytics & Reports", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Real-time revenue attribution & ROI", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(
                    onClick = {
                        Toast.makeText(context, "Exporting Q2 Analytics Report (CSV)...", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PureBlack, contentColor = PureWhite)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Export", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Time Filters
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("Today", "7 Days", "30 Days", "3 Months", "1 Year")) { filter ->
                    val isSelected = timeFilter == filter
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.setTimeFilter(filter) },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) DeepPink else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = filter,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        item {
            RevenueBarChart(timeFilter = timeFilter)
        }

        // Channel Attribution
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Revenue Channel Attribution", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("WhatsApp is your highest ROI channel with 14.2x ROAS", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(14.dp))

                    channels.forEach { (channel, stats, ratio) ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(channel, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                                Text(stats, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = DeepPink)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(ratio)
                                        .height(8.dp)
                                        .background(DeepPink, RoundedCornerShape(4.dp))
                                )
                            }
                        }
                    }
                }
            }
        }

        // AI Impact Metrics
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = DeepPink, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI Automation Impact", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Hours Saved / mo", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("142 hrs", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DeepPink)
                        }
                        Column {
                            Text("AI Copy Generated", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("84 campaigns", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Column {
                            Text("Extra GMV Recovered", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("₹1,94,000", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }
}
