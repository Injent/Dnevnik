package com.injent.dnevnik.ui.topstudents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.injent.dnevnik.NavActions
import com.injent.dnevnik.domain.model.Student
import com.injent.dnevnik.ui.components.AnimatedShimmer
import com.injent.dnevnik.ui.theme.Magenta
import com.injent.dnevnik.ui.theme.dimen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Students(
    navBar: @Composable () -> Unit,
    navActions: NavActions,
    viewModel: StudentsViewModel = hiltViewModel(),
    scope: CoroutineScope
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var refreshing by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = navBar,
        topBar = {
            LazyRow(
                modifier = Modifier
                    .padding(MaterialTheme.dimen.medium),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.medium)
            ) {
                items(state.periods) { period ->
                    FilterChip(
                        enabled = !state.loading,
                        label = { Text(text = period.name, color = MaterialTheme.colorScheme.onPrimary) },
                        selected = state.selectedPeriodId == period.idStr,
                        onClick = { viewModel.switchPeriod(period.idStr) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .7f),
                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = .5f),
                            disabledSelectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = .6f)
                        ),
                        shape = MaterialTheme.shapes.large,
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Color.Transparent,
                            selectedBorderColor = Color.Transparent,
                            borderWidth = 0.dp,
                            selectedBorderWidth = 0.dp
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        LaunchedEffect(refreshing) {
            delay(500)
            refreshing = false
        }
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = {
                refreshing = true
                viewModel.refreshAverageMarks()
            }
        ) {
            if (state.loading) {
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.dimen.medium,
                            end = MaterialTheme.dimen.medium,
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                ) {
                    items(12) {
                        StudentCardShimmer()
                        Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
                    }
                }
                return@SwipeRefresh
            }

            LazyColumn(
                Modifier.padding(
                    start = MaterialTheme.dimen.medium,
                    end = MaterialTheme.dimen.medium,
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                itemsIndexed(state.classmates) { index, item ->
                    StudentCard(item, index + 1, state.myId) { userId ->
                        navActions.navigateToProfile(userId)
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCard(student: Student, index: Int, myId: Long, onClick: (userId: Long) -> Unit) {
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = .5f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(MaterialTheme.dimen.medium)
            .fillMaxWidth()
            .clickable { onClick(student.userID) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.small)
        ) {
            Text(text = "$index. ${student.shortName}", color = MaterialTheme.colorScheme.onTertiaryContainer)
            if (student.userID == myId)
                Icon(imageVector = Icons.Rounded.Star, contentDescription = null, tint = Magenta)
        }
        Text(
            text = student.averageMark.toString(),
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Preview
@Composable
fun StudentCardShimmer() {
    AnimatedShimmer { brush ->
        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = .5f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(MaterialTheme.dimen.medium)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(modifier = Modifier
                .fillMaxWidth()
                .background(brush = brush, shape = MaterialTheme.shapes.small),
                text = " "
            )
        }
    }
}