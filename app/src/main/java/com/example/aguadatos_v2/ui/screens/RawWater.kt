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
* Raw water composable:
* Intent & Design:
*  - screen to input chlorine data in mg/L
*  - only allows digits and decimal points typed
* */

@Composable
public fun RawWater(
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

    val selectedChemical by remember {mutableStateOf(stringResource(R.string.pac))} //take this value from server or previous screen
    var rawWater by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xffdbecff),
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onRecordsClick = onRecordsClick,
                onGraphsClick = onGraphsClick,
                onProfileClick = onProfileClick,
                currentScreen = stringResource(R.string.home)
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
                    text = stringResource(R.string.raw_water),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.width(44.dp))
            }

            //explanation text
            Text(
                text = stringResource(R.string.what_is_the_turbidity_of_the_raw_water),
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
                //raw water input text field
                OutlinedTextField(
                    value = rawWater,
                    onValueChange = {  newValue ->
                        // Only allow digits and decimal points
                        if (newValue.all { it.isDigit() || it== '.' }) {
                            rawWater = newValue
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
                Text(text = stringResource(R.string.ntu), fontSize = 16.sp, fontFamily = fontFamily)
            }

            Text(
                text = stringResource(R.string.reminder_the_chemical_type_is) + selectedChemical,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 24.dp),
                fontFamily = fontFamily
            )

            //submit button
            Button(
                onClick = onSubmitClick, /*submit data to server code goes here*/
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF77AF87),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .height(40.dp)
            ) {
                Text(text = stringResource(R.string.submit), fontFamily = fontFamily)
            }
        }
    }
}

