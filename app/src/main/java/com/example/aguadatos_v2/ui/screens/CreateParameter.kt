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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R

@Preview(showBackground = true)
@Composable
fun PreviewCreateParameter() {
    CreateParameter()
}
@Composable
fun CreateParameter() {
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
    var parameterName by remember { mutableStateOf("") }
    var measurementunitName by remember { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xffe4effc))
        .padding(vertical = 24.dp, horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(R.drawable.back_arrow), contentDescription = "back arrow",
            modifier = Modifier
                .size(25.dp)
                .clickable {})
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Create a Parameter",
                fontSize = 25.sp,
                color = Color.Black,
                fontFamily = fontFamily,
            )
        }
    }
        Spacer(modifier = Modifier.height(24.dp))

        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)){
            Text(
                text = "Parameter Name",
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = fontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = parameterName,
                onValueChange = { parameterName = it },
                label = { Text("ex. Color", color = Color.Gray) },
                colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Unit of Measure",
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = fontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = measurementunitName,
                onValueChange = { measurementunitName = it },
                label = { Text("ex. NTU", color = Color.Gray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {append("Your Parameter: ")
                    if (parameterName != "" && measurementunitName != "")
                        withStyle( style = SpanStyle(color = Color(0xff4153af
                        ))){append("$parameterName in $measurementunitName")}},
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = fontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Create Button aligned to the right
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = {  },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff7cbb84),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                {Text(text = "CREATE", fontSize = 15.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W500)}
            }
        }
    }}

