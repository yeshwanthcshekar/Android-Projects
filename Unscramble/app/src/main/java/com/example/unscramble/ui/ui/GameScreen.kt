package com.example.unscramble.ui.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.R


@SuppressLint("SuspiciousIndentation")
@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()){

    val gameUiState by gameViewModel.uiState.collectAsState()

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {

            Text("Unscramble",
                style = typography.titleLarge,
                modifier = Modifier.padding(8.dp))

            GameLayout(currentScrambledWord =  gameUiState.currentScrambledWord,
                userGuessWord = gameViewModel.userGuess,
                onUserGuessChanged = {gameViewModel.updateUserGuessWord(it)},
                wordCount = gameUiState.wordCount,
                isGuessWrong = gameUiState.isGuessWrong)

            Button(
                onClick = { gameViewModel.verifyUserGuessedWord()},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Submit",
                    modifier = Modifier,
                    style = typography.titleMedium)
            }

            OutlinedButton(
                onClick = { gameViewModel.skipWord()},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 4.dp, 8.dp, 8.dp)
            ) {
                Text("Skip", style = typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Score: ${gameUiState.score}",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                style = typography.titleMedium)

            if(gameUiState.isGameOver){
                FinalScoreDialog(score = gameUiState.score,
                    onPlayAgain = { gameViewModel.resetGame() }
                )
            }
        }
}


@Composable
fun GameLayout(
    currentScrambledWord: String,
    userGuessWord: String,
    onUserGuessChanged:(String) -> Unit,
    wordCount: Int,
    isGuessWrong : Boolean
    ){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {

        Text(
            text = "${wordCount}/10" ,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(colorScheme.surfaceTint)
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .align(alignment = Alignment.End),
            color = colorScheme.onPrimary,
            style = typography.titleMedium
        )

        Text(
            text = currentScrambledWord,
            style = typography.displayMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp))

        Text("Unscramble the word using all the letters.", modifier = Modifier
            .padding(8.dp)
            .align(Alignment.CenterHorizontally))

        OutlinedTextField(value = userGuessWord ,
            onValueChange = onUserGuessChanged,
            isError = isGuessWrong,
            placeholder = {
                if(!isGuessWrong){
                    Text(stringResource(R.string.enter_your_word_here))
                }else{
                    Text(stringResource(R.string.wrong_guess))
                }
                          },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .padding(8.dp))
    }
}


@Composable
private fun FinalScoreDialog(score: Int, onPlayAgain : () -> Unit){
    val activity = (LocalContext.current as Activity)
    AlertDialog(
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(onClick = onPlayAgain)
            { Text(stringResource(R.string.play_again)) } },

        dismissButton = {
            TextButton(onClick = {
                activity.finish()
            }) {
                Text("Exit")
            }
        },
        title = { Text(stringResource(R.string.congratulations)) },
        text = { Text("You Scored: $score") }
    )
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameScreenPreview(){
    GameScreen()
}