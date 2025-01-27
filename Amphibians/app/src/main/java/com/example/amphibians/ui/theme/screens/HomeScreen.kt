package com.example.amphibians.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.network.Amphibian


private const val TAG = "HomeScreen"


@Composable
fun HomeScreen(contentPadding: PaddingValues = PaddingValues(4.dp)) {

    val amphibianViewModel : AmphibianViewModel = viewModel(factory = AmphibianViewModel.Factory)
    val amphibianUiState by amphibianViewModel.amphibianUiState.collectAsState()
    var successState : AmphibianUiState.Success = AmphibianUiState.Success(emptyList())
    /*val tempAmphibian = Amphibian(name = stringResource(R.string.amphibians),
        type = "Toad", description = stringResource(R.string.dummyDescription), imageSource = "")*/

    Log.i(TAG,"Before When statement")
    when (amphibianUiState){
        is AmphibianUiState.Loading ->{
        }
        is AmphibianUiState.Success -> {
           successState  = amphibianUiState as AmphibianUiState.Success
            LazyColumn(userScrollEnabled = true,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items = successState.amphibianList, key = { it -> it.name}) {
                        amphibian -> AmphibianCard(amphibian)
                }
            }
        }
        is AmphibianUiState.Error -> {
        }
    }

}


@Composable
fun AmphibianCard(amphibian : Amphibian, modifier: Modifier = Modifier){
    Card(modifier = Modifier.padding(8.dp),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(4.dp)) {
        Column(){
            Text("${amphibian.name} (${amphibian.type})",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp))
            AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
                .data(amphibian.imageSource)
                .crossfade(true)
                .build(),
                error = painterResource(R.drawable.ic_connection_error),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "Amphibian Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth())
            Text(amphibian.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(8.dp)
                )
        }

    }
}


@Preview(showBackground = true, showSystemUi = false)
@Composable
fun CardPreview(){
    HomeScreen()
}