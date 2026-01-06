package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Horizontal
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R
import com.example.aguadatos_v2.ui.components.BottomNavigationBar
import com.example.aguadatos_v2.ui.components.BottomNavButton

/*
* Plant flow composable:
* Intent & Design:
*  - screen to input plant flow data in lts/sec
*  - only allows digits and decimal points typed
* */

@Composable
public fun PlantFlow(
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onHomeClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onGraphsClick: () -> Unit,
    onProfileClick: () -> Unit
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

    val selectedChemical by remember {mutableStateOf("PAC")} //take this value from server or previous screen
    var plantFlow by remember { mutableStateOf("") }

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
                //back arrow
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBackClick() }
                )
                Spacer(modifier = Modifier.width(16.dp))

                //heading
                Text(
                    text = stringResource(R.string.plant_flow_caps),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.width(44.dp))
            }

            //explanation text
            Text(
                text = stringResource(R.string.what_is_the_inflow_rate),
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp),
                fontFamily = fontFamily
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                //plant flow input text field
                OutlinedTextField(
                    value = plantFlow,
                    onValueChange = {  newValue ->
                        // Only allow digits and decimal points
                        if (newValue.all { it.isDigit() || it== '.' }) {
                            plantFlow = newValue
                        }},
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),

                    modifier = Modifier
                        .width(80.dp)
                        .padding(bottom = 8.dp)


                    )

                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "lts/sec", fontSize = 16.sp, fontFamily = fontFamily)
            }

            Text(
                text = stringResource(R.string.chemical_type_reminder) + " $selectedChemical",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 24.dp),
                fontFamily = fontFamily
            )

            //submit button
            Button(
                onClick = onSubmitClick /*submit data to server code goes here*/,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF77AF87),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .height(40.dp)
            ) {
                Text(text = stringResource(R.string.confirm), fontFamily = fontFamily)
            }
        }
    }
}

