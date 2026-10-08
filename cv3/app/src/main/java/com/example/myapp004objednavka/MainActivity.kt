package com.example.myapp004objednavka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp004objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Deklarace binding objektu
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Načtení rozhraní pomocí View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ošetření systémových lišt
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Přepínání obrázků podle vybraných bot
        binding.rbOn1.setOnClickListener {
            binding.ivShoe.setImageResource(R.drawable.on_1)
        }

        binding.rbOn2.setOnClickListener {
            binding.ivShoe.setImageResource(R.drawable.on_2)
        }

        binding.rbOn3.setOnClickListener {
            binding.ivShoe.setImageResource(R.drawable.on_3)
        }

        // Výpis souhrnu objednávky
        binding.btnOrder.setOnClickListener {
            val shoe = when (binding.rgShoes.checkedRadioButtonId) {
                binding.rbOn1.id -> binding.rbOn1
                binding.rbOn2.id -> binding.rbOn2
                binding.rbOn3.id -> binding.rbOn3
                else -> binding.rbOn1
            }

            // Seznam začíná názvem vybraných bot
            val orderItems = mutableListOf(shoe.text.toString())

            // Přidání vybraných doplňků
            if (binding.cbFoam.isChecked) {
                orderItems.add(binding.cbFoam.text.toString())
            }

            if (binding.cbBootstrap.isChecked) {
                orderItems.add(binding.cbBootstrap.text.toString())
            }

            if (binding.cbSocks.isChecked) {
                orderItems.add(binding.cbSocks.text.toString())
            }

            // Spojení položek pomocí oddělovače ze strings.xml
            val selectedItems = orderItems.joinToString(
                separator = getString(R.string.order_item_separator)
            )

            // Lokalizovaný souhrn objednávky
            binding.tvOrder.text = getString(
                R.string.order_summary_format,
                selectedItems
            )
        }
    }
}