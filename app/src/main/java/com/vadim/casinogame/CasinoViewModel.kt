package com.vadim.casinogame

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random
import java.io.File

class CasinoViewModel: ViewModel() {
    var balance by mutableIntStateOf(100)
    var bet = 0
    var chance by mutableDoubleStateOf(50.0)
    var double = 0
    val precise = 10000

    fun save(context: Context) {
        val file = File(context.filesDir, "game_data.txt")
        val content = "$balance;$chance"
        file.writeText(content)
    }

    fun load(context: Context) {
        val file = File(context.filesDir, "game_data.txt")
        if (file.exists()) {
            val parts = file.readText().split(";")
            balance = parts[0].toInt()
            chance = parts[1].toDouble()
        }
    }

    fun checkWin() {
        val rand = Random.nextInt(1, precise+1)
        val winThreshold = precise - (chance * 100)
        if (rand >= winThreshold) {
            val payout = bet / (chance / 100) - bet
            balance += payout.toInt()
            double = 2 * bet
        } else {
            balance -= bet
            double = 2 * bet
        }
    }

    fun restart() {
        balance = 100
        double = 0
        chance = 50.0
    }

    fun allIn() {
        bet = balance
        checkWin()
    }

    fun double() {
        if (double > 0 && double <= balance) {
            bet = double
            checkWin()
        }
    }

    fun halfBalance() {
        bet = balance / 2
        checkWin()
    }

    fun enterBet(newBet: String) {
        if (newBet.isNotEmpty()) {
            if (newBet.toInt() > 0 && newBet.toInt() <= balance) {
                bet = newBet.toInt()
                checkWin()
            }
        }
    }

    fun enterChance(newChance: String) {
        if (newChance.isNotEmpty()) {
            if (newChance.toDouble() > 0.0 && newChance.toDouble() < 100.0) {
                chance = newChance.toDouble()
            }
        }
    }

}