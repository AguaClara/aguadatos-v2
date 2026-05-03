package com.example.aguadatos_v2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R

@Composable
fun DataGraph(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val backgroundColor = Color.White
    val textColor = Color.Black
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName = GoogleFont("Lato")
    val fontFamily = FontFamily(Font(
        googleFont = fontName,
        fontProvider = provider,
    )
    )
    var title = text;
    when (text) {
        "Coag. Dose" -> title = "Coagulant Dose"
        "Filter Turbid." -> title = "Filtered Turbidity"
        "Clarified Turbid." -> title = "Clarified Turbidity"
    }
    var unit = ""
    when (text) {
        "Plant Flow" -> unit = " (mL/s)"
        "Raw Water" -> unit = " (NTU)"
        "Coag. Dose" -> unit = " (mg/L)"
        "Chlorine Dose" -> unit = " (mg/L)"
        "Filter Turbid." -> unit = " (mg/L)"
        "Clarified Turbid." -> unit = " (mg/L)"
    }
    
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .size(width = 400.dp, height = 300.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = title + unit,
                color = Color.Black,
                modifier = Modifier.padding(10.dp),
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 60.dp, horizontal = 7.dp)
            )
            // add buttons for timeframe
        }
        // add new box for graphs
    }


}