package com.colman.colman_kotlin_tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var playerTurnText: TextView
    private lateinit var boardCells: Array<Array<Button>>
    private var board = Array(3) { CharArray(3) {' '} }
    private var currentPlayer = 'X'
    private var winner: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerTurnText = findViewById(R.id.playerTurn)

        // UI Board GridLayout reference
        boardCells = arrayOf(
            arrayOf(
                findViewById(R.id.cell00),
                findViewById(R.id.cell01),
                findViewById(R.id.cell02)
            ),
            arrayOf(
                findViewById(R.id.cell10),
                findViewById(R.id.cell11),
                findViewById(R.id.cell12)
            ),
            arrayOf(
                findViewById(R.id.cell20),
                findViewById(R.id.cell21),
                findViewById(R.id.cell22)
            )
        )

        val resetButton: Button = findViewById(R.id.resetGame)
        resetButton.setOnClickListener { resetBoard() }

        initGameBoard()
    }

    private fun initGameBoard() {
        board = Array(3) { CharArray(3) {' '} }
        currentPlayer = 'X'
        playerTurnText.text = "Player X turn"
        winner = null

        for (row in 0..2) {
            for (column in 0..2) {
                boardCells[row][column].text = ""
                boardCells[row][column].isEnabled = true
                boardCells[row][column].setOnClickListener{ onCellClicked(row, column) }
            }
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer
            boardCells[row][col].text = currentPlayer.toString()
            boardCells[row][col].isEnabled = false
        }

        checkWinner()

        if (winner == null) {
            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            playerTurnText.text = "Player $currentPlayer turn"

        } else {
            playerTurnText.text = "Player $winner has won!"

            for (row in 0..2) {
                for (col in 0..2) {
                    boardCells[row][col].isEnabled = false
                }
            }
        }
    }

    private fun resetBoard() {
        initGameBoard()
    }

    private fun checkWinner() {
        // Implement winner logic
    }
}