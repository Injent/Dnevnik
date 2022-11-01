package com.injent.dnevnik.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.injent.dnevnik.NavActions
import com.injent.dnevnik.R
import com.injent.dnevnik.domain.repository.Repository
import com.injent.dnevnik.repository

@Composable
fun ProfileScreen(
    navActions: NavActions,
    userId: Long,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    viewModel.loadUser(userId)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.user != null) {
            Text(text = stringResource(id = R.string.birthday) + " ${state.user!!.birthday}")
        }
    }
}

@Preview
@Composable
fun Card() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
    }
}