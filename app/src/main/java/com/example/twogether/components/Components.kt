package com.example.twogether.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.twogether.R
import com.example.twogether.navigation.TwogetherScreens
import com.google.firebase.auth.FirebaseAuth

@Preview
@Composable
fun TwogetherAppBar(
    title: String = "def",
    icon: ImageVector? = null,
    showProfile: Boolean = false,
    showNotifications: Boolean = false,
    navController: NavController? = null,
    onBackAction: () -> Unit = {}
) {
    SmallTopAppBar(title = {
        Row() {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = stringResource(id = R.string.arrow_back_desc),
                    modifier = Modifier.clickable {
                        onBackAction.invoke()
                    })
                Spacer(modifier = Modifier.width(30.dp))
            }
            Text(
                text = title, style = TextStyle(
                    fontWeight = FontWeight.ExtraBold, fontSize = 22.sp
                ), textAlign = TextAlign.Center
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
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(id = R.string.logout_button),
                    modifier = Modifier.size(27.dp)
                )
            }
        }
        if (showNotifications) {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = stringResource(id = R.string.notification_content_desc),
                    modifier = Modifier.size(27.dp)
                )
            }
        }
    }, modifier = Modifier.padding(top = 18.dp))
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}