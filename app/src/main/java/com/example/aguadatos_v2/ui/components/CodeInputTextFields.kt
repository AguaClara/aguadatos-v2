package com.example.aguadatos_v2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

/*
* Function to create a 6 box text field for phone verification code
* creates 6 outlined text fields for each number of the code, with a focus manager to move between text fields when a new digit is typed or backspace is pressed
* should handle if code is pasted
*
* referenced: Geeks for Geeks
*/
@Composable
fun CodeInputTextFields(
    codeLength: Int,
    codeValues: List<String>,
    onUpdateCodeValuesByIndex: (Int, String) -> Unit,
    onCodeInputComplete: () -> Unit,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    val focusRequesters = List(codeLength) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        codeValues.forEachIndexed { index, value ->
            OutlinedTextField(
                modifier = Modifier
                    .size(width = 48.dp, height = 56.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Backspace) {
                            if (codeValues[index].isEmpty() && index > 0) {
                                onUpdateCodeValuesByIndex(index, "")
                                focusRequesters[index - 1].requestFocus()
                            } else {
                                onUpdateCodeValuesByIndex(index, "")
                            }
                            true
                        } else {
                            false
                        }
                    },
                value = value,
                onValueChange = { newValue ->
                    // to handle pasted code
                    if (newValue.length == codeLength) {
                        for (i in codeValues.indices) {
                            onUpdateCodeValuesByIndex(
                                i,
                                if (i < newValue.length && newValue[i].isDigit()) newValue[i].toString() else ""
                            )
                        }
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onCodeInputComplete()

                    } else if (newValue.length <= 1) {
                        onUpdateCodeValuesByIndex(index, newValue)
                        if (newValue.isNotEmpty()) {
                            if (index < codeLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    } else {
                        if (index < codeLength - 1) focusRequesters[index + 1].requestFocus()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == codeLength - 1) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        if (index < codeLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    },
                    onDone = {
                        if (codeValues.all { it.isNotEmpty() && it.isNotBlank() && it.isDigitsOnly()}) {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            onCodeInputComplete()
                        }
                    }
                ),
                shape = RoundedCornerShape(8.dp),
                isError = isError,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W500
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff7cbb84),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

        }
    }

    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}