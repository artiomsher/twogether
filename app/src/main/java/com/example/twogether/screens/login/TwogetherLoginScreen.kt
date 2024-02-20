package com.example.twogether.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.twogether.R
import com.example.twogether.components.TwogetherAppBar
import com.example.twogether.components.login.ButtonDivider
import com.example.twogether.components.login.EmailInput
import com.example.twogether.components.login.PasswordInput
import com.example.twogether.components.login.SubmitButton
import com.example.twogether.navigation.TwogetherScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
    val topBarTitle =
        if (showLoginForm.value) {
            stringResource(id = R.string.top_bar_login)
        } else {
            stringResource(id = R.string.top_bar_register)
        }
    Scaffold(topBar = {
        TwogetherAppBar(title = topBarTitle, navController = navController)
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
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
                ButtonDivider()

                Row(
                    modifier = Modifier.padding(15.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    val text = if (showLoginForm.value) stringResource(id = R.string.sign_up)
                    else stringResource(id = R.string.login)

                    Text(
                        text = text,
                        modifier = Modifier
                            .clickable {
                                showLoginForm.value = !showLoginForm.value
                            }
                            .padding(start = 5.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }


        }
    }

}



@Preview
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

        EmailInput(
            modifier = Modifier.padding(horizontal = 10.dp),
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            })

        PasswordInput(
            modifier = Modifier
                .focusRequester(passwordFocusRequest)
                .padding(horizontal = 10.dp),
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