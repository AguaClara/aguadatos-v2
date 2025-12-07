package com.example.aguadatos_v2.ui.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.height
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R

@Preview(showBackground = true)
@Composable
fun PreviewWelcomePage() {
    WelcomePage()
}
@Composable
fun WelcomePage(
    onCreateAccountClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffe4effc))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){Text(text = "Welcome to AguaDatos !",
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        color = Color.Black,
        fontFamily = fontFamily,
        fontWeight = FontWeight.W400)
    Spacer(modifier = Modifier.height(24.dp))
    Image(painter = painterResource(R.drawable.agimg), contentDescription = "agimg",
        modifier = Modifier.size(140.dp))
    Spacer(modifier = Modifier.height(24.dp))
    Button(onClick = onCreateAccountClick,
        modifier = Modifier.align(Alignment.End),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    ) {
        Text(text = "Create an account ", fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W600)
        Image(painter = painterResource(R.drawable.right_arrow), contentDescription = "Arrow",
            modifier = Modifier.size(40.dp))
    }
    Spacer(modifier = Modifier.height(16.dp))
    TextButton(
        onClick = onLoginClick,
        modifier = Modifier.clickable { }
            .align(Alignment.End)
            .padding(end = 25.dp)
    ) {
        Text(
            text = "Already have an account? Log in",
            fontSize = 10.sp,
            textDecoration = TextDecoration.Underline,
            color = Color.Gray,

            fontFamily = fontFamily
        )
    }

    }

}

