package com.example.myapp001bdicethrowcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp001bdicethrowcompose.ui.theme.MyApp001bDiceThrowCOMPOSETheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MyApp001bDiceThrowCOMPOSETheme {
                DiceApp()
            }
        }
    }
}

@Composable
fun DiceApp() {

    // Unicode symboly jednotlivých stran kostky.
    val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")

    // Aktuální hodnoty obou kostek.
    var firstDiceValue by remember { mutableIntStateOf(1) }
    var secondDiceValue by remember { mutableIntStateOf(1) }

    // Určuje, zda právě probíhá animace hodu.
    var isRolling by remember { mutableStateOf(false) }

    // Počet dokončených hodů.
    var rollCount by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    // Barvy aplikace.
    val backgroundColor = Color(0xFFF3EBDD)
    val primaryColor = Color(0xFF111111)
    val secondaryColor = Color(0xFF4A4A4A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .safeDrawingPadding()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Nadpis aplikace.
        Text(
            text = "Hoď kostkou",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )

        // Dvě kostky vedle sebe.
        Row(
            modifier = Modifier.padding(
                top = 24.dp,
                bottom = 16.dp
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = diceSymbols[firstDiceValue - 1],
                fontSize = 100.sp,
                color = primaryColor
            )

            Text(
                text = diceSymbols[secondDiceValue - 1],
                fontSize = 100.sp,
                color = primaryColor,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Součet hodnot obou kostek.
        Text(
            text = "Součet: ${firstDiceValue + secondDiceValue}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Tlačítko je během animace zakázané.
        Button(
            enabled = !isRolling,

            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = backgroundColor,
                disabledContainerColor = Color(0xFF888888),
                disabledContentColor = backgroundColor
            ),

            onClick = {
                isRolling = true

                scope.launch {

                    // Deset rychlých změn hodnot kostek.
                    repeat(10) {
                        firstDiceValue = (1..6).random()
                        secondDiceValue = (1..6).random()

                        delay(250)
                    }

                    // Výsledný hod.
                    firstDiceValue = (1..6).random()
                    secondDiceValue = (1..6).random()

                    // Zvýšení počtu dokončených hodů.
                    rollCount++

                    isRolling = false
                }
            }
        ) {
            Text(
                text = "Hodit",
                fontSize = 24.sp
            )
        }

        // Počet dokončených hodů.
        Text(
            text = "Počet hodů: $rollCount",
            fontSize = 18.sp,
            color = secondaryColor,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}