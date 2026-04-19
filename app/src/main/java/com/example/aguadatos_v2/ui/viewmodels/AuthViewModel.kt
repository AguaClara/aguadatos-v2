package com.example.aguadatos_v2.ui.viewmodels

import com.amplifyframework.core.Amplify
import com.example.aguadatos_v2.AguaDatosAmplify
import com.example.aguadatos_v2.AmplifyService
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import com.amplifyframework.core.model.temporal.Temporal

import com.amplifyframework.datastore.generated.model.Plant
import com.amplifyframework.datastore.generated.model.Operator
import com.amplifyframework.datastore.generated.model.InflowEntry
import com.amplifyframework.datastore.generated.model.RawEntry

import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val plantCode : String = "",
    var password: String = ""
)

data class LoginState(
    val name: String = "",
    val email: String = "",
    var password: String = ""
)

data class VerificationState(
    val email: String = "",
    val code: String = ""
)

class AuthViewModel : ViewModel() {
    private val amplifyService: AmplifyService = AguaDatosAmplify()
    var signUpState = mutableStateOf(SignUpState())
        private set
    var loginState = mutableStateOf(LoginState())
        private set
    var verificationState = mutableStateOf(VerificationState())
        private set
    var currentUserId = mutableStateOf<String?>(null)
        private set

    fun updateSignUpState(
        name: String? = null,
        email: String? = null,
        plantCode: String? = null,
        password: String? = null
    ) {
        signUpState.value = signUpState.value.copy(
            name      = name ?: signUpState.value.name,
            email     = email ?: signUpState.value.email,
            plantCode = plantCode ?: signUpState.value.plantCode,
            password  = password ?: signUpState.value.password
        )
    }

    fun updateLoginState(
        name: String? = null,
        email: String? = null,
        password: String? = null
    ) {
        loginState.value = loginState.value.copy(
            name      = name ?: signUpState.value.name,
            email     = email ?: signUpState.value.email,
            password  = password ?: signUpState.value.password
        )
    }

    fun updateVerificationState(
        code: String? = null,
        email: String? = null
    ) {
        verificationState.value = verificationState.value.copy(
            email = email ?: verificationState.value.email,
            code  = code ?: verificationState.value.code
        )
    }

    fun configureAmplify(context : Context) {
        amplifyService.configureAmplify(context)
    }
    fun signUp(
        onSuccess: () -> Unit,
        onError: (msg : String) -> Unit
    ) {
        val state = signUpState.value
        val attributes = listOf(
            AuthUserAttribute(AuthUserAttributeKey.name(), state.name),
            AuthUserAttribute(AuthUserAttributeKey.email(), state.email),
//      AuthUserAttribute(AuthUserAttributeKey.custom("plantCode"), state.plantCode)
        )
        val options = AuthSignUpOptions.builder()
            .userAttributes(attributes)
            .build()
        Amplify.Auth.signUp(
            state.email,
            state.password,
            options,
            { result ->
                onSuccess()
            },
            { error ->
                onError(error.localizedMessage ?: "Sign up failed")
            }
        )
    }

    fun login(
        onSuccess: () -> Unit,
        onError: (msg : String) -> Unit
    ) {
        val state = loginState.value
        Amplify.Auth.signIn(
            state.email,
            state.password,
            { onSuccess() },
            { error ->
                onError(error.localizedMessage ?: "Login Failed")
            }
        )
    }

    fun logout( onSuccess: () -> Unit) {
        Amplify.Auth.signOut(
            { viewModelScope.launch(Dispatchers.Main) { onSuccess() } }
        )
    }

    fun confirmCode(
        code: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        Amplify.Auth.confirmSignUp(
            signUpState.value.email,
            code,
            {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            },
            { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error.localizedMessage ?: "Confirm code error") }
            }
        )
    }

    fun resendCode(
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {
        Amplify.Auth.resendSignUpCode(
            signUpState.value.email,
            {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            },
            { error ->
                viewModelScope.launch(Dispatchers.Main) { onError(error.localizedMessage ?: "Resend code error") }
            }
        )

    }

    fun createOperator(
        cognitoId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
//        val operator = Operator.builder()
//            .name(loginState.value.name) // you can replace later
//            .plantID("default-plant-id") // temp for now
//            .build()
    }
}