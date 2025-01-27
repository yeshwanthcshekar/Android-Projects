package com.example.amphibians.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.example.amphibians.R
import com.example.amphibians.ui.theme.screens.HomeScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibiansApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {AmphibiansTopAppBar(scrollBehavior = scrollBehavior)}
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(contentPadding = it)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibiansTopAppBar(scrollBehavior: TopAppBarScrollBehavior,
                        modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(scrollBehavior = scrollBehavior,
        title = {
            Text(
                stringResource(R.string.amphibians),
                style = MaterialTheme.typography.headlineSmall)
        },
        modifier = modifier)
}