package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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

@Composable
public fun TankVolumes(
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

    val selectedChemical by remember {mutableStateOf("PAC")}

    var coagulantTank1 by remember { mutableStateOf("") }
    var coagulantTank2 by remember { mutableStateOf("") }

    var chlorineTank1 by remember { mutableStateOf("") }
    var chlorineTank2 by remember { mutableStateOf("") }

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
                .padding(horizontal = 44.dp)
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
                    text = stringResource(R.string.tank_volume_caps),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.width(44.dp))
            }
            //explanation text
            Text(
                text = stringResource(R.string.what_is_the_volume),
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp),
                fontFamily = fontFamily
            )
            Column(
                   verticalArrangement = Arrangement.spacedBy(4.dp)
               ) {
                   Text(
                       text = stringResource(R.string.coagulant),
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp,
                       fontFamily = fontFamily
                   )
                   Column(
                       modifier = Modifier.padding(start = 16.dp),
                       verticalArrangement = Arrangement.spacedBy(12.dp)
                   ) {
                       Row(
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Text(
                               text = stringResource(R.string.tank_1_volume),
                               modifier = Modifier.width(160.dp),
                               fontWeight = FontWeight.Bold,
                               fontFamily = fontFamily
                           )
                           OutlinedTextField(
                               value = coagulantTank1,
                               onValueChange = { coagulantTank1 = it },
                               singleLine = true,
                               shape = RoundedCornerShape(8.dp),
                               colors = OutlinedTextFieldDefaults.colors(
                                   focusedContainerColor = Color.White,
                                   unfocusedContainerColor = Color.White,
                                   focusedTextColor = Color.Black,
                                   unfocusedTextColor = Color.Black,
                               ),
                               modifier = Modifier
                                   .width(90.dp)
                                   .height(48.dp)
                           )
                           Spacer(modifier = Modifier.width(8.dp))

                           Text(
                               text = "L",
                               fontWeight = FontWeight.Bold,
                               fontFamily = fontFamily
                           )
                       }
                       Row(
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Text(
                               text = stringResource(R.string.tank_2_volume),
                               modifier = Modifier.width(160.dp),
                               fontWeight = FontWeight.Bold,
                               fontFamily = fontFamily
                           )
                           OutlinedTextField(
                               value = coagulantTank2,
                               onValueChange = { coagulantTank2 = it },
                               singleLine = true,
                               shape = RoundedCornerShape(8.dp),
                               colors = OutlinedTextFieldDefaults.colors(
                                   focusedContainerColor = Color.White,
                                   unfocusedContainerColor = Color.White,
                                   focusedTextColor = Color.Black,
                                   unfocusedTextColor = Color.Black,
                               ),
                               modifier = Modifier
                                   .width(90.dp)
                                   .height(48.dp)
                           )
                           Spacer(modifier = Modifier.width(8.dp))

                           Text(
                               text = "L",
                               fontWeight = FontWeight.Bold,
                               fontFamily = fontFamily
                           )
                       }
                   }
                    Text(
                        text = stringResource(R.string.chlorine),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = fontFamily
                    )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.tank_1_volume),
                            modifier = Modifier.width(160.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )
                        OutlinedTextField(
                            value = chlorineTank1,
                            onValueChange = { chlorineTank1 = it },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                            ),
                            modifier = Modifier
                                .width(90.dp)
                                .height(48.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "L",
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.tank_2_volume),
                            modifier = Modifier.width(160.dp),
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )
                        OutlinedTextField(
                            value = chlorineTank2,
                            onValueChange = { chlorineTank2 = it },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                            ),
                            modifier = Modifier
                                .width(90.dp)
                                .height(48.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "L",
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )
                    }
                }
               }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(R.string.chemical_type_reminder) + " $selectedChemical",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 24.dp),
                    fontFamily = fontFamily
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                //submit button
                Button(
                    onClick = onSubmitClick /*submit data to server code goes here*/ ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF77AF87),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(40.dp)
                ) {
                    Text(text = stringResource(R.string.submit), fontFamily = fontFamily)
                }
            }
        }

    }
}


