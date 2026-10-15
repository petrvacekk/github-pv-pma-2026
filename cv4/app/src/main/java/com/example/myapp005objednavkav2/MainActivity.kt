package com.example.myapp005objednavkav2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp005objednavkav2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ošetření systémových lišt
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // Aktualizace ceny při výběru bot
        binding.rgShoes.setOnCheckedChangeListener { _, _ ->
            updatePrice()
        }

        // Aktualizace ceny při výběru doplňků
        binding.cbBootstraps.setOnCheckedChangeListener { _, _ ->
            updatePrice()
        }

        binding.cbSocks.setOnCheckedChangeListener { _, _ ->
            updatePrice()
        }

        binding.cbFoam.setOnCheckedChangeListener { _, _ ->
            updatePrice()
        }

        // Výpočet počáteční ceny
        updatePrice()
    }

    private fun updatePrice() {
        var totalPrice = 0

        // 1. Základní cena vybraných bot
        totalPrice += when (binding.rgShoes.checkedRadioButtonId) {
            binding.rOn1.id -> 4500
            binding.rOn2.id -> 5500
            binding.rOn3.id -> 6500
            else -> 0
        }

        // 2. Cena doplňků
        if (binding.cbBootstraps.isChecked) totalPrice += 200
        if (binding.cbSocks.isChecked) totalPrice += 300
        if (binding.cbFoam.isChecked) totalPrice += 500

        // 3. Zobrazení celkové ceny
        binding.tvPrice.text = "Celkem: $totalPrice Kč"
    }
}
