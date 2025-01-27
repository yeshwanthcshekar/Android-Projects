package com.example.unscramble.ui.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    //for internal access
    private val _uiState : MutableStateFlow<GameUiState> = MutableStateFlow(GameUiState())

    //for external access
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentWord : String

    private var usedWords : MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")
        private set

    private fun pickRandomWordAndShuffle(): String{
        currentWord = allWords.random()
        if(usedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        }else{
            usedWords.add(currentWord)
        }
        return shuffleCurrentWord(currentWord)
    }

    private fun shuffleCurrentWord(currentWord : String):String{
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while(String(tempWord) == currentWord){
            tempWord.shuffle()
        }
        return String(tempWord)
    }


    fun updateUserGuessWord(userGuessWord: String){
        userGuess = userGuessWord
    }

    fun verifyUserGuessedWord(){
        if(userGuess.equals(currentWord, ignoreCase = true)){
/*Can check whether code is working as expected using println, in the logcat filter for System.out to see print statements
            println("UserGuess - $userGuess, Current Word- $currentWord - MATCHING")*/

            //check for last round
            if(usedWords.size == MAX_NO_OF_WORDS){
                _uiState.update { it ->
                    it.copy(
                        score = it.score.plus(10),
                        wordCount = it.wordCount.inc(),
                        isGameOver = true
                    )
                }
            }
            //normal round
            else{
                _uiState.update { currentGameState ->
                    currentGameState.copy(currentScrambledWord = pickRandomWordAndShuffle(),
                        score = currentGameState.score.plus(10),
                        wordCount = currentGameState.wordCount.inc(),
                        isGuessWrong = false)
                }
            }

        }else {
            //println("UserGuess - $userGuess, Current Word- $currentWord - NOT MATCHING")
            _uiState.update { currentGameState ->
                currentGameState.copy(isGuessWrong = true)
            }
        }
        updateUserGuessWord("")
    }


    fun skipWord(){
        //pick another random word
        //
        _uiState.update { it ->
            it.copy(
                currentScrambledWord = pickRandomWordAndShuffle()
            )
        }
        //println(usedWords)
        updateUserGuessWord("")
    }

    fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle(),
            score = 0, wordCount = 0, isGameOver = false)
    }

    init {
        resetGame()
    }

}