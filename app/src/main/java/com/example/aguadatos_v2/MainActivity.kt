package com.example.aguadatos_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.aguadatos_v2.ui.screens.Chlorine
import com.example.aguadatos_v2.ui.screens.ClarifiedWater
import com.example.aguadatos_v2.ui.screens.Coagulant
import com.example.aguadatos_v2.ui.screens.CoagulantSubmission
import com.example.aguadatos_v2.ui.screens.Color
import com.example.aguadatos_v2.ui.screens.ConfirmPlant
import com.example.aguadatos_v2.ui.screens.ConfirmScreen
import com.example.aguadatos_v2.ui.screens.CreateParameter
import com.example.aguadatos_v2.ui.screens.FilteredWater
import com.example.aguadatos_v2.ui.screens.HomeScreen
import com.example.aguadatos_v2.ui.screens.LoginPage
import com.example.aguadatos_v2.ui.screens.PlantConfiguration
import com.example.aguadatos_v2.ui.screens.PlantConfigurationConfirm
import com.example.aguadatos_v2.ui.screens.PlantConfigurationOther
import com.example.aguadatos_v2.ui.screens.PlantFlow
import com.example.aguadatos_v2.ui.screens.RawWater
import com.example.aguadatos_v2.ui.screens.Records
import com.example.aguadatos_v2.ui.screens.SignUp
import com.example.aguadatos_v2.ui.screens.SubmittedConfirmScreen
import com.example.aguadatos_v2.ui.screens.TankVolumes
import com.example.aguadatos_v2.ui.screens.VerificationCode
import com.example.aguadatos_v2.ui.screens.WelcomePage

