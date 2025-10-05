package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Horizontal
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R
import com.example.aguadatos_v2.ui.components.BottomNavigationBar
import com.example.aguadatos_v2.ui.components.BottomNavButton

/*
* tried to finish this screen as well, but since internship ends, I dont have time (double check with patrick for what to do)
* */


@Composable
public fun Coagulant(
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onHomeClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onGraphsClick: () -> Unit,
    onProfileClick: () -> Unit
){

    var waterInflow by remember { mutableStateOf("") }
    var startVolume by remember { mutableStateOf("") }
    var endVolume by remember { mutableStateOf("") }
    //var waterInflow by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xffe4effc),
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onRecordsClick = onRecordsClick,
                onGraphsClick = onGraphsClick,
                onProfileClick = onProfileClick,
                currentScreen = "Home"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBackClick() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "COAGULANT DOSAGE",
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(44.dp))
            }

            Text(
                text = "Reminder: the chemical type is ___ and the chemical concentration is #",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 24.dp)
            )





            Button(
                onClick = onSubmitClick,
                //{ /* submit things*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF77AF87),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .height(40.dp)
            ) {
                Text(text = "SUBMIT")
            }
        }
    }



}