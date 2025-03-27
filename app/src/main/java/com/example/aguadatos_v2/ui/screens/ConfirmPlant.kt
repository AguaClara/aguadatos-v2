package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.example.aguadatos_v2.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun PreviewConfirmPlant() {
    ConfirmPlant()
}
@Composable
fun ConfirmPlant() {
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
    val plant= "Gracias"
 Column(modifier = Modifier
     .fillMaxSize()
     .background(Color(0xffe4effc))
     .padding(24.dp),
     horizontalAlignment = Alignment.CenterHorizontally) {
     Image(painter = painterResource(R.drawable.logo),
         contentDescription = "adlogo",
         modifier = Modifier.align(Alignment.Start)
             .size(width = 180.dp, height = 100.dp))
     Text(text = "Before creating your account, please confirm that the plant code you have entered corresponds to the plant that you work at. \n " +
             "\nThe plant code you have entered is associated with plant: "+ plant +"\n" +
             "\nPlease confirm that this is the correct plant. If it is incorrect, go back and input the correct plant code."
             , fontSize = 20.sp,
         textAlign = TextAlign.Left,
         color = Color.Black,
         fontFamily = fontFamily,
         fontWeight = FontWeight.W400,
         modifier = Modifier.width(310.dp))
     Spacer(modifier = Modifier.height(36.dp))
     Row(modifier = Modifier
         .width(310.dp),
         horizontalArrangement = Arrangement.SpaceBetween){
         Button(onClick = {  },
             colors = ButtonDefaults.buttonColors(
                 containerColor = Color.White,
                 contentColor = Color(0xff7cbb84)
             ),
             elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
             shape = RoundedCornerShape(12.dp))
         {Text(text = "GO BACK", fontSize = 15.sp,
             fontFamily = fontFamily,
             fontWeight = FontWeight.W500)}
         Button(onClick = {  },
             colors = ButtonDefaults.buttonColors(
                 containerColor = Color(0xff7cbb84),
                 contentColor = Color.White
             ),
             elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
             shape = RoundedCornerShape(12.dp)
         )

         {Text(text = "CONFIRM", fontSize = 15.sp,
             fontFamily = fontFamily,
             fontWeight = FontWeight.W500)}

     }
 }
}