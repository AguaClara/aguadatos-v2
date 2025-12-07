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
//import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    SignUp({},{})
}


@Composable
fun SignUp(
    onCreateAccountClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
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

    var userName by remember {mutableStateOf("")}
    var plantCode by remember {mutableStateOf("")}
    var phoneNumber by remember {mutableStateOf("")}


    var password by remember{mutableStateOf("")}
    var showPassword by remember {mutableStateOf(false)}
    var confPassword by remember{mutableStateOf("")}
    var showConfPassword by remember {mutableStateOf(false)}

    val phoneRegex = Regex("""^\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$""")
    val phoneValid = phoneNumber.isNotBlank() && phoneRegex.matches(phoneNumber)
    val hasLower = password.any { it.isLowerCase() }
    val hasUpper = password.any { it.isUpperCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSymbol = password.any { !it.isLetterOrDigit() }


    val authViewModel: AuthViewModel = viewModel()
    fun onCreateAccountClick() {
        if (!hasDigit || !hasLower || !hasUpper || !hasSymbol) {
            // TODO: Error message pop up
        } else if (password != confPassword || !phoneValid) {
            // TODO: Error message pop up
        } else {
            authViewModel.signUp(
                onSuccess = {
                    onCreateAccountClick();
                },
                onError = { msg: String ->
                    Log.e("SignUp error: ", msg)
                }
            )
        }
    }

    Scaffold(
        containerColor = Color(0xffe4effc)
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
                        Text(stringResource(R.string.ex) +  "Kelly Tran",
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
                        Text(stringResource(R.string.ex) + "ABC123",
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

                //Phone number field
                Text(
                    text = stringResource(R.string.phone_number),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = fontFamily
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it},
                    placeholder = {
                        Text(stringResource(R.string.ex) + "123-456-789", color = Color.Gray, fontFamily = fontFamily)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    leadingIcon = {Icon(painter = painterResource(id = R.drawable.phone), contentDescription = "phone icon")},
                )

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
                        onCreateAccountClick();
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
                    onClick = onLoginClick,
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

