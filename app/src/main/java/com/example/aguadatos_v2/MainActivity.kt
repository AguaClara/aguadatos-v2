package com.example.aguadatos_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.aguadatos_v2.ui.screens.Chlorine
import com.example.aguadatos_v2.ui.screens.ClarifiedWater
import com.example.aguadatos_v2.ui.screens.Coagulant
import com.example.aguadatos_v2.ui.screens.ConfirmPlant
import com.example.aguadatos_v2.ui.screens.CreateParameter
import com.example.aguadatos_v2.ui.screens.FilteredWater
import com.example.aguadatos_v2.ui.screens.HomeScreen
import com.example.aguadatos_v2.ui.screens.PlantConfiguration
import com.example.aguadatos_v2.ui.screens.PlantConfigurationConfirm
import com.example.aguadatos_v2.ui.screens.PlantConfigurationOther
import com.example.aguadatos_v2.ui.screens.PlantFlow
import com.example.aguadatos_v2.ui.screens.RawWater
import com.example.aguadatos_v2.ui.screens.Records
import com.example.aguadatos_v2.ui.screens.SignUp
import com.example.aguadatos_v2.ui.screens.VerificationCode
import com.example.aguadatos_v2.ui.screens.WelcomePage
import com.example.aguadatos_v2.ui.theme.AguaDatosv2Theme

//main activity handles navigation
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //uses navController and navHost to handle all navigation routes
            val navController = rememberNavController()

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    //home route
                    composable("home") {
                        HomeScreen(
                            onPlantFlow = { navController.navigate("plant_flow") },
                            onRawWaterTurbidity = { navController.navigate("raw_water") },
                            onCoagulantDosage = { navController.navigate("coagulant") },
                            onFilteredWaterTurbidity = { navController.navigate("filtered_water") },
                            onClarifiedWaterTurbidity = { navController.navigate("clarified_water") },
                            onChlorineDosage = { navController.navigate("chlorine") },
                            onColor = {},
                            onTankVolumes = {},
                            onHomeClick = {},
                            onRecordsClick = {},
                            onGraphsClick = {},
                            onProfileClick = { navController.navigate("plant_configuration") }
                        )
                    }

                    //records route
                    composable("records"){
                        Records()
                    }

                    //chlorine route
                    composable("chlorine"){
                        Chlorine(onBackClick = {navController.navigate("home")}, {}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
                    }

                    //clarified water route
                    composable("clarified_water"){
                        ClarifiedWater(onBackClick = {navController.navigate("home")}, {}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
                    }

                    //filtered water route
                    composable("filtered_water"){
                        FilteredWater(onBackClick = {navController.navigate("home")}, {}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
                    }

                    //plant flow route
                    composable("plant_flow"){
                        PlantFlow(onBackClick = {navController.navigate("home")}, {}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
                    }

                    //raw water route
                    composable("raw_water"){
                        RawWater(onBackClick = {navController.navigate("home")}, {}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
                    }

                    //coagulant route
                    composable("coagulant"){
                        Coagulant(onBackClick = {navController.navigate("home")}, {}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
                    }

                    //plant configuration route
                    composable("plant_configuration") {
                        PlantConfiguration()
                    }

                    //plant configuration other route
                    composable("plant_configuration_other"){
                        PlantConfigurationOther({})
                    }

                    //signup route
                    composable("signup"){
                        SignUp({},{})
                    }

                    //create parameter route
                    composable("create_parameter") {
                        CreateParameter()
                    }

                    //plant configuration confirm route
                    composable("plant_configuration_confirm") {
                        PlantConfigurationConfirm({})
                    }

                    //verification code route
                    composable("verification_code") {
                        VerificationCode({})
                    }

                    //welcome page route
                    composable("welcome_page") {
                        WelcomePage()
                    }

                    //confirm plant route
                    composable("confirm_plant") {
                        ConfirmPlant()
                    }



                }
            }
        }
    }
}