import com.example.aguadatos_v2.ui.viewmodels.AuthViewModel
import com.example.aguadatos_v2.ui.viewmodels.DataViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//main activity handles navigation
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      //uses navController and navHost to handle all navigation routes
      val navController = rememberNavController()
      val authViewModel: AuthViewModel = viewModel()
      val dataViewModel: DataViewModel = viewModel()
      authViewModel.configureAmplify(this)

      Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
          navController = navController,
          startDestination = "welcome",
          modifier = Modifier.padding(innerPadding)
        ) {

          // wecome page route
          composable("welcome") {
            WelcomePage(
              onCreateAccountClick = { navController.navigate("signup") },
              onLoginClick = { navController.navigate("login") }
            )
          }

          //signup route
          composable("signup"){
            SignUp (
              authViewModel = authViewModel,
              onSignUp = { navController.navigate("verification_code") },
//              onSignUp = { navController.navigate("home") },
              onLogInClick = { navController.navigate("login") }
            )
          }

          //verification code route
          composable("verification_code") {
            VerificationCode(
              authViewModel = authViewModel,
              onSubmitClick = { navController.navigate("home") }
            )
          }

          //confirm plant route
          composable("confirm_plant") {
            ConfirmPlant()
          }

          composable("login") {
            LoginPage(
              onLoginClick = { navController.navigate("home") },
              onSignupClick = {navController.navigate(route = "signup")}
            )
          }

          //home route
          composable("home") {
            HomeScreen(
              onPlantFlow = { navController.navigate("plant_flow") },
              onRawWaterTurbidity = { navController.navigate("raw_water") },
              onCoagulantDosage = { navController.navigate("coagulant") },
              onFilteredWaterTurbidity = { navController.navigate("filtered_water") },
              onClarifiedWaterTurbidity = { navController.navigate("clarified_water") },
              onChlorineDosage = { navController.navigate("chlorine") },
              onColor = { navController.navigate("color") },
              onTankVolumes = { navController.navigate("tank_volumes") },
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
            Chlorine(
              onBackClick = {navController.popBackStack()},
              {},
              onHomeClick = {navController.navigate("home")},
              onRecordsClick = {navController.navigate("records")},
              {},
              {}
            )
          }

          //clarified water route
          composable("clarified_water"){
            ClarifiedWater(
              onBackClick = {navController.popBackStack()},
              {},
              onHomeClick = {navController.navigate("home")},
              onRecordsClick = {navController.navigate("records")},
              {},
              {}
            )
          }

          //color route
          composable("color") {
            Color(onBackClick = {navController.popBackStack()},{}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
          }

          //tank volumes route
          composable("tank_volumes") {
            TankVolumes(
                onBackClick = { navController.popBackStack() },
                onSubmitClick = {},
                onHomeClick = { navController.navigate("home") },
                onRecordsClick = { navController.navigate("records") },
                onGraphsClick = {},
                onProfileClick = { navController.navigate("plant_configuration") }
            )
          }

          //filtered water route
          composable("filtered_water"){
            FilteredWater(onBackClick = {navController.popBackStack()}, {}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
          }

          //plant flow route
          composable("plant_flow"){
            PlantFlow(onBackClick = {navController.popBackStack()}, {}, onHomeClick = {navController.navigate("home")}, onRecordsClick = {navController.navigate("records")}, {}, {})
          }

          //raw water route
          composable("raw_water"){
            RawWater(
              dataViewModel = dataViewModel,
              onBackClick = {navController.popBackStack()},
              {},
              onHomeClick = {navController.navigate("home")},
              onRecordsClick = {navController.navigate("records")},
              {},
              {}
            )
          }

          //coagulant route
          composable("coagulant"){
            Coagulant(
              onBackClick = {navController.popBackStack()},
              onSubmitClick = { submission ->
                navController.currentBackStackEntry
                  ?.savedStateHandle
                  ?.set("coagulantSubmission", submission)
                navController.navigate("confirmation") },
              onHomeClick = {navController.navigate("home")},
              onRecordsClick = {navController.navigate("records")},
              {},
              {}
            )
          }

          // confirmation route
          composable("confirmation") {

            val submission =
              navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<CoagulantSubmission>("coagulantSubmission")

            if (submission != null) {

              ConfirmScreen(
                date = submission.date,
                chemicalType = submission.chemicalType,
                sliderPosition = submission.sliderPosition,
                inflowRate = submission.inflowRate,
                startVolume = submission.startVolume,
                endVolume = submission.endVolume,
                timeElapsed = submission.timeElapsed,
                chemicalDose = submission.chemicalDose,
                chemicalFlowRate = submission.chemicalFlowRate,

                onBackClick = { navController.popBackStack() },

                onSubmitClick = {
                  println("CONFIRM CLICKED")

                  navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("coagulantSubmission", submission)

                  navController.navigate("submitted_confirm")
                },

                onHomeClick = { navController.navigate("home") },
                onGraphsClick = { navController.navigate("graphs") },
                onRecordsClick = { navController.navigate("records") },
                onProfileClick = { navController.navigate("profile") }
              )
            }
          }

          //submitted confirm route
          composable("submitted_confirm") {

            val submission =
              navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<CoagulantSubmission>("coagulantSubmission")

            if (submission != null) {
              SubmittedConfirmScreen(
                date = submission.date,
                time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                chemicalType = submission.chemicalType,
                sliderPosition = submission.sliderPosition,
                inflowRate = submission.inflowRate,
                startVolume = submission.startVolume,
                endVolume = submission.endVolume,
                timeElapsed = submission.timeElapsed,
                chemicalDose = submission.chemicalDose,
                chemicalFlowRate = submission.chemicalFlowRate,
                onBackClick = { navController.popBackStack() },
                onHomeClick = { navController.navigate("home") },
                onGraphsClick = { navController.navigate("graphs") },
                onRecordsClick = { navController.navigate("records") },
                onProfileClick = { navController.navigate("profile") }
              )
            }
          }

          //plant configuration route
          composable("plant_configuration") {
            PlantConfiguration(
                onBackClick = { navController.popBackStack() },

            )
          }

          //plant configuration other route
          composable("plant_configuration_other"){
            PlantConfigurationOther({})
          }

          //create parameter route
          composable("create_parameter") {
            CreateParameter()
          }

          //plant configuration confirm route
          composable("plant_configuration_confirm") {
            PlantConfigurationConfirm({})
          }
        }
      }
    }
  }
}

