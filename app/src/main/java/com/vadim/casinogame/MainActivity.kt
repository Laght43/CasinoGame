package com.vadim.casinogame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.vadim.casinogame.ui.theme.CasinoGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CasinoGameTheme {
                MainScreen()
            }
        }
    }
}

val game = CasinoViewModel()

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen() {
    val activity = LocalActivity.current as ComponentActivity
    var text by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()
    ) {
        Button (
            onClick = {activity.finish()},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .navigationBarsPadding()
                .padding(8.dp)
        ) {
            Text ("Exit")
        }
        Button (
            onClick = { game.restart() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .navigationBarsPadding()
                .padding(8.dp)
        ) {
            Text ("Restart")
        }
        Text(
            text = "Balance: ${game.balance}\n\nChance: ${game.chance.toInt()}",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )

        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column (
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        newText: String ->
                        if (newText.all { it.isDigit()}) {
                            text = newText
                        }
                    },
                    label = { Text("Enter your bet or chance") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button (
                        onClick = { game.allIn() },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) { Text("All in") }
                    Button(
                        onClick = { game.double() },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) { Text("Double last bet") }
                    Button(
                        onClick = { game.halfBalance() },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) { Text("Half Balance") }
                    Button(
                        onClick = { game.enterBet(text)},
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) { Text("Enter your bet") }
                    Button(
                        onClick = {game.enterChance(text)},
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) { Text("Enter chance for win") }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    CasinoGameTheme {
        MainScreen()
    }
}