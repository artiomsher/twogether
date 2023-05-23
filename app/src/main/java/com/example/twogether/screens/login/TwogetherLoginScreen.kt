package com.example.twogether.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.twogether.R
import com.example.twogether.components.login.EmailInput
import com.example.twogether.components.login.PasswordInput
import com.example.twogether.components.login.SubmitButton
import com.example.twogether.navigation.TwogetherScreens

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (showLoginForm.value) {
                UserForm(loading = false, isCreateAccount = false) { email, password ->
                    viewModel.signInWithEmailAndPassword(email, password) {
                        navController.navigate(TwogetherScreens.HomeScreen.name)
                    }
                }
            } else {
                UserForm(loading = false, isCreateAccount = true) { email, password ->
                    viewModel.createUserWithEmailAndPassword(email, password) {
                        navController.navigate(TwogetherScreens.HomeScreen.name)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = if (showLoginForm.value) stringResource(id = R.string.sign_up)
                        else stringResource(id = R.string.login)

            Text(
                text = text,
                modifier = Modifier.clickable {
                    showLoginForm.value = !showLoginForm.value
                }.padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(
            rememberScrollState()
        )

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) Text(
            text = stringResource(id = R.string.registration_note)
        ) else Text(text = "")

        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = stringResource(id = R.string.password),
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )

        SubmitButton(
            text = if (isCreateAccount) stringResource(id = R.string.create_account)
            else stringResource(id = R.string.login),
            loading = loading,
            validInputs = valid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}