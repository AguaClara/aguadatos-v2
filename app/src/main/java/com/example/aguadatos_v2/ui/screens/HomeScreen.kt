package com.example.aguadatos_v2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.aguadatos_v2.ui.components.HomeButton
import com.example.aguadatos_v2.ui.components.BottomNavigationBar
import com.example.aguadatos_v2.ui.components.BottomNavButton

/*
*
* Draft data mapping and field relationships (for GraphQL schema implementation)
* - this screen navigates to all other screens, but has no fields or data entry in this screen
* */


/*
* Sign up composable:
*
* Intent & Design:
*  - Home screen to allow user to navigate to data entry, profile, records, and graph screens
*  - since the user has the option to create other parameters, takes a list of the parameters used and creates all the home buttons accordingly
* */


@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen({},{},{},{},{},{},{},{},{},{},{},{})
}


@Composable
fun HomeScreen(
    onPlantFlow: () -> Unit,
    onRawWaterTurbidity: () -> Unit,
    onCoagulantDosage: () -> Unit,
    onFilteredWaterTurbidity: () -> Unit,
    onClarifiedWaterTurbidity: () -> Unit,
    onChlorineDosage: () -> Unit,
    onColor: () -> Unit,
    onTankVolumes: () -> Unit,
    onHomeClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onGraphsClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    //labels and handlers of all parameters that need buttons
    val labels = listOf(
        stringResource(R.string.plant_flow),
        stringResource(R.string.raw_water_turbidity),
        stringResource(R.string.coagulant_dosage),
        stringResource(R.string.filtered_water_turbidity),
        stringResource(R.string.clarified_water_turbidity),
        stringResource(R.string.chlorine_dosage),
        stringResource(R.string.color),
        stringResource(R.string.tank_volumes)
    )
    val onClickHandlers = listOf(
        onPlantFlow,
        onRawWaterTurbidity,
        onCoagulantDosage,
        onFilteredWaterTurbidity,
        onClarifiedWaterTurbidity,
        onChlorineDosage,
        onColor,
        onTankVolumes
    )


    Scaffold(
        containerColor = Color(0xffdbecff),
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
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //back arrow
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = stringResource(R.string.aguadatos_logo),
                    modifier = Modifier
                        .weight(7f)
                        .height(62.dp)
                )
                Spacer(modifier = Modifier.weight(5f))
            }

            //heading
            Text(
                text = stringResource(R.string.types_of_entries),
                fontSize = 30.sp,
                color = Color(0xff000000),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 20.dp, top = 8.dp, end = 8.dp, bottom = 12.dp)
            )

            //buttons section
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                // Create rows dynamically based on labels list
                for (i in labels.indices step 2) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        // First button in the row
                        HomeButton(
                            text = labels[i],
                            onClick = onClickHandlers[i],
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )

                        // Second button in the row (if it exists)
                        if (i + 1 < labels.size) {
                            HomeButton(
                                text = labels[i + 1],
                                onClick = onClickHandlers[i + 1],
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        }
                    }
                }
            }
        }
    }
}





