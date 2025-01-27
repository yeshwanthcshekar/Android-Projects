package com.example.buildagrid

import android.os.Build
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buildagrid.ui.theme.BuildaGridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuildaGridTheme {
                ListGrid()
            }
        }
    }
}


@Composable
fun ListGrid(){
    LazyColumn() {
        items(DataSource.topics){ item ->
            Grid(item, modifier = Modifier)
        }
    }
}


@Composable
fun Grid(topic: Topic, modifier: Modifier){
    Row( horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp,8.dp,8.dp,0.dp)){
        BuildGrid(topic = topic, modifier = Modifier.weight(0.5f).padding(0.dp,0.dp,8.dp,0.dp))
        BuildGrid(topic = topic, modifier = Modifier.weight(0.5f).padding(0.dp,0.dp,0.dp,0.dp))
    }
}


@Composable
fun BuildGrid(topic: Topic, modifier: Modifier){
    Card(modifier = modifier) {
        Row(modifier = Modifier.height(68.dp)) {
            Image(painter = painterResource(topic.imageResourceID),
                contentDescription = "",
                modifier = Modifier.fillMaxHeight()
                    .width(68.dp),
                contentScale = ContentScale.Crop)
            Column() {
                Text(stringResource(topic.course),
                    modifier = Modifier.padding(16.dp,8.dp,16.dp,8.dp))

                Row(modifier = Modifier.padding(8.dp,0.dp,0.dp,8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.baseline_menu_book_24)
                        , contentDescription = "",
                        modifier=Modifier.padding(8.dp,0.dp,8.dp,0.dp))
                    Text("${topic.numberOfTopics}", modifier = Modifier.padding(0.dp,0.dp,8.dp,0.dp))
                }
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BuildaGridTheme {
        Grid(DataSource.topics.get(1), modifier = Modifier)
    }
}