package com.example.aguadatos_v2.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R
import com.example.aguadatos_v2.ui.components.BottomNavigationBar
import com.example.aguadatos_v2.ui.components.BottomNavButton
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class DisplayRow(
    val labelRes: Int,
    val value: String,
    val unitRes: String? = null
)

interface DataSubmission : Serializable {
    val date: String
    val screenTitle: Int
    val displayRows: List<DisplayRow>
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
public fun ConfirmScreen(
    submission: DataSubmission,
    onGraphsClick: () -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit
) {
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
                Text(
                    text = stringResource(submission.screenTitle),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.please_confirm_your_entry),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Date
                    Text(
                        text = "• " + stringResource(R.string.date) + " ${submission.date}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    submission.displayRows.forEach { (labelRes, value, unitRes) ->
                        Text(
                            text = "• " + stringResource(labelRes) + ":"  + " $value " + unitRes,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }

                    Divider(thickness = 1.dp, color = Color(0xFFE0E0E0))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(color = 0xFF77AF87)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.go_back), fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { onSubmitClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF77AF87),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.confirm), fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SubmittedConfirmScreen(
    submission: DataSubmission,
    time: String,
    onGraphsClick: () -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onBackClick: () -> Unit,
) {
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
                Text(
                    text = stringResource(submission.screenTitle),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.submission_complete),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = stringResource(R.string.submitted_on) + " ${submission.date} " + stringResource(R.string.at) + " $time",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = stringResource(R.string.entry_details),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Date
                    Text(
                        text = "• " + stringResource(R.string.date) + " ${submission.date}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // One row per field the submission wants shown
                    submission.displayRows.forEach { (labelRes, value, unitRes) ->
                        Text(
                            text = "• " + stringResource(labelRes) + ":" + " $value " + unitRes,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(thickness = 1.dp, color = Color(0xFFE0E0E0))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.back), fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onHomeClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF77AF87),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Home", fontSize = 16.sp)
                }
            }
        }
    }
}