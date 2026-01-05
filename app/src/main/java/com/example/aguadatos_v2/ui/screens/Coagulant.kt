package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R
import com.example.aguadatos_v2.ui.components.BottomNavigationBar
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/*
* tried to finish this screen as well, but since internship ends, I dont have time (double check with patrick for what to do)
* */

data class CoagulantSubmission(
  val date: String,
  val chemicalType: String,
  val sliderPosition: Float,
  val inflowRate: String,
  val startVolume: String,
  val endVolume: String,
  val timeElapsed: String,
  val chemicalDose: String,
  val chemicalFlowRate: String
) : Serializable

@Preview(showBackground = true)
@Composable
fun PreviewCoagulant(){
  Coagulant({},{submission -> }, {}, {}, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun Coagulant(
  onBackClick: () -> Unit,
  onSubmitClick: (CoagulantSubmission) -> Unit,
  onHomeClick: () -> Unit,
  onRecordsClick: () -> Unit,
  onGraphsClick: () -> Unit,
  onProfileClick: () -> Unit,
) {

  var tabIndex by remember { mutableStateOf(0) }
  val tabs = listOf("Calibration", "Change Dose")
  var isOn by remember { mutableStateOf(false) }

  var sliderPos by remember { mutableStateOf(0f) }
  var sliderPosOverDose by remember { mutableStateOf(0f) }
  var waterInflow by remember { mutableStateOf("") }
  var startVolume by remember { mutableStateOf("") }
  var endVolume by remember { mutableStateOf("") }
  var timeElapsed by remember { mutableStateOf("")}

  var chemFlowRate by remember { mutableStateOf("") }
  var targetChemDose by remember { mutableStateOf("")}
  var newSliderPos by remember { mutableStateOf(0f)}

  val selectedChemical by remember {mutableStateOf("PAC")} //take this value from server or previous screen
  var clarifiedWater by remember { mutableStateOf("") }

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
          contentDescription = "Back",
          modifier = Modifier
            .size(28.dp)
            .clickable { onBackClick() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          text = stringResource(R.string.coagulant_dosage_caps),
          fontSize = 24.sp,
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(44.dp))
      }
      Text(
        // TODO: Add chemical concentration to reminder
        text = stringResource(R.string.chemical_type_reminder) + " $selectedChemical",
        fontSize = 14.sp,
        fontStyle = FontStyle.Italic,
        color = Color.Gray.copy(alpha = 0.8f),
        modifier = Modifier.padding(bottom = 24.dp)
      )
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
                .height(40.dp)
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
      // Switching between Calibration and Change Dose tabs
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
              Slider(
                value = sliderPos,
                onValueChange = { sliderPos = it },
                valueRange = 0f..100f,
                modifier = Modifier
                  .padding(start = 10.dp, end = 10.dp)
                  .fillMaxWidth(),
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
                Text(text = "35%")
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
                    modifier = Modifier
                      .width(120.dp),
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
                    text = stringResource(R.string.start_volume),
                    modifier = Modifier.width(100.dp),
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
                    text = stringResource(R.string.end_volume),
                    modifier = Modifier.width(100.dp),
                    fontWeight = FontWeight.Bold
                  )
                  OutlinedTextField(
                    value = endVolume,
                    onValueChange = { endVolume = it },
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
                    onValueChange = { timeElapsed = it },
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
                  modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                  fontSize = 20.sp
                )
                Text(
                  text = stringResource(R.string.chemical_dose) + " ",
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                  fontSize = 16.sp
                )
                Text(
                  text = stringResource(R.string.chemical_flow_rate) + " $chemFlowRate",
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
              .height(380.dp)
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
              Slider(
                value = sliderPosOverDose,
                onValueChange = { sliderPosOverDose = it },
                valueRange = 0f..100f,
                modifier = Modifier
                  .padding(start = 10.dp, end = 10.dp)
                  .fillMaxWidth(),
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
                Text(text = "35%")
                Spacer(modifier = Modifier.weight(0.30f))
                Text(text = "100%")
              }
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Target Chemical Dose:",
                  modifier = Modifier.width(200.dp),
                  fontWeight = FontWeight.Bold,
                  fontSize = 18.sp
                )
                OutlinedTextField(
                  value = targetChemDose,
                  onValueChange = { targetChemDose = it },
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
              Slider(
                value = newSliderPos,
                onValueChange = { newSliderPos = it },
                valueRange = 0f..100f,
                modifier = Modifier
                  .padding(start = 30.dp, end = 30.dp)
                  .fillMaxWidth(),
                colors = SliderDefaults.colors(
                  thumbColor = Color(0xFF3C89E1),
                  activeTrackColor = Color(0xFF3C89E1),
                  inactiveTrackColor = Color.White
                )
              )
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(start = 30.dp, top = 0.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "0%", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(0.10f))
                Text(text = "35%", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(0.26f))
              }
            }
          }

        }
      }
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp)
      ) {
        Text(
          text = stringResource(R.string.active_tank),
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Switch(
          checked = !isOn,
          onCheckedChange = { isOn = !it },
          colors = SwitchDefaults.colors(
            uncheckedThumbColor = Color(0xFFE0F3E3),
            uncheckedTrackColor = Color(0xFF7CBB84),
            checkedThumbColor = Color.White,
            checkedTrackColor = Color.Gray
          ),
          modifier = Modifier.scale(1f)
        )
      }

      Button (
        onClick = {
          val submission = CoagulantSubmission(
            date = LocalDate.now().toString(),
            chemicalType = "PAC",
            sliderPosition = sliderPos,
            inflowRate = waterInflow,
            startVolume = startVolume,
            endVolume = endVolume,
            timeElapsed = timeElapsed,
            chemicalDose = "___", //TODO
            chemicalFlowRate = chemFlowRate
          )
          onSubmitClick(submission)
        },
        //{ /* submit things*/ },
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


@Composable
public fun ConfirmScreen(
  date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
  chemicalType: String,
  sliderPosition: Float,
  inflowRate: String,
  startVolume: String,
  endVolume: String,
  timeElapsed: String,
  chemicalDose: String,
  chemicalFlowRate: String,
  onGraphsClick: () -> Unit,
  onHomeClick: () -> Unit,
  onProfileClick: () -> Unit,
  onRecordsClick: () -> Unit,
  onSubmitClick: () -> Unit,
  onBackClick: () -> Unit
) {
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
  )
  { innerPadding ->

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
        Text(
          text = stringResource(R.string.coagulant_dosage_caps),
          fontSize = 24.sp,
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Center
        )

      }
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
          containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Text(
            text = stringResource(R.string.please_confirm_your_entry),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
          )

          // Date / Chemical Type
          Text(
            text = "• " + stringResource(R.string.date) + " $date",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 4.dp)
          )
          Text(
            text = "• " + stringResource(R.string.chemical_type) + " $chemicalType",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 12.dp)
          )

          Divider(thickness = 1.dp, color = Color(0xFFE0E0E0))

          // Calibration section
          Text(
            text = stringResource(R.string.calibration),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
          )

          Text(
            text = "• " + stringResource(R.string.slider_position) + ": ${"%.1f".format(sliderPosition)} %",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.inflow_rate) + ": $inflowRate mL/s",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.start_volume) + " $startVolume mL",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.end_volume) + " $endVolume mL",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.time_elapsed) + " $timeElapsed s",
            fontSize = 18.sp
          )

          Divider(
            thickness = 1.dp,
            color = Color(0xFFE0E0E0),
            modifier = Modifier.padding(top = 16.dp)
          )

          // Output section
          Text(
            text = stringResource(R.string.output),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
          )
          Text(
            text = "• " + stringResource(R.string.chemical_dose) + " $chemicalDose mg/L",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.chemical_flow_rate) + " $chemicalFlowRate mL/s",
            fontSize = 18.sp
          )
        }
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Button(
          onClick = onBackClick,
          colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(color = 0xFF77AF87)
          ),
          shape = RoundedCornerShape(20.dp),
          modifier = Modifier.weight(1f)
        ) {
          Text(stringResource(R.string.go_back), fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))

        Button(
          onClick = onSubmitClick,
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF77AF87),
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(20.dp),
          modifier = Modifier.weight(1f)
        ) {
          Text(stringResource(R.string.confirm), fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
      }
    }

  }
}

