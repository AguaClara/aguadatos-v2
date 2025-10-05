package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R

/*
* Draft data mapping and field relationships (for GraphQL schema implementation)
* selectedChemical: can only be PAC or Al2SO42
* number of filters will only allow digits
* */

/*
* Plant configuration (other) composable:
* Intent and design:
*  - follows the PlantConfiguration page to collect more info about dosage presets and filter presets
*  - toggle for selected chemical values to only allow PAC and Al2SO42
*  - number of filters field only allows digits to be entered
* */

@Composable
@Preview
fun PreviewPlantConfigurationOther(){
    PlantConfigurationOther({})
}

@Composable
fun PlantConfigurationOther(
    onSubmitClick: () -> Unit
) {

    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName = GoogleFont("Lato")
    val fontFamily = FontFamily(
        Font(
            googleFont = fontName,
            fontProvider = provider,
        )
    )

    val plant = "Gracias"
    val options = listOf("PAC", "Al₂(SO₄)₂")
    var selectedChemical by remember{mutableStateOf("PAC")}
    var chemicalConcentration by remember{mutableStateOf("")}
    var numberOfFilters by remember{mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffe4effc))
            .padding(24.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "adlogo",
            modifier = Modifier.align(Alignment.Start)
                .size(width = 180.dp, height = 100.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 24.dp)
        ){
            Text(
                text = "Plant " + plant + " also needs to be configured these settings. ",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                color = Color.Black,
                fontWeight = FontWeight.W400,
                modifier = Modifier.width(310.dp),
                fontFamily = fontFamily
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Dosage Presets",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Type of Chemical:",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    color = Color(0xff4153AF),
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.width(4.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFE8F5E9)) // light green background
                        .border(1.dp, Color(0xFFE0F3E3), RoundedCornerShape(20.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    options.forEach { option ->
                        val isSelected = (option == selectedChemical)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) Color(0xFF7CBB84) else Color.Transparent)
                                .clickable { selectedChemical = option }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = option,
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chemical Concentration:",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    color = Color(0xff4153AF),
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = chemicalConcentration,
                    onValueChange = { chemicalConcentration = it},
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp)
                )
            }

            Text(
                text = "Filter Presets",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Number of Filters: ",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    color = Color(0xff4153AF),
                    fontFamily = fontFamily
                )

                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = numberOfFilters,
                    onValueChange = { newValue ->
                        // Only allow digits
                        if (newValue.all { it.isDigit() }) {
                            numberOfFilters = newValue
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                //store data to server here

                //onClick checks fields are not empty before calling onSubmitClick
                onClick = {if (selectedChemical != "" && chemicalConcentration != "" && numberOfFilters != "") onSubmitClick()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7CBB84),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .height(40.dp)
            ) {
                Text(text = "CONFIGURE", fontFamily = fontFamily)
            }
        }
    }
}