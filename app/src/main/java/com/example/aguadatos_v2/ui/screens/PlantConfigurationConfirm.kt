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
import androidx.compose.material3.CheckboxDefaults
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
* Plant configuration confirmation composable:
* Intent & Design:
*  - to verify the information is correct before proceeding
*  - list out all the information collected in previous Plant configuration pages
* */

@Preview(showBackground = true)
@Composable
fun PreviewPlantConfigurationConfirm(){
    PlantConfigurationConfirm({})
}


@Composable
fun PlantConfigurationConfirm(
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

    //temporary values
    // get real data from server or previous screens passed in as parameters to this composable
    val plant = "Gracias"
    var checkedStates = remember { mutableStateListOf(true, true, true, false, true, true) }
    var labels = remember { mutableStateListOf("Plant Flow in NTU", "Raw Water in NTU", "Coagulant Dose", "Filtered Water Turbidity in NTU", "Clarified Water Turbidity in NTU", "Chlorine Dose")}
    var selectedChemical by remember{mutableStateOf("PAC")}
    var chemicalConcentration by remember{mutableStateOf("##")}
    var numberOfFilters by remember{mutableStateOf("#")}

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Configuration Overview of ",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400
                )
            Text(
                text = plant,
                fontSize = 18.sp,
                color = Color.Blue,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,

            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Water Parameters",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500)

        Column(modifier = Modifier.padding(start = 12.dp)) {
            labels.forEachIndexed { index, label ->
                if (checkedStates[index]) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(R.drawable.checkmark),
                            contentDescription = "adlogo",
                            modifier = Modifier.size(width = 20.dp, height = 20.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W400
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Dosage Presets",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500)

        Column(modifier = Modifier.padding(start = 12.dp, top = 6.dp)){

            Text(text = "Type of Chemical: $selectedChemical",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(bottom = 6.dp))

            Text(text = "Chemical Concentration: $chemicalConcentration",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Filter Presets",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500)

        Text(text = "Number of Filters: $numberOfFilters",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(start = 12.dp))

        Button(
            //send data to server
            onClick = onSubmitClick,

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7CBB84),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .align(Alignment.End)
                .height(40.dp)
        ) {
            Text(text = "CONFIRM")
        }

    }
}