package com.example.aguadatos_v2.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.ui.components.RequirementLine
import com.example.aguadatos_v2.R

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aguadatos_v2.ui.theme.AuthViewModel
import kotlinx.coroutines.launch


/*
*
* Draft data mapping and field relationships (for GraphQL schema implementation)

* Field relationships:
*  - password and confPassword (confirm Passsword) must match
*  - must contain a lower, upper, digit, and symbol
*  - phone number must be a valid phone number format (123-456-7890, 1234567890, or 123.456.7890)
*  - Then server will revalidate/store info
* */


/*
* Sign up composable:
*
* Intent & Design:
*  - SignUp screen to collect sign up information, including full name, plant, phone number, and password
*  - Performs client-side verification of passwords matching, password strength, and phone number validity
* */


@Preview(showBackground = true)
@Composable
fun PreviewSignUp(){
    SignUp(viewModel(),{},{})
}


@Composable
fun SignUp(
  authViewModel : AuthViewModel,
  onSignUp: () -> Unit = {},
  onLogInClick : () -> Unit = {}
){
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

  val invalid_user_name_error = stringResource(R.string.invalid_user_name)
  val invalid_phone_number_error = stringResource(R.string.invalid_phone_number)
  val invalid_password_error = stringResource(R.string.invalid_password)
  val password_mismatch_error = stringResource(R.string.password_mismatch)


  fun formatNumber(input : String): String {
    if (input.isBlank()) { return "" }
    val number = "+" + input.replace("[^\\d]".toRegex(), "")
    return number
  }
  val e164Regex = Regex("^\\+[1-9]\\d{1,14}$")

  var userName by remember {mutableStateOf("")}
  var plantCode by remember {mutableStateOf("")}
  var phoneNumber by remember {mutableStateOf("")}
  val formattedNumber = formatNumber(phoneNumber)


  var password by remember{mutableStateOf("")}
  var showPassword by remember {mutableStateOf(false)}
  var confPassword by remember{mutableStateOf("")}
  var showConfPassword by remember {mutableStateOf(false)}

  val validName = userName.isNotBlank()
  val validPlantCode = plantCode.isNotBlank()
  val phoneValid = phoneNumber.isNotBlank() && e164Regex.matches(formattedNumber)
  val hasLower = password.any { it.isLowerCase() }
  val hasUpper = password.any { it.isUpperCase() }
  val hasDigit = password.any { it.isDigit() }
  val hasSymbol = password.any { !it.isLetterOrDigit() }

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
//  fun verifyFields() : Boolean {
//    if (!validName) {
//      Log.e("SignUp error: ", "Invalid user name")
//      return false
//    } else if (!phoneValid) {
//      Log.e("SignUp error: ", "Invalid phone number $formattedNumber")
//      return false
//    } else if (!hasDigit || !hasLower || !hasUpper || !hasSymbol) {
//      Log.e("SignUp error: ", "Invalid Password")
//      Log.e("SignUp error: ", "hasDigit:$hasDigit, hasLower:$hasLower, hasUpper:$hasUpper, hasSymbol:$hasSymbol")
//      return false
//    } else if (password != confPassword) {
//      Log.e("SignUp error: ", "Confirmation Password does not match")
//      return false
//    }
//    Log.i("SignUp number", "Formated #: $formattedNumber")
//    return true
//  }

  Scaffold(
    containerColor = Color(0xffe4effc),
    snackbarHost = { SnackbarHost(snackbarHostState) }
  ) { innerPadding ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ){
      Row(
        Modifier
          .fillMaxWidth()
          .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
      ){
        Image(
          painter = painterResource(R.drawable.agimg),
          contentDescription = stringResource(R.string.aguadatos_logo),
          modifier = Modifier
            .weight(1f)
            .height(62.dp)
        )
        Text(
          text = stringResource(R.string.sign_up),
          fontSize = 24.sp,
          color = Color.Black,
          modifier = Modifier
            .weight(7f)
            .padding(vertical = 16.dp),
          textAlign = TextAlign.Center,
          fontFamily = fontFamily
        )
      }

      Column(
        Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp)
      ){

        //Name field
        Text(
          text = stringResource(R.string.first_and_last_name),
          color = Color.Black,
          fontSize = 14.sp,
          fontFamily = fontFamily
        )
        OutlinedTextField(
          value = userName,
          onValueChange = { userName = it},
          placeholder = {
            Text("Ex. Kelly Tran",
              color = Color.Gray,
              fontFamily = fontFamily)
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
          leadingIcon = {Icon(painter = painterResource(id = R.drawable.person2), contentDescription = "Person icon")},

          )

        //Plant code field
        Text(
          text = stringResource(R.string.plant_code),
          color = Color.Black,
          fontSize = 14.sp,
          fontFamily = fontFamily
        )
        OutlinedTextField(
          value = plantCode,
          onValueChange = { plantCode = it},
          placeholder = {
            Text("Ex. ABC123",
              color = Color.Gray,
              fontFamily = fontFamily)
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth(),
          leadingIcon = {Icon(painter = painterResource(id = R.drawable.person2), contentDescription = "Person icon")}
        )
        Text(
          text = stringResource(R.string.your_plant),
          color = Color.Black,
          textAlign = TextAlign.Right,
          fontSize = 9.sp,
          fontFamily = fontFamily,
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),

          )

        //Phone number field. Commonly +504 XXXX-XXXX
        Text(
          text = stringResource(R.string.phone_number),
          color = Color.Black,
          fontSize = 14.sp,
          fontFamily = fontFamily
        )
        OutlinedTextField(
          value = phoneNumber,
          onValueChange = { phoneNumber = it },
          placeholder = {
            Text("Ex. +504 1234-5678",
              color = Color.Gray, fontFamily = fontFamily)
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
          leadingIcon = {Icon(
            painter = painterResource(id = R.drawable.phone),
            contentDescription = "phone icon")
          },
        )

//        RequirementLine(met = phoneValid, text = stringResource(R.string.includes_lowercase_character))

        //Password field
        Text(
          text = stringResource(R.string.password),
          color = Color.Black,
          fontSize = 14.sp,
          fontFamily = fontFamily,
        )
        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          placeholder = {
            Text(stringResource(R.string.enter_your_password), color = Color.Gray, fontFamily = fontFamily)
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
          leadingIcon = {
            Icon(
              painter = painterResource(id = R.drawable.lock),
              contentDescription = stringResource(R.string.lock_icon)
            )
          },
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
          },
          visualTransformation = if (showPassword) {
            VisualTransformation.None
          } else {
            PasswordVisualTransformation()
          }
        )

        //Requirement line component for password strength visuals
        RequirementLine(met = hasLower, text = stringResource(R.string.includes_lowercase_character))
        RequirementLine(met = hasUpper, text = stringResource(R.string.includes_uppercase_character))
        RequirementLine(met = hasDigit, text = stringResource(R.string.includes_numbers))
        RequirementLine(met = hasSymbol, text = stringResource(R.string.includes_symbols))

        //confirm password field
        Text(
          text = stringResource(R.string.confirm_password),
          color = Color.Black,
          fontSize = 14.sp,
          modifier = Modifier.padding(top = 4.dp),
          fontFamily = fontFamily
        )
        OutlinedTextField(
          value = confPassword,
          onValueChange = { confPassword = it },
          placeholder = {
            Text(stringResource(R.string.enter_your_password), color = Color.Gray, fontFamily = fontFamily)
          },
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
          leadingIcon = {
            Icon(
              painter = painterResource(id = R.drawable.lock),
              contentDescription = stringResource(R.string.lock_icon)
            )
          },
          trailingIcon = {
            IconButton(
              onClick = { showConfPassword = !showConfPassword }
            ) {
              Icon(
                painter = painterResource(
                  id = if (showConfPassword) R.drawable.eyeoff else R.drawable.eye
                ),
                contentDescription = if (showConfPassword) stringResource(R.string.hide_password)
                else stringResource(R.string.show_password)
              )
            }
          },
          visualTransformation = if (showConfPassword) {
            VisualTransformation.None
          } else {
            PasswordVisualTransformation()
          }
        )

        //sign up button
        Button(
          onClick = {
            // TODO
            val error = when {
                !validName -> invalid_user_name_error
                !phoneValid ->invalid_phone_number_error
                !hasDigit || !hasLower || !hasUpper || !hasSymbol -> invalid_password_error
                password != confPassword -> password_mismatch_error
                else -> null
              }

            if (error != null) {
              scope.launch {
                snackbarHostState.showSnackbar(
                  message = error,
                  duration = SnackbarDuration.Short
                )
              }
            }
            else {
              onSignUp()
            }
//            if (verifyFields()) {
//              authViewModel.updateSignUpState(
//                name = userName,
//                phone = formattedNumber,
//                plantCode = plantCode,
//                password = password
//              )
//              authViewModel.signUp(
//                onSuccess = onSignUp,
//                onError = { msg -> Log.e("SignUp error: ", msg) }
//              )
//            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4A90E2)
          ),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text(
            text = stringResource(R.string.create_account),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily
          )
        }

        //login button
        TextButton(
          onClick = onLogInClick,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = stringResource(R.string.already_have_an_account_log_in),
            color = Color(0xFF666666),
            fontSize = 14.sp,
            fontFamily = fontFamily
          )
        }
      }
    }
  }
}

