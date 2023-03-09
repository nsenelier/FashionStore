package com.cyberwalker.fashionstore.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.dump.BottomNav

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    //onAction: (actions: ProfileScreenActions) -> Unit,
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        ProfileScreenContent(modifier = Modifier.padding(innerPadding))
            //, onAction = onAction)
    }
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier,
    //onAction: (actions: ProfileScreenActions) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Profile Screen" }
    ) {
        Row(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Profile Screen",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}

sealed class ProfileScreenActions{
    object Details: ProfileScreenActions()
}