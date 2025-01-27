package com.example.unscramble.ui.ui

data class GameUiState(
    val currentScrambledWord: String = "",
    val score: Int = 0,
    val wordCount : Int = 1,
    val isGuessWrong : Boolean = false,
    val isGameOver : Boolean = false
)