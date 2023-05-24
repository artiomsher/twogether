package com.example.twogether.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.twogether.R
import com.example.twogether.navigation.TwogetherNavigation
import com.example.twogether.navigation.TwogetherScreens
import com.google.firebase.auth.FirebaseAuth

@Preview
@Composable
fun TwogetherAppBar(
    title: String = "def",
    icon: ImageVector? = null,
    showProfile: Boolean = false,
    navController: NavController? = null,
    onBackAction: () -> Unit = {}
) {
    SmallTopAppBar(title = {
        Row() {
            if (showProfile) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.logo_content_desc),
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .scale(0.8f)
                )
            }
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = stringResource(id = R.string.arrow_back_desc), modifier = Modifier.clickable {
                    onBackAction.invoke()
                })
                Spacer(modifier = Modifier.width(30.dp))
            }
            Text(
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(150.dp))
        }
    }, actions = {
        if (showProfile) {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController?.navigate(TwogetherScreens.LoginScreen.name)
                }
            }) {
                Icon(imageVector = Icons.Filled.Logout, contentDescription = stringResource(id = R.string.logout_button))
            }
        } else {
            Box {

            }
        }
    })
}