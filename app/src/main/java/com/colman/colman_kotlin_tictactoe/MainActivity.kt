package com.colman.colman_kotlin_tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

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
                boardCells[row][column].setTextColor(ContextCompat.getColor(this, android.R.color.black))
                boardCells[row][column].setOnClickListener{ this.onCellClicked(row, column) }
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
            if (winner == 'D') {
                playerTurnText.text = "Draw"
            } else {
                playerTurnText.text = "Player $winner has won!"
            }

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
        for (i in 0..2) {
            // Row Victory
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                winner = board[i][0]
                for (col in 0..2) {
                    boardCells[i][col].setTextColor(ContextCompat.getColor(this,
                        android.R.color.holo_green_light))
                }

                return
            }
            // Column Victory
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ') {
                for (row in 0..2) {
                    boardCells[row][i].setTextColor(ContextCompat.getColor(this,
                        android.R.color.holo_green_light))
                }

                winner = board[0][i]
                return
            }
        }

        // Cross Victory
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
            winner = board[0][0]
            for (i in 0..2) {
                boardCells[i][i].setTextColor(ContextCompat.getColor(this,
                    android.R.color.holo_green_light))
            }

            return
        }

        // Cross Victory
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
            winner = board[0][2]
            for (i in 0..2) {
                boardCells[i][2 - i].setTextColor(ContextCompat.getColor(this,
                    android.R.color.holo_green_light))
            }

            return
        }

        // Tie Game
        if (isBoardFull()) {
            winner = 'D'
            playerTurnText.text = "Draw"
        }
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it != ' ' } }
    }
}