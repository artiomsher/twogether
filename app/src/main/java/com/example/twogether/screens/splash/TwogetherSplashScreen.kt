package com.example.twogether.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.twogether.R
import com.example.twogether.navigation.TwogetherScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url("https://assets7.lottiefiles.com/packages/lf20_HBmdKwR35P.json")
    )

    LaunchedEffect(key1 = true, block = {
        delay(1000L)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(TwogetherScreens.LoginScreen.name)
        } else {
            navController.navigate(TwogetherScreens.HomeScreen.name)
        }
    })

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 110.dp),
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium
        )
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
    }
}