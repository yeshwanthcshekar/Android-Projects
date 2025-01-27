package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtspaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtspaceTheme {
                ArtSpace(modifier = Modifier)
            }
        }
    }
}

data class ArtWork(val ImageID: Int, val ArtWorkName : String, val ArtistName : String)

@Composable
fun ArtSpace(modifier: Modifier){
    var image by remember { mutableStateOf(R.drawable.pexels_ekrulila_3246665) }
    var artWork by remember { mutableStateOf("pexels_ekrulila") }
    var artWorkYear by remember { mutableStateOf("3246665") }

    Column(verticalArrangement = Arrangement.Top, modifier = modifier.fillMaxSize()
        .padding(32.dp)) {
        Image(painter = painterResource(id = image), "", modifier= modifier.weight(0.75f)
            .padding(16.dp)
            .align(Alignment.CenterHorizontally), contentScale = ContentScale.Crop)

        Text(artWork, modifier = modifier.align(Alignment.CenterHorizontally).padding(8.dp),
            style = MaterialTheme.typography.displaySmall)

        Text(artWorkYear, modifier = modifier
            .align(Alignment.CenterHorizontally).padding(8.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.padding(8.dp)
           ){
            Button(onClick = { val imageData = imageChange(true,false, image)
                image = imageData.ImageID
                artWork = imageData.ArtWorkName
                artWorkYear = imageData.ArtistName },
                modifier = modifier.weight(0.5f).padding(8.dp)
            ) {
                Text("Previous")
            }

            Button(onClick = { val imageData = imageChange(false, true, image)
                image = imageData.ImageID
                artWork = imageData.ArtWorkName
                artWorkYear = imageData.ArtistName },
                modifier = modifier.weight(0.5f).padding(8.dp)
            ) {
                Text("Next")
            }
        }
    }
}

fun imageChange(previous: Boolean, next: Boolean, imageRes: Int): ArtWork {
    //pexels_ekrulila_3246665 = 1
    //pexels_daiangan_102127 = 2
    //pexels_pixabay_161154 = 3

    return when {
        previous && imageRes == R.drawable.pexels_ekrulila_3246665 -> ArtWork(R.drawable.pexels_pixabay_161154,"Pexels Pixabay", "25 Janurary 1978") // Previous from 1st image
        previous && imageRes == R.drawable.pexels_daiangan_102127 -> ArtWork(R.drawable.pexels_ekrulila_3246665, "pexels_ekrulila", "3246665") // Previous from 2nd image
        previous && imageRes == R.drawable.pexels_pixabay_161154 -> ArtWork(R.drawable.pexels_daiangan_102127, "pexels_daiangan", "102127") // Previous from 3rd image
        next && imageRes == R.drawable.pexels_ekrulila_3246665 -> ArtWork(R.drawable.pexels_pixabay_161154,"Pexels Pixabay", "25 Janurary 1978") // Next from 1st image
        next && imageRes == R.drawable.pexels_daiangan_102127 -> ArtWork(R.drawable.pexels_ekrulila_3246665, "pexels_ekrulila", "3246665") // Next from 2nd image
        next && imageRes == R.drawable.pexels_pixabay_161154 -> ArtWork(R.drawable.pexels_daiangan_102127, "pexels_daiangan", "102127")// Next from 3rd image
        else -> ArtWork(R.drawable.pexels_daiangan_102127, "pexels_daiangan", "102127") // Default case: no change
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ArtspaceTheme {
        ArtSpace(modifier = Modifier)
    }
}