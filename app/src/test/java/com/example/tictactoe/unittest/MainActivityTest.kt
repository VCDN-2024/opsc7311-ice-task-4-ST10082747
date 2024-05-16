package com.example.tictactoe.unittest

import org.junit.Assert.*
import org.junit.Test
class MainActivityTest {
    @Test
    fun test_PlayerXWins() {
        val gameState = arrayOf("X", "X", "X", "", "", "", "", "", "")
        val winningPositions = arrayOf(
            arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8), // rows
            arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8), // columns
            arrayOf(0, 4, 8), arrayOf(2, 4, 6)
        )
        winningPositions.forEach { pos ->
            if (gameState[pos[0]] == gameState[pos[1]] && gameState[pos[1]] == gameState[pos[2]] && gameState[pos[0]] != "") {
                assertEquals("X", gameState[pos[0]]) // Assert that the winner is X
            }
        }
    }
}