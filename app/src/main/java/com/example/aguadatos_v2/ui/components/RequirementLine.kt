package com.example.aguadatos_v2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R

//Function for visual representation of password check for security - check or cross symbol + text input
@Composable
fun RequirementLine(met: Boolean, text: String) {
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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 8.dp)
    ) {
        if (met) {
            Image(
                painter = painterResource(id = R.drawable.check),
                contentDescription = "Requirement met",
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 4.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.cross),
                contentDescription = "Requirement not met",
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 4.dp)
            )
        }

        Text(
            text = text,
            fontSize = 12.sp,
            fontFamily = fontFamily
        )
    }
}