package com.example.myapp001a

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Zachování původního odsazení z XML.
        val llMain = findViewById<android.widget.LinearLayout>(R.id.llMain)
        val initialLeft = llMain.paddingLeft
        val initialTop = llMain.paddingTop
        val initialRight = llMain.paddingRight
        val initialBottom = llMain.paddingBottom

        // Přidání odsazení od systémových lišt.
        ViewCompat.setOnApplyWindowInsetsListener(llMain) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                initialLeft + systemBars.left,
                initialTop + systemBars.top,
                initialRight + systemBars.right,
                initialBottom + systemBars.bottom
            )

            insets
        }

        // Symboly odpovídající hodnotám 1 až 6.
        val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")

        // Získání prvků z XML pomocí jejich ID.
        val tvDice = findViewById<TextView>(R.id.tvDice)
        val tvDice2 = findViewById<TextView>(R.id.tvDice2)
        val tvSum = findViewById<TextView>(R.id.tvSum)
        val tvRollCount = findViewById<TextView>(R.id.tvRollCount)
        val btnRoll = findViewById<Button>(R.id.btnRoll)

        var rollCount = 0

        btnRoll.setOnClickListener {
            // Během animace nelze spustit další hod.
            btnRoll.isEnabled = false
            tvSum.text = "Házím…"

            lifecycleScope.launch {
                // Deset náhodných změn obou kostek po 250 ms.
                repeat(10) {
                    tvDice.text = diceSymbols.random()
                    tvDice2.text = diceSymbols.random()
                    delay(250)
                }

                // Nezávislé výsledné hodnoty obou kostek.
                val firstDiceValue = (1..6).random()
                val secondDiceValue = (1..6).random()

                tvDice.text = diceSymbols[firstDiceValue - 1]
                tvDice2.text = diceSymbols[secondDiceValue - 1]

                // Součet kostek a počet dokončených hodů.
                val diceSum = firstDiceValue + secondDiceValue
                tvSum.text = "Součet: $diceSum"

                rollCount++
                tvRollCount.text = "Počet hodů: $rollCount"

                btnRoll.isEnabled = true
            }
        }
    }
}