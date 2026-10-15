package com.example.myapp005objednavkav2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val mainView = findViewById<View>(R.id.main)
        val tvSelectedShoes = findViewById<TextView>(R.id.tvSelectedShoes)
        val tvExtras = findViewById<TextView>(R.id.tvExtras)
        val tvFinalPrice = findViewById<TextView>(R.id.tvFinalPrice)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Ošetření systémových lišt
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

                v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
                )
                insets
            }
        }

        // 1. Získání informací z MainActivity
        val shoeName = intent.getStringExtra("EXTRA_SHOE_NAME")
            ?: "Nevybrané boty"

        val totalPrice = intent.getStringExtra("EXTRA_TOTAL_PRICE")
            ?: "Celkem: 0 Kč"

        // 2. Získání informací o doplňcích
        val hasBootstraps = intent.getBooleanExtra("EXTRA_HAS_BOOTSTRAPS", false)
        val hasSocks = intent.getBooleanExtra("EXTRA_HAS_SOCKS", false)
        val hasFoam = intent.getBooleanExtra("EXTRA_HAS_FOAM", false)

        // 3. Sestavení seznamu doplňků
        val extras = mutableListOf<String>()

        if (hasBootstraps) extras.add("Tkaničky")
        if (hasSocks) extras.add("Ponožky")
        if (hasFoam) extras.add("Tlumení")

        val extrasText = if (extras.isEmpty()) {
            "Žádná"
        } else {
            extras.joinToString(", ")
        }

        // 4. Zobrazení rekapitulace objednávky
        tvSelectedShoes.text = "Vybrané boty: $shoeName"
        tvExtras.text = "Příplatková výbava: $extrasText"
        tvFinalPrice.text = totalPrice

        // 5. Tlačítko Zpět k úpravě
        btnBack.setOnClickListener {
            finish()
        }
    }
}