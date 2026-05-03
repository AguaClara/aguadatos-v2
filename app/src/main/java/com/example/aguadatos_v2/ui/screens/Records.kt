package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aguadatos_v2.data.*
import com.example.aguadatos_v2.ui.viewmodel.RecordViewModel
import java.text.SimpleDateFormat
import java.util.*

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy  hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun Records(recordViewModel: RecordViewModel = viewModel()) {
    val tabs = listOf("Raw Water", "Coagulant", "Filtered Water", "Clarified Water", "Chlorine")
    var tabIndex by remember { mutableStateOf(0) }

    val coagulantRecords by recordViewModel.coagulantRecords.collectAsState()
    val chlorineRecords by recordViewModel.chlorineRecords.collectAsState()
    val rawWaterRecords by recordViewModel.rawWaterRecords.collectAsState()
    val filteredWaterRecords by recordViewModel.filteredWaterRecords.collectAsState()
    val clarifiedWaterRecords by recordViewModel.clarifiedWaterRecords.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.White,
            contentColor = Color(0xFF3C89E1)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(title, fontSize = 13.sp) }
                )
            }
        }

        when (tabIndex) {
            0 -> RawWaterRecordsList(rawWaterRecords)
            1 -> CoagulantRecordsList(coagulantRecords)
            2 -> FilteredWaterRecordsList(filteredWaterRecords)
            3 -> ClarifiedWaterRecordsList(clarifiedWaterRecords)
            4 -> ChlorineRecordsList(chlorineRecords)
        }
    }
}

@Composable
fun RawWaterRecordsList(records: List<RawWaterRecord>) {
    var selected by remember { mutableStateOf<RawWaterRecord?>(null) }
    RecordList(records = records, timestamp = { it.timestamp }, onClick = { selected = it })
    selected?.let {
        RecordDialog(title = "Raw Water Entry", onDismiss = { selected = null }) {
            DialogRow("Raw Water", it.rawWater)
        }
    }
}

@Composable
fun FilteredWaterRecordsList(records: List<FilteredWaterRecord>) {
    var selected by remember { mutableStateOf<FilteredWaterRecord?>(null) }
    RecordList(records = records, timestamp = { it.timestamp }, onClick = { selected = it })
    selected?.let {
        RecordDialog(title = "Filtered Water Entry", onDismiss = { selected = null }) {
            DialogRow("Filtered Water", it.filteredWater)
        }
    }
}

@Composable
fun ClarifiedWaterRecordsList(records: List<ClarifiedWaterRecord>) {
    var selected by remember { mutableStateOf<ClarifiedWaterRecord?>(null) }
    RecordList(records = records, timestamp = { it.timestamp }, onClick = { selected = it })
    selected?.let {
        RecordDialog(title = "Clarified Water Entry", onDismiss = { selected = null }) {
            DialogRow("Clarified Water", it.clarifiedWater)
        }
    }
}

@Composable
fun CoagulantRecordsList(records: List<CoagulantRecord>) {
    var selected by remember { mutableStateOf<CoagulantRecord?>(null) }
    RecordList(records = records, timestamp = { it.timestamp }, onClick = { selected = it })
    selected?.let {
        RecordDialog(title = "Coagulant Entry", onDismiss = { selected = null }) {
            DialogRow("Slider Position", "${it.sliderPos.toInt()}%")
            DialogRow("New Slider Position", "${it.newSliderPos.toInt()}%")
            DialogRow("Water Inflow", it.waterInflow)
            DialogRow("Start Volume", it.startVolume)
            DialogRow("End Volume", it.endVolume)
            DialogRow("Time Elapsed", it.timeElapsed)
            DialogRow("Slider Overdose", "${it.sliderPosOverDose.toInt()}%")
            DialogRow("Chem Flow Rate", it.chemFlowRate)
        }
    }
}

@Composable
fun ChlorineRecordsList(records: List<ChlorineRecord>) {
    var selected by remember { mutableStateOf<ChlorineRecord?>(null) }
    RecordList(records = records, timestamp = { it.timestamp }, onClick = { selected = it })
    selected?.let {
        RecordDialog(title = "Chlorine Entry", onDismiss = { selected = null }) {
            DialogRow("Slider Position", "${it.sliderPos.toInt()}%")
            DialogRow("New Slider Position", "${it.newSliderPos.toInt()}%")
            DialogRow("Water Inflow", it.waterInflow)
            DialogRow("Start Volume", it.startVolume)
            DialogRow("End Volume", it.endVolume)
            DialogRow("Time Elapsed", it.timeElapsed)
            DialogRow("Slider Overdose", "${it.sliderPosOverDose.toInt()}%")
            DialogRow("Chem Flow Rate", it.chemFlowRate)
        }
    }
}

@Composable
fun <T> RecordList(
    records: List<T>,
    timestamp: (T) -> Long,
    onClick: (T) -> Unit
) {
    if (records.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No records yet.", color = Color.Gray)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(records as List<Any>) { record ->
                @Suppress("UNCHECKED_CAST")
                val typedRecord = record as T
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(typedRecord) },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formatTimestamp(timestamp(typedRecord)),
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "View →",
                            fontSize = 13.sp,
                            color = Color(0xFF3C89E1),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecordDialog(title: String, onDismiss: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3C89E1),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                content()
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C89E1)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun DialogRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Text(text = value.ifEmpty { "—" }, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}