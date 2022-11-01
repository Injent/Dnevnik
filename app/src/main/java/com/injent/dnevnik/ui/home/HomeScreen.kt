package com.injent.dnevnik.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navBar: @Composable () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
//            Row(
//                modifier = Modifier.padding(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//
//                Row(
//                    modifier = Modifier
//                        .wrapContentWidth()
//                ) {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(imageVector = Icons.Rounded.Notifications, contentDescription = null)
//                    }
//                    Spacer(modifier = androidx.compose.ui.Modifier.width(8.dp))
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
//                    }
//                }
//            }
        },
        bottomBar = navBar,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.offset(y = 64.dp),
                onClick = {  },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "home")
        }
    }
}