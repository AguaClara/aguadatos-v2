package com.example.aguadatos_v2.ui.screens

import android.R.attr.contentDescription
import android.R.attr.fontFamily
import android.R.attr.fontWeight
import android.R.attr.password
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aguadatos_v2.R


@Preview(showBackground = true)
@Composable
fun PreviewLoginPage(){
  LoginPage({},{})
}

@Composable
fun LoginPage(
  onLoginClick: () -> Unit = {},
  onSignupClick: () -> Unit = {}
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

  var name by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var showPassword by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xffe4effc))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      verticalArrangement = Arrangement.spacedBy(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(16.dp))

      Row(
        Modifier
          .fillMaxWidth()
          .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
      ){
        Image(
          painter = painterResource(R.drawable.agimg),
          contentDescription = stringResource(com.example.aguadatos_v2.R.string.aguadatos_logo),
          modifier = Modifier
            .weight(1f)
            .height(62.dp)
        )
        Text(
          text = stringResource(R.string.log_in),
          fontSize = 24.sp,
          color = Color.Black,
          modifier = Modifier
            .weight(7f)
            .padding(vertical = 16.dp),
          textAlign = TextAlign.Center,
        )
      }

      Spacer(modifier = Modifier.height(150.dp))
      Box(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = stringResource(R.string.first_and_last_name),
          color = Color.Black,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          textAlign = TextAlign.Start,
          fontFamily = fontFamily
        )
      }
      OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        placeholder = { Text("ex. Kelly Tran", color = Color.Gray) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = Color.White,
          unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(0.dp),
        leadingIcon = { Icon(
          painter = painterResource(id = R.drawable.person2),
          contentDescription = "Person icon") },
      )

      Box(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = stringResource(R.string.password),
          color = Color.Black,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          textAlign = TextAlign.Start
        )
      }
      OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        placeholder = { Text(stringResource(R.string.password), color = Color.Gray) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = Color.White,
          unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(
          painter = painterResource(id = R.drawable.lock),
          contentDescription = "lock") },
        trailingIcon = {
          IconButton(
            onClick = { showPassword = !showPassword }
          ) {
            Icon(
              painter = painterResource(
                id = if (showPassword) R.drawable.eyeoff else R.drawable.eye
              ),
              contentDescription = if (showPassword) stringResource(R.string.hide_password)
              else stringResource(R.string.show_password)
            )
          }
        }
      )
    }
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      Button(
        onClick = onLoginClick,
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFF4A90E2),
          contentColor = Color.White
        ),
        enabled = name.isNotBlank() && password.isNotBlank()
      ) {
        Text(stringResource(R.string.log_in), fontSize = 18.sp)
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = stringResource(R.string.dont_have_an_account_sign_up),
        fontSize = 14.sp,
        color = Color.Gray,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable { onSignupClick() }
      )
    }
  }
}