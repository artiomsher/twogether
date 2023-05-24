package com.example.twogether.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.twogether.R
import com.example.twogether.components.TwogetherAppBar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(topBar = {
        TwogetherAppBar(
            title = "Twogether",
            navController = navController,
            showProfile = true,
            showNotifications = true
        )
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(15.dp)
        ) {
            HomeView()
        }
    }
}

@Composable
fun HomeView() {
    Column() {
        FirstSection()
    }
}

@Composable
fun FirstSection() {
    Row(modifier = Modifier
        .height(60.dp)
        .width(150.dp)
        .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(4.dp)), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(30.dp).padding(start = 7.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.calendar),
            contentDescription = stringResource(
                id = R.string.calendar_icon_content_desc
            ),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(modifier = Modifier.padding(start = 5.dp), text = "Calendar", fontWeight = FontWeight.SemiBold)
    }
}