@Composable
fun SubmittedConfirmScreen(
  date: String,
  time: String,
  chemicalType: String,
  sliderPosition: Float,
  inflowRate: String,
  startVolume: String,
  endVolume: String,
  timeElapsed: String,
  chemicalDose: String,
  chemicalFlowRate: String,
  onGraphsClick: () -> Unit,
  onHomeClick: () -> Unit,
  onProfileClick: () -> Unit,
  onRecordsClick: () -> Unit,
  onBackClick: () -> Unit
) {
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
        Text(
          text = stringResource(R.string.coagulant_dosage_caps),
          fontSize = 24.sp,
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Center
        )
      }

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
          containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Text(
            text = stringResource(R.string.submission_complete),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
          )

          Text(
            text = stringResource(R.string.submitted_on) + " $date " + stringResource(R.string.at) + " $time",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
          )

          Text(
            text = stringResource(R.string.entry_details),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
          )

          // Date / Chemical Type
          Text(
            text = "• " + stringResource(R.string.date) + " $date",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 4.dp)
          )
          Text(
            text = "• " + stringResource(R.string.chemical_type) + " $chemicalType",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 12.dp)
          )

          Divider(thickness = 1.dp, color = Color(0xFFE0E0E0))

          // Calibration
          Text(
            text = stringResource(R.string.calibration),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
          )

          Text(
            text = "• " + stringResource(R.string.slider_position) + ": ${"%.1f".format(sliderPosition)} %",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.inflow_rate) + ": $inflowRate mL/s",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.start_volume) + " $startVolume mL",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.end_volume) + " $endVolume mL",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.time_elapsed) + " $timeElapsed s",
            fontSize = 18.sp
          )

          Divider(
            thickness = 1.dp,
            color = Color(0xFFE0E0E0),
            modifier = Modifier.padding(top = 16.dp)
          )

          // Output
          Text(
            text = stringResource(R.string.output),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
          )
          Text(
            text = "• " + stringResource(R.string.chemical_dose) + " $chemicalDose mg/L",
            fontSize = 18.sp
          )
          Text(
            text = "• " + stringResource(R.string.chemical_flow_rate) + " $chemicalFlowRate mL/s",
            fontSize = 18.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        OutlinedButton(
          onClick = onBackClick,
          shape = RoundedCornerShape(20.dp),
          modifier = Modifier.weight(1f)
        ) {
          Text(stringResource(R.string.back), fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))

        Button(
          onClick = onHomeClick,
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF77AF87),
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(20.dp),
          modifier = Modifier.weight(1f)
        ) {
          Text("Home", fontSize = 16.sp)
        }
      }
    }
  }
}







