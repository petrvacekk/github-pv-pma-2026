package com.example.myapp005objednavkav2

import android.content.Intent
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

        // Zavoláme hned po startu, aby se zobrazila výchozí cena
        updatePrice()

        // Nastavení listeneru na změny pro RadioGroup (výběr bot)
        binding.rgShoes.setOnCheckedChangeListener { _, _ ->
            updatePrice()
        }

        // Nastavení listenerů na změny pro CheckBoxy (doplňky)
        binding.cbBootstraps.setOnClickListener { updatePrice() }
        binding.cbSocks.setOnClickListener { updatePrice() }
        binding.cbFoam.setOnClickListener { updatePrice() }

        // Tlačítko OBJEDNAT
        binding.btnOrder.setOnClickListener {

            // Zjišťujeme název vybraných bot
            val shoeName = when (binding.rgShoes.checkedRadioButtonId) {
                binding.rOn1.id -> binding.rOn1.text.toString()
                binding.rOn2.id -> binding.rOn2.text.toString()
                binding.rOn3.id -> binding.rOn3.text.toString()
                else -> "Nevybrané boty"
            }

            // Získání výsledné ceny
            val currentPriceText = binding.tvPrice.text.toString()

            // Vytvoření Intentu směřujícího na SummaryActivity
            val intent = Intent(this, SummaryActivity::class.java)

            // Předání informací o vybraných botách a ceně
            intent.putExtra("EXTRA_SHOE_NAME", shoeName)
            intent.putExtra("EXTRA_TOTAL_PRICE", currentPriceText)

            // Předání informací o doplňcích
            intent.putExtra("EXTRA_HAS_BOOTSTRAPS", binding.cbBootstraps.isChecked)
            intent.putExtra("EXTRA_HAS_SOCKS", binding.cbSocks.isChecked)
            intent.putExtra("EXTRA_HAS_FOAM", binding.cbFoam.isChecked)

            // Spuštění druhé aktivity
            startActivity(intent)
        }
    }

    // Funkce pro výpočet celkové ceny
    private fun updatePrice() {
        var totalPrice = 0

        // 1. Zjištění základní ceny vybraných bot
        totalPrice += when (binding.rgShoes.checkedRadioButtonId) {
            binding.rOn1.id -> 4500
            binding.rOn2.id -> 5500
            binding.rOn3.id -> 6500
            else -> 0
        }

        // 2. Přičtení příplatkové výbavy
        if (binding.cbBootstraps.isChecked) totalPrice += 200
        if (binding.cbSocks.isChecked) totalPrice += 300
        if (binding.cbFoam.isChecked) totalPrice += 500

        // 3. Zobrazení výsledku v barevném TextView
        binding.tvPrice.text = "Celkem: $totalPrice Kč"
    }
}