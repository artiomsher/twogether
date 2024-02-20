package com.example.twogether.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.twogether.R
import com.example.twogether.components.TwogetherAppBar
import com.example.twogether.navigation.TwogetherScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        TwogetherAppBar(
            title = "Home",
            navController = navController,
            showProfile = true,
            showNotifications = true
        )
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HomeView(navController, viewModel)
        }
    }
}

@Composable
fun HomeView(navController: NavController, viewModel: HomeScreenViewModel) {
    val modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp);
    Column(modifier = Modifier.fillMaxSize()) {
        PartnerSection(modifier, navController, viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        ActionSection(Modifier.padding(horizontal = 15.dp))
        EverydayActions(modifier)
        MoreForYouSection(modifier)
    }
}

@Composable
fun ActionSection(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            SmallActionCard(
                title = stringResource(id = R.string.calendar),
                imageVector = ImageVector.vectorResource(id = R.drawable.calendar),
                contentDescription = stringResource(
                    id = R.string.calendar_icon_content_desc
                )
            )
            SmallActionCard(
                title = stringResource(id = R.string.passions),
                imageVector = ImageVector.vectorResource(id = R.drawable.hobbies),
                contentDescription = stringResource(
                    id = R.string.passions_icon_content_desc
                )
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            SmallActionCard(
                title = stringResource(id = R.string.wishes),
                imageVector = ImageVector.vectorResource(id = R.drawable.wishes),
                contentDescription = stringResource(
                    id = R.string.wishes_icon_content_desc
                )
            )
            SmallActionCard(
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
fun SmallActionCard(
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
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
fun PartnerSection(modifier: Modifier = Modifier, navController: NavController, viewModel: HomeScreenViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp))
            .clickable {
                navController.navigate(
                    TwogetherScreens.PairRequestScreen.name + "/${viewModel.uniqueCode.value}"
                )

            }
            .padding(25.dp), horizontalAlignment = Alignment.CenterHorizontally
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
fun EverydayActions(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Text(
        text = stringResource(id = R.string.everyday_actions),
        style = TextStyle(
            fontWeight = FontWeight.ExtraBold, fontSize = 21.sp
        ),
        modifier = modifier.padding(bottom = 10.dp)
    )
    Row(modifier = Modifier.horizontalScroll(scrollState), horizontalArrangement = Arrangement.SpaceBetween) {
        EverydayActionCard(Modifier.padding(start = 15.dp), title = "Attention", imageVector = ImageVector.vectorResource(id = R.drawable.attention), contentDescription = "")
        EverydayActionCard(title = "Location", imageVector = ImageVector.vectorResource(id = R.drawable.location), contentDescription = "")
        EverydayActionCard(title = "Budget", imageVector = ImageVector.vectorResource(id = R.drawable.budget), contentDescription = "")
        EverydayActionCard(title = "Food", imageVector = ImageVector.vectorResource(id = R.drawable.food), contentDescription = "")
        EverydayActionCard(title = "Something", imageVector = ImageVector.vectorResource(id = R.drawable.wishes), contentDescription = "")
    }

}

@Preview
@Composable
fun EverydayActionCard(
    modifier: Modifier = Modifier,
    title: String = "Location",
    imageVector: ImageVector = ImageVector.vectorResource(id = R.drawable.story),
    contentDescription: String = "",
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .height(90.dp)
            .width(100.dp)
            .padding(end = 10.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp))
            .clickable {
                onClick.invoke()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(35.dp),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}

@Composable
fun MoreForYouSection(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.more_for_you),
        style = TextStyle(
            fontWeight = FontWeight.ExtraBold, fontSize = 21.sp
        ),
        modifier = modifier.padding(bottom = 10.dp)
    )
}