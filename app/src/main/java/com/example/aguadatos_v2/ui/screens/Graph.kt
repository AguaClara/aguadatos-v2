package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguadatos_v2.R
import com.example.aguadatos_v2.ui.components.BottomNavigationBar
import com.example.aguadatos_v2.ui.components.DataGraph
import com.example.aguadatos_v2.ui.components.GraphButton


@Preview(showBackground = true)
@Composable
fun PreviewGraphScreen() {
    Graph({}, {}, {}, {})
}
@Composable
fun Graph (
    // will make all of the graph functions soon
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onProfileClick: () -> Unit,
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

    val labels = listOf(
        "Plant Flow",
        "Raw Water",
        "Coag. Dose",
        "Chlorine Dose",
        "Filter Turbid.",
        "Clarified Turbid."
    )
    val clickedButtons = remember { mutableStateListOf<String>() }

    Scaffold(
        containerColor = Color(0xffdbecff),
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onRecordsClick = onRecordsClick,
                onGraphsClick = {},
                onProfileClick = onProfileClick,
                currentScreen = "Graph"
            )
        }

    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp)
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //back arrow
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBackClick() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text (
                    text = "GRAPHS",
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(28.dp))
            }
            // columns
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (i in labels.indices step 3) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .height(60.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        // First button in the row
                        GraphButton(
                            text = labels[i],
                            onClick = {
                                if (clickedButtons.contains(labels[i])) {
                                    clickedButtons.remove(labels[i])
                                }
                                else { clickedButtons.add(labels[i]) }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        // Second button in row
                        if (i + 1 < labels.size) {
                            GraphButton(
                                text = labels[i + 1],
                                onClick = {
                                    if (clickedButtons.contains(labels[i+1])) {
                                        clickedButtons.remove(labels[i+1])
                                    }
                                    else { clickedButtons.add(labels[i+1]) }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        }

                        if (i + 2 < labels.size) {
                            GraphButton(
                                text = labels[i+2],
                                onClick = {
                                    if (clickedButtons.contains(labels[i+2])) {
                                        clickedButtons.remove(labels[i+2])
                                    }
                                    else { clickedButtons.add(labels[i+2]) }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        }
                    }
                }
            }
            Column (
                Modifier
                    .fillMaxWidth()
            ) {
                if (clickedButtons.isNotEmpty()) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                    )

                }
            }
            LazyColumn(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(clickedButtons.size) {
                    clickedButtons.forEach { button ->
                        DataGraph(
                            text = button,
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }

        }

    }
}