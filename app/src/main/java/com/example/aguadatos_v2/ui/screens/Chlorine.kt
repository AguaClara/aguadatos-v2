package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R
import com.example.aguadatos_v2.ui.components.BottomNavigationBar
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.ui.text.googlefonts.GoogleFont
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/*
* Chlorine composable:
* Intent & Design:
*  - screen to input chlorine data in mg/L
*  - only allows digits and decimal points typed
* */

data class ChlorineSubmission(
    val sliderPos: Float,
    val newSliderPos: Float,
    val waterInflow: String,
    val startVolume: String,
    val endVolume: String,
    val timeElapsed: String,
    val sliderPosOverDose: Float,
    val chemFlowRate: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun Chlorine(
    onBackClick: () -> Unit,
    onSubmitClick: (ChlorineSubmission) -> Unit,
    onHomeClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onGraphsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Calibration", "Change Dose")
    var sliderPos by remember { mutableStateOf(0f) }
    var sliderPosOverDose by remember { mutableStateOf(0f) }
    var waterInflow by remember { mutableStateOf("") }
    var startVolume by remember { mutableStateOf("") }
    var endVolume by remember { mutableStateOf("") }
    var timeElapsed by remember { mutableStateOf("") }
    var accumulatedMillis by remember { mutableStateOf(0L) }
    var startTimeMillis by remember { mutableStateOf<Long?>(null) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var chemFlowRate by remember { mutableStateOf("") }
    var targetChlorineDose by remember { mutableStateOf("") }
    var newSliderPos by remember { mutableStateOf(0f) }
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            if (startTimeMillis == null) {
                startTimeMillis = System.currentTimeMillis()
            }
            while (isTimerRunning) {
                val base = accumulatedMillis + (System.currentTimeMillis() - (startTimeMillis ?: System.currentTimeMillis()))
                val secondsPart = base / 1000
                val centisPart = ((base % 1000) / 10).toInt().toString().padStart(2, '0')
                timeElapsed = "$secondsPart:$centisPart"
                delay(50)
            }
        }
    }
    Scaffold(
        containerColor = Color(0xffe4effc),
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onRecordsClick = onRecordsClick,
                onGraphsClick = onGraphsClick,
                onProfileClick = onProfileClick,
                currentScreen = "Home"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBackClick() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.chlorine_dosage_caps),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(44.dp))
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = tabIndex == index
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    color = if (isSelected) Color(0xFF3C89E1) else Color.Transparent,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .clickable { tabIndex = index }
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) Color.Black else Color.Transparent,
                                    shape = RoundedCornerShape(18.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                color = if (isSelected) Color.White else Color.Black,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            when (tabIndex) {
                0 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp))
                            .background(Color.Transparent)
                            .padding(0.dp)
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val cornerRadius = 18.dp.toPx()
                                drawRoundRect(
                                    color = Color.Black,
                                    topLeft = Offset(0f, 0f),
                                    size = Size(size.width, size.height),
                                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                                    style = Stroke(width = strokeWidth)
                                )
                                drawRect(
                                    color = Color(0xffe4effc),
                                    topLeft = Offset(0f, 0f),
                                    size = Size(size.width, strokeWidth + 12.dp.toPx())
                                )
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height - cornerRadius),
                                    strokeWidth = strokeWidth,
                                )
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width, size.height - cornerRadius),
                                    strokeWidth = strokeWidth,
                                )
                            }
                            .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp))
                            .background(Color.Transparent),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = stringResource(R.string.slider_position),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(26.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .fillMaxWidth()
                            ) {
                                IconButton(
                                    onClick = {
                                        sliderPos = (sliderPos - 1f).coerceIn(0f, 100f)
                                    },
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(28.dp)
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "Decrease",
                                        tint = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.width(22.dp))
                                Text(
                                    text = "${sliderPos.toInt()}%",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.width(22.dp))
                                IconButton(
                                    onClick = {
                                        sliderPos = (sliderPos + 1f).coerceIn(0f, 100f)
                                    },
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(28.dp)
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Increase",
                                        tint = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Slider(
                                value = sliderPos,
                                onValueChange = { sliderPos = it.roundToInt().toFloat() },
                                valueRange = 0f..100f,
                                steps = 19,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                                    .height(40.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF3C89E1),
                                    activeTrackColor = Color(0xFF3C89E1),
                                    inactiveTrackColor = Color.White
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "0%")
                                Spacer(modifier = Modifier.weight(0.12f))
                                Text(text = "30%")
                                Spacer(modifier = Modifier.weight(0.30f))
                                Text(text = "100%")
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.water_inflow_rate),
                                        modifier = Modifier.width(120.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    OutlinedTextField(
                                        value = waterInflow,
                                        onValueChange = { waterInflow = it },
                                        shape = RoundedCornerShape(8.dp),
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = Color.White,
                                            unfocusedContainerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .width(80.dp)
                                            .height(50.dp)
                                    )
                                    Text(text = stringResource(R.string.liters_per_second), fontWeight = FontWeight.Bold)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.volume),
                                        modifier = Modifier.width(60.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    OutlinedTextField(
                                        value = startVolume,
                                        onValueChange = { startVolume = it },
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = Color.White,
                                            unfocusedContainerColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .width(80.dp)
                                            .height(50.dp)
                                    )
                                    Text(text = "mL", fontWeight = FontWeight.Bold)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.time_elapsed),
                                        modifier = Modifier.width(100.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    OutlinedTextField(
                                        value = timeElapsed,
                                        onValueChange = {
                                            timeElapsed = it
                                            if (!isTimerRunning) {
                                                val parts = it.split(":")
                                                accumulatedMillis = if (parts.size == 2) {
                                                    val sec = parts[0].toLongOrNull() ?: 0L
                                                    val cs = parts[1].toLongOrNull() ?: 0L
                                                    sec * 1000L + (cs.coerceIn(0L, 99L) * 10L)
                                                } else {
                                                    (it.toLongOrNull() ?: 0L) * 1000L
                                                }
                                            }
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = Color.White,
                                            unfocusedContainerColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .width(80.dp)
                                            .height(45.dp)
                                    )
                                    Text(text = "s", fontWeight = FontWeight.Bold)
                                    Button(
                                        onClick = {
                                            if (isTimerRunning) {
                                                val endBase = accumulatedMillis + (System.currentTimeMillis() - (startTimeMillis ?: System.currentTimeMillis()))
                                                accumulatedMillis = endBase
                                                startTimeMillis = null
                                                isTimerRunning = false
                                            } else {
                                                val parts = timeElapsed.split(":")
                                                accumulatedMillis = if (parts.size == 2) {
                                                    val sec = parts[0].toLongOrNull() ?: 0L
                                                    val cs = parts[1].toLongOrNull() ?: 0L
                                                    sec * 1000L + (cs.coerceIn(0L, 99L) * 10L)
                                                } else {
                                                    (timeElapsed.toLongOrNull() ?: 0L) * 1000L
                                                }
                                                startTimeMillis = System.currentTimeMillis()
                                                isTimerRunning = true
                                            }
                                        },
                                        modifier = Modifier
                                            .size(40.dp)
                                            .border(1.5.dp, Color.Black, RoundedCornerShape(8.dp)),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isTimerRunning) Color(0xFF3C89E1) else Color.White,
                                            contentColor = if (isTimerRunning) Color.White else Color(0xFF3C89E1)
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(4.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.timer),
                                            contentDescription = "Timer",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp),
                                    thickness = 1.dp,
                                    color = Color.Black
                                )
                                Text(
                                    text = stringResource(R.string.results),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = stringResource(R.string.chemical_dose) + " ---",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = stringResource(R.string.chemical_flow_rate) + " ---$chemFlowRate",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp))
                            .background(Color.Transparent)
                            .padding(0.dp)
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val cornerRadius = 18.dp.toPx()
                                drawRoundRect(
                                    color = Color.Black,
                                    topLeft = Offset(0f, 0f),
                                    size = Size(size.width, size.height),
                                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                                    style = Stroke(width = strokeWidth)
                                )
                                drawRect(
                                    color = Color(0xffe4effc),
                                    topLeft = Offset(0f, 0f),
                                    size = Size(size.width, strokeWidth + 12.dp.toPx())
                                )
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height - cornerRadius),
                                    strokeWidth = strokeWidth,
                                )
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width, size.height - cornerRadius),
                                    strokeWidth = strokeWidth,
                                )
                            }
                            .clip(RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp))
                            .background(Color.Transparent),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 12.dp, start = 4.dp, end = 4.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.chemical_flow_rate) + " $chemFlowRate mL/s",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = stringResource(R.string.slider_position_over_dose),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(22.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .fillMaxWidth()
                            ) {
                                IconButton(
                                    onClick = {
                                        sliderPosOverDose = (sliderPosOverDose - 1f).coerceIn(0f, 100f)
                                    },
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(28.dp)
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "Decrease",
                                        tint = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.width(22.dp))
                                Text(
                                    text = "${sliderPosOverDose.toInt()}%",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.width(22.dp))
                                IconButton(
                                    onClick = {
                                        sliderPosOverDose = (sliderPosOverDose + 1f).coerceIn(0f, 100f)
                                    },
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(28.dp)
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Increase",
                                        tint = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Slider(
                                value = sliderPosOverDose,
                                onValueChange = { sliderPosOverDose = it.roundToInt().toFloat() },
                                valueRange = 0f..100f,
                                steps = 19,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                                    .height(40.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF3C89E1),
                                    activeTrackColor = Color(0xFF3C89E1),
                                    inactiveTrackColor = Color.White
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "0%")
                                Spacer(modifier = Modifier.weight(0.12f))
                                Text(text = "30%")
                                Spacer(modifier = Modifier.weight(0.30f))
                                Text(text = "100%")
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Target Chlorine Dose:",
                                    modifier = Modifier.width(200.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                OutlinedTextField(
                                    value = targetChlorineDose,
                                    onValueChange = { targetChlorineDose = it },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = Color.White,
                                        focusedIndicatorColor = Color.Black
                                    ),
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(30.dp)
                                        .padding(end = 8.dp)
                                )
                                Text(
                                    text = "mg/L",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                thickness = 1.dp,
                                color = Color.Black
                            )
                            Text(
                                text = "Results",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                            Text(
                                text = "New Slider Position:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 28.dp)
                            )
                            Spacer(modifier = Modifier.height(22.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .fillMaxWidth()
                            ) {
                                IconButton(
                                    onClick = {
                                        newSliderPos = (newSliderPos - 1f).coerceIn(0f, 100f)
                                    },
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(28.dp)
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "Decrease",
                                        tint = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.width(22.dp))
                                Text(
                                    text = "${newSliderPos.toInt()}%",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.width(22.dp))
                                IconButton(
                                    onClick = {
                                        newSliderPos = (newSliderPos + 1f).coerceIn(0f, 100f)
                                    },
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(28.dp)
                                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Increase",
                                        tint = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Slider(
                                value = newSliderPos,
                                onValueChange = { newSliderPos = it.roundToInt().toFloat() },
                                valueRange = 0f..100f,
                                steps = 19,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                                    .height(40.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF3C89E1),
                                    activeTrackColor = Color(0xFF3C89E1),
                                    inactiveTrackColor = Color.White
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "0%")
                                Spacer(modifier = Modifier.weight(0.12f))
                                Text(text = "30%")
                                Spacer(modifier = Modifier.weight(0.30f))
                                Text(text = "100%")
                            }
                        }
                    }
                }
            }
            //submit button
            Button(
                onClick = {
                    onSubmitClick(ChlorineSubmission(
                        sliderPos = sliderPos,
                        newSliderPos = newSliderPos,
                        waterInflow = waterInflow,
                        startVolume = startVolume,
                        endVolume = endVolume,
                        timeElapsed = timeElapsed,
                        sliderPosOverDose = sliderPosOverDose,
                        chemFlowRate = chemFlowRate
                    ))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF77AF87),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .height(50.dp)
                    .padding(8.dp)
            ) {
                Text(text = stringResource(R.string.submit))
            }
        }
    }
}