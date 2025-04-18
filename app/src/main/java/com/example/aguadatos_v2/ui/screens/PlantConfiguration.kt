package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Preview(showBackground = true)
@Composable
fun PreviewPlantConfiguration() {
    PlantConfiguration()
}
@Composable
fun PlantConfiguration() {
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
    val checkedStates = remember { mutableStateListOf(true, true, false, false, false, false) }
    val labels = listOf("Plant Flow in NTU", "Raw Water in NTU", "Coagulant Dose", "Filtered Water Turbidity in NTU", "Clarified Water Turbidity in NTU", "Chlorine Dose")
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xffe4effc))
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.logo),
            contentDescription = "adlogo",
            modifier = Modifier.align(Alignment.Start)
                .size(width = 180.dp, height = 100.dp))
        Text(text = "Plant " + plant + " has not yet been configured. " +
                "Please select which water parameters your plant records."
            , fontSize = 18.sp,
            textAlign = TextAlign.Left,
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            modifier = Modifier.width(310.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start){
        checkedStates.forEachIndexed { index, checked -> Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { isChecked ->
                    checkedStates[index] = isChecked }

            )
            Text(
                text = labels[index],
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = if (checkedStates[index]) Color.Black else Color.Gray,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,
            )}}
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)){
            Spacer(modifier = Modifier.width(12.dp))
            Image(painter = painterResource(R.drawable.icon), contentDescription = "plus icon",
            modifier = Modifier.size(25.dp)
                .clickable {})
            Spacer(modifier = Modifier.width(11.dp))
            Text(text = "Parameter not here? Create one",
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W400,)
        }
        }
        Spacer(modifier = Modifier.height(125.dp))
        Button(onClick = {  },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff7cbb84),
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.align(Alignment.End)
        )

        {Text(text = "CONFIGURE", fontSize = 15.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500)}}
    }