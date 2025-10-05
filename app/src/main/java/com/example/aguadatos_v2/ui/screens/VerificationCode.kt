package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R
import com.example.aguadatos_v2.ui.components.CodeInputTextFields

/*
*
* Draft data mapping and field relationships (for GraphQL schema implementation)

* Field relationships:
*  - Verification code input field
*  - Client-side verifies code is only digits and 6 characters long
*  - validation to be done by server
* */


/*
* Verification code composable:
*
* Intent & Design:
*  - Verification code screen to verify phone number
* */

@Preview(showBackground = true)
@Composable
fun PreviewVerificationCode() {
    VerificationCode({})
}


@Composable
fun VerificationCode(
    onSubmitClick: () -> Unit
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

    val verificationCode = remember {mutableStateListOf("","","","","","") }
    val codeLength = 6

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffe4effc))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(R.drawable.logo),
            contentDescription = "adlogo",
            modifier = Modifier.align(Alignment.Start)
                .size(width = 180.dp, height = 100.dp))

        Text(text = "Please input the 6-digit verification code that has been sent to your phone number: ",
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400)

        Spacer(modifier = Modifier.height(24.dp))

        //call CodeInputTextFields component for 6 box code input field
        CodeInputTextFields(
            codeLength = codeLength,
            codeValues = verificationCode,
            onUpdateCodeValuesByIndex = { index, value ->
                verificationCode[index] = value
            },
            onCodeInputComplete = {
                // code to validate verification code with server goes here
                val code = verificationCode.joinToString("")
                //if (server_validates_code), then:
                onSubmitClick()

            },
            fontFamily = fontFamily
        )

        Spacer(modifier = Modifier.height(24.dp))

        //submit button
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = onSubmitClick,
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
}

