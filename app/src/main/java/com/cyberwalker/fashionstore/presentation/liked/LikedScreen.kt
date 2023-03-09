package com.cyberwalker.fashionstore.presentation.liked


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.dump.BottomNav


@Composable
fun LikedScreen(
    viewModel: LikedViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    //onAction: (actions: LikedScreenActions) -> Unit,
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        LikedScreenContent(modifier = Modifier.padding(innerPadding))
            //, onAction = onAction)
    }
}

@Composable
private fun LikedScreenContent(
    modifier: Modifier,
    //onAction: (actions: LikedScreenActions) -> Unit,
){
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Liked Screen" }
    ) {
        Row(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Liked Screen",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}
sealed class LikedScreenActions {
    object Home : LikedScreenActions()
}
//@Preview(showBackground = true)
//@Composable
//fun LikedScreenPreview() {
//    LikedScreen()
//}