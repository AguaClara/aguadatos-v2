package com.example.aguadatos_v2.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R
import android.graphics.Color as AndroidColor


@Composable
fun GraphButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    var isClicked by remember { mutableStateOf(false)}
    val backgroundColor = if (isClicked) {
        when (text) {
            "RAW\nWATER"         -> Color(AndroidColor.parseColor("#377D22"))
            "COAG.\nDOSE"        -> Color(AndroidColor.parseColor("#2C98B9"))
            "CLARIFIED TURBID." -> Color(AndroidColor.parseColor("#4153AF"))
            "FILTER TURBID."  -> Color(AndroidColor.parseColor("#ED6237"))
            else -> Color.Gray
        }
    } else {
        Color.White
    }
    var textColor = if (isClicked) {Color.White} else {Color.Black}
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
    Button(
        onClick = { isClicked = !isClicked
                  onClick()}
        ,
        modifier = modifier
            .heightIn(min = 65.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        contentPadding = PaddingValues(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                letterSpacing = 0.15.em,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 1.em
            )
        }
    }
}