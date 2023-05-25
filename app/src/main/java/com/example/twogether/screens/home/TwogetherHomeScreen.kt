package com.example.twogether.screens.home

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Column(modifier = Modifier.fillMaxSize()) {
        PartnerSection()
        ActionSection()
        EverydayActions()
    }
}

@Composable
fun ActionSection() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            ActionCard(
                title = stringResource(id = R.string.calendar),
                imageVector = ImageVector.vectorResource(id = R.drawable.calendar),
                contentDescription = stringResource(
                    id = R.string.calendar_icon_content_desc
                )
            )
            ActionCard(
                title = stringResource(id = R.string.passions),
                imageVector = ImageVector.vectorResource(id = R.drawable.hobbies),
                contentDescription = stringResource(
                    id = R.string.passions_icon_content_desc
                )
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            ActionCard(
                title = stringResource(id = R.string.wishes),
                imageVector = ImageVector.vectorResource(id = R.drawable.wishes),
                contentDescription = stringResource(
                    id = R.string.wishes_icon_content_desc
                )
            )
            ActionCard(
                title = stringResource(id = R.string.our_story),
                imageVector = ImageVector.vectorResource(id = R.drawable.story),
                contentDescription = stringResource(
                    id = R.string.our_story_icon_desc
                )
            )
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .height(65.dp)
            .fillMaxWidth()
            .padding(bottom = 9.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp))
            .clickable {
                onClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(50.dp)
                .padding(start = 3.dp, end = 10.dp),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(text = title, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun PartnerSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.partner),
            contentDescription = stringResource(id = R.string.partner_picture_content_desc),
            modifier = Modifier
                .clip(CircleShape)
                .size(220.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(text = "In relationship with ...", modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun EverydayActions() {
    Text(
        text = stringResource(id = R.string.everyday_actions),
        style = TextStyle(
            fontWeight = FontWeight.ExtraBold, fontSize = 22.sp
        ),
        modifier = Modifier.padding(top = 20.dp)
    )


}





