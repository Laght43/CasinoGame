package com.vadim.casinogame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random
import com.google.gson.Gson
import java.io.File

class CasinoViewModel: ViewModel() {
    var balance by mutableIntStateOf(100)
        private set
    var bet = 0
    var chance by mutableDoubleStateOf(50.0)
    var double = 0
    val precise = 10000
        private


    fun save() {

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