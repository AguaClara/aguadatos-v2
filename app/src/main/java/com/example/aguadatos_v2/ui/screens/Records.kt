package com.example.aguadatos_v2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aguadatos_v2.data.*
import com.example.aguadatos_v2.ui.components.BottomNavigationBar
import com.example.aguadatos_v2.ui.viewmodel.RecordViewModel
import java.text.SimpleDateFormat
import java.util.*

// Wrapper to unify all record types into one list
sealed class RecordEntry {
    abstract val timestamp: Long

    data class RawWater(val record: RawWaterRecord) : RecordEntry() {
        override val timestamp = record.timestamp
    }
    data class FilteredWater(val record: FilteredWaterRecord) : RecordEntry() {
        override val timestamp = record.timestamp
    }
    data class ClarifiedWater(val record: ClarifiedWaterRecord) : RecordEntry() {
        override val timestamp = record.timestamp
    }
    data class Coagulant(val record: CoagulantRecord) : RecordEntry() {
        override val timestamp = record.timestamp
    }
    data class Chlorine(val record: ChlorineRecord) : RecordEntry() {
        override val timestamp = record.timestamp
    }
}

fun Long.toDateKey(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toTimeString(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toDisplayDate(): String {
    val sdf = SimpleDateFormat("M/d", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toDayLabel(): String {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    val today = Calendar.getInstance()
    return when {
        cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> "TODAY"
        else -> SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(Date(this)).uppercase()
    }
}

@Composable
fun Records(
    recordViewModel: RecordViewModel = viewModel(),
    onHomeClick: () -> Unit = {},
    onRecordsClick: () -> Unit = {},
    onGraphsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val coagulantRecords by recordViewModel.coagulantRecords.collectAsState()
    val chlorineRecords by recordViewModel.chlorineRecords.collectAsState()
    val rawWaterRecords by recordViewModel.rawWaterRecords.collectAsState()
    val filteredWaterRecords by recordViewModel.filteredWaterRecords.collectAsState()
    val clarifiedWaterRecords by recordViewModel.clarifiedWaterRecords.collectAsState()

    // Combine all records into one sorted list
    val allEntries = remember(coagulantRecords, chlorineRecords, rawWaterRecords, filteredWaterRecords, clarifiedWaterRecords) {
        (rawWaterRecords.map { RecordEntry.RawWater(it) } +
                filteredWaterRecords.map { RecordEntry.FilteredWater(it) } +
                clarifiedWaterRecords.map { RecordEntry.ClarifiedWater(it) } +
                coagulantRecords.map { RecordEntry.Coagulant(it) } +
                chlorineRecords.map { RecordEntry.Chlorine(it) })
            .sortedByDescending { it.timestamp }
    }

    // Get unique dates that have entries
    val availableDates = remember(allEntries) {
        allEntries.map { it.timestamp.toDateKey() }.distinct().sorted()
    }

    var currentDateIndex by remember(availableDates) {
        mutableStateOf(if (availableDates.isNotEmpty()) availableDates.size - 1 else 0)
    }

    val currentDateKey = availableDates.getOrNull(currentDateIndex)

    val entriesForDate = remember(allEntries, currentDateKey) {
        allEntries.filter { it.timestamp.toDateKey() == currentDateKey }
    }

    // Find a timestamp for the current date for display
    val currentDateTimestamp = entriesForDate.firstOrNull()?.timestamp

    Scaffold(
        containerColor = Color(0xFFE4EFFC),
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onRecordsClick = onRecordsClick,
                onGraphsClick = onGraphsClick,
                onProfileClick = onProfileClick,
                currentScreen = "Records"
            )
        }
    ) {
        innerPadding -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE4EFFC))
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "RECORDS",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            // Date navigator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (currentDateIndex > 0) currentDateIndex-- },
                    enabled = currentDateIndex > 0
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = "Previous day",
                        tint = if (currentDateIndex > 0) Color.Black else Color.LightGray
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = currentDateTimestamp?.toDayLabel() ?: "NO ENTRIES",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (currentDateTimestamp != null) {
                        Text(
                            text = currentDateTimestamp.toDisplayDate(),
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }

                IconButton(
                    onClick = { if (currentDateIndex < availableDates.size - 1) currentDateIndex++ },
                    enabled = currentDateIndex < availableDates.size - 1
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Next day",
                        tint = if (currentDateIndex < availableDates.size - 1) Color.Black else Color.LightGray
                    )
                }
            }

            // Entries list
            if (entriesForDate.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No entries yet.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(entriesForDate) { entry ->
                        RecordEntryCard(entry)
                    }
                    item {
                        // Data synced footer
                        Text(
                            text = "ⓘ Data synced locally",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                        )
                    }
                }
            }
        }
        }
    }

}

@Composable
fun RecordEntryCard(entry: RecordEntry) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = entry.entryTitle(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    entry.entrySubtitle()?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Text(
                        text = "Timestamp: ${entry.timestamp.toTimeString()}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Color.DarkGray
                )
            }

            // Expanded details
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                ) {
                    HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    entry.entryDetails()
                }
            }
        }
    }
}

// Extension functions for entry display
fun RecordEntry.entryTitle(): String = when (this) {
    is RecordEntry.RawWater -> "Entry: Raw Water"
    is RecordEntry.FilteredWater -> "Entry: Filtered Water Turbidity"
    is RecordEntry.ClarifiedWater -> "Entry: Clarified Water Turbidity"
    is RecordEntry.Coagulant -> "Entry: Coagulant Dosage"
    is RecordEntry.Chlorine -> "Entry: Chlorine Dosage"
}

fun RecordEntry.entrySubtitle(): String? = when (this) {
    is RecordEntry.Coagulant -> "Chemical Flow Rate: ${record.chemFlowRate} mL/s"
    is RecordEntry.Chlorine -> "Chemical Flow Rate: ${record.chemFlowRate} mL/s"
    else -> null
}

@Composable
fun RecordEntry.entryDetails() {
    when (this) {
        is RecordEntry.RawWater -> {
            DetailRow("Raw Water Turbidity", record.rawWater)
        }
        is RecordEntry.FilteredWater -> {
            DetailRow("Filtered Water Turbidity", record.filteredWater)
        }
        is RecordEntry.ClarifiedWater -> {
            DetailRow("Clarified Water Turbidity", record.clarifiedWater)
        }
        is RecordEntry.Coagulant -> {
            Text("Calibration", fontWeight = FontWeight.Bold, fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp))
            DetailRow("Slider Position", "${record.sliderPos.toInt()} %")
            DetailRow("Inflow Rate", "${record.waterInflow} mL/s")
            DetailRow("Start Height", "${record.startVolume} mL")
            DetailRow("End Height", "${record.endVolume} mL")
            DetailRow("Time Elapsed", "${record.timeElapsed} s")
            DetailRow("Chemical Flow Rate", "${record.chemFlowRate} mL/s")
            Spacer(modifier = Modifier.height(6.dp))
            Text("Change Dose", fontWeight = FontWeight.Bold, fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp))
            DetailRow("Slider Overdose", "${record.sliderPosOverDose.toInt()} %")
            DetailRow("New Slider Position", "${record.newSliderPos.toInt()} %")
        }
        is RecordEntry.Chlorine -> {
            Text("Calibration", fontWeight = FontWeight.Bold, fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp))
            DetailRow("Slider Position", "${record.sliderPos.toInt()} %")
            DetailRow("Inflow Rate", "${record.waterInflow} mL/s")
            DetailRow("Start Height", "${record.startVolume} mL")
            DetailRow("End Height", "${record.endVolume} mL")
            DetailRow("Time Elapsed", "${record.timeElapsed} s")
            DetailRow("Chemical Flow Rate", "${record.chemFlowRate} mL/s")
            Spacer(modifier = Modifier.height(6.dp))
            Text("Change Dose", fontWeight = FontWeight.Bold, fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp))
            DetailRow("Slider Overdose", "${record.sliderPosOverDose.toInt()} %")
            DetailRow("New Slider Position", "${record.newSliderPos.toInt()} %")
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "• $label:",
            fontSize = 13.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value.ifEmpty { "---" },
            fontSize = 13.sp,
            color = Color.Black
        )
    }
}