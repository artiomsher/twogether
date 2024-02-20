package com.example.twogether.screens.pair.request

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.twogether.R
import com.example.twogether.components.TwogetherAppBar
import com.example.twogether.components.login.ButtonDivider
import com.example.twogether.model.PairRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PairRequestScreen(
    navController: NavController,
    viewModel: PairRequestViewModel = hiltViewModel(),
    uniqueCode: String
)
{
    Scaffold(topBar = {
        TwogetherAppBar(
            title = stringResource(id = R.string.pair_request_screen_title),
            navController = navController
        )
    })
    {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PairScreenView(uniqueCode)
        }
    }
}

@Composable
private fun PairScreenView(uniqueCode: String) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 10.dp)
    ) {
        InvitesSection()
        InviteActionSection(uniqueCode)
    }
}

@Composable
private fun InviteActionSection(
    uniqueCode: String,
) {
    Column {
        UniqueCodeSection(uniqueCode)
        ButtonDivider()
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp)
        ) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier.width(200.dp)) {
                Text(text = "Invite partner", fontSize = 18.sp)
            }
        }
    }
}

@Composable
private fun UniqueCodeSection(uniqueCode: String) {
    val scope = rememberCoroutineScope()
    var isCodeCopied by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember { MutableInteractionSource() }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Column(modifier = Modifier
        .clickable(interactionSource = interactionSource, indication = null)
        {
            clipboardManager.setText(AnnotatedString(uniqueCode))
            isCodeCopied = true
            scope.launch {
                delay(2.seconds)
                isCodeCopied = false
            }
        }
    ) {
        Text(
            text = if (isCodeCopied) "Copied! Send it to your partner!" else "Tap to copy",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 7.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraLight,
            color = if (isCodeCopied) MaterialTheme.colorScheme.tertiary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(97.dp)
                .padding(start = 35.dp, end = 35.dp, bottom = 30.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            for (character in uniqueCode.toList()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(shape = RoundedCornerShape(7.dp))
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                        .padding(10.dp)
                ) {
                    Text(text = character.toString(), fontSize = 32.sp);
                }
            }
        }
    }
}

@Composable
private fun InvitesSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 10.dp)
    ) {
        Text(text = "Invites (0)", fontSize = 18.sp)
        Icon(
            painter = painterResource(R.drawable.baseline_arrow_forward_24),
            contentDescription = stringResource(id = R.string.baseline_arrow_forward),
            modifier = Modifier.height(30.dp)
        )
    }
}
