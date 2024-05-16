package com.example.tictactoe

import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var  resetButton: Button
    private var playerTurn = true // for player 1 (X), for player 2 false (O)
    private var gameState = Array(9){""} // track each cell

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //declare variables
        gridLayout = findViewById(R.id.gridLayout)
        resetButton = findViewById(R.id.resetButton)

        // set the board
        initializeBoard()

        resetButton.setOnClickListener {
            initializeBoard()
            resetButton.visibility = View.INVISIBLE
            Toast.makeText(this, "Player 1's turn (X)", Toast.LENGTH_SHORT).show()
            gameState.fill("")
            playerTurn = true
        }
    }

    private fun initializeBoard(){
        gridLayout.removeAllViews()
        for (i in 0 until 9){
            val button = Button(this).apply {
              layoutParams = GridLayout.LayoutParams().apply {
                  width = 0
                  height = 0
                  rowSpec = GridLayout.spec(i / 3, 1f)
                  columnSpec = GridLayout.spec( i % 3, 1f)
              }
                text = ""
                setOnClickListener { playGame(this, i) }
            }
            gridLayout.addView(button)
        }
    }

    private fun playGame(button: Button, cellIndex: Int){
        if (gameState[cellIndex].isNotEmpty()){
            Toast.makeText(this, "Cell is already occupied", Toast.LENGTH_SHORT).show()
            return
        }

        gameState[cellIndex] = if (playerTurn) "X" else "O"
        button.text = gameState[cellIndex]

        // switch turn
        playerTurn = !playerTurn

        checkForWin() // check moves
        if(playerTurn)
        {
            Toast.makeText(this, "Player 1's turn (X)", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this, "Player 2's turn (O)", Toast.LENGTH_SHORT).show()
        }


    }

    private fun checkForWin(){
        val winningPositions = arrayOf(
            arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8), // rows
            arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8), // columns
            arrayOf(0, 4, 8), arrayOf(2, 4, 6)
        )
        winningPositions.forEach { pos ->
            if (gameState[pos[0]] == gameState[pos[1]] &&
                gameState[pos[1]] == gameState[pos[2]] &&
                gameState[pos[0]] != ""){
                //winner
                showToast("${gameState[pos[0]]} has won")
                gridLayout.isEnabled = false
                resetButton.visibility = View.VISIBLE
                return
            }
        }

        // check for draw
        if (gameState.none {it.isEmpty()}){
            showToast("It is a draw")
            gridLayout.isEnabled = false
            resetButton.visibility = View.VISIBLE
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}