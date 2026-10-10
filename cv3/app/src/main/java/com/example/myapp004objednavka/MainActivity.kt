package com.example.myapp004objednavka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp004objednavka.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Položka obsahuje odkaz na přeložený název a cenu v Kč.
    private data class PricedItem(
        val nameResource: Int,
        val price: Int
    )

    // Ceny měníš pouze zde.
    private val shoes = listOf(
        PricedItem(R.string.shoe_on_1, 4290),
        PricedItem(R.string.shoe_on_2, 5990),
        PricedItem(R.string.shoe_on_3, 6590)
    )

    private val waterproofing = PricedItem(
        R.string.extra_waterproofing, 199
    )

    private val shoelaces = PricedItem(
        R.string.extra_shoelaces, 99
    )

    private val socks = PricedItem(
        R.string.extra_socks, 659
    )

    private lateinit var priceFormatter: NumberFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Formát ceny podle jazyka zařízení.
        // Měna zůstává ve všech jazycích CZK.
        val locale = ConfigurationCompat
            .getLocales(resources.configuration)[0]
            ?: Locale.getDefault()

        priceFormatter = NumberFormat.getCurrencyInstance(locale).apply {
            currency = Currency.getInstance("CZK")
            minimumFractionDigits = 0
            maximumFractionDigits = 0
        }

        // Ošetření systémových lišt.
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

        // Zobrazení názvů a cen u všech položek.
        binding.rbOn1.text = itemWithPrice(shoes[0])
        binding.rbOn2.text = itemWithPrice(shoes[1])
        binding.rbOn3.text = itemWithPrice(shoes[2])

        binding.cbFoam.text = itemWithPrice(waterproofing)
        binding.cbBootstrap.text = itemWithPrice(shoelaces)
        binding.cbSocks.text = itemWithPrice(socks)

        // Změna bot aktualizuje obrázek i celkovou cenu.
        binding.rgShoes.setOnCheckedChangeListener { _, _ ->
            updateShoeImage()
            updateTotal()
        }

        // Změna doplňků aktualizuje celkovou cenu.
        binding.cbFoam.setOnCheckedChangeListener { _, _ ->
            updateTotal()
        }

        binding.cbBootstrap.setOnCheckedChangeListener { _, _ ->
            updateTotal()
        }

        binding.cbSocks.setOnCheckedChangeListener { _, _ ->
            updateTotal()
        }

        // Souhrn se vytvoří po stisknutí tlačítka Objednat.
        binding.btnOrder.setOnClickListener {
            val items = selectedItems()

            val orderDetails = items.joinToString(
                separator = getString(R.string.order_line_separator)
            ) { item ->
                itemWithPrice(item)
            }

            val total = items.sumOf { it.price }

            binding.tvOrder.text = getString(
                R.string.order_summary_with_total,
                orderDetails,
                formatPrice(total)
            )
        }

        // Výchozí obrázek a cena po spuštění.
        updateShoeImage()
        updateTotal()
    }

    // Vrátí položku odpovídající vybraným botám.
    private fun selectedShoe(): PricedItem {
        return when (binding.rgShoes.checkedRadioButtonId) {
            binding.rbOn2.id -> shoes[1]
            binding.rbOn3.id -> shoes[2]
            else -> shoes[0]
        }
    }

    // Vrátí vybrané boty a všechny zaškrtnuté doplňky.
    private fun selectedItems(): List<PricedItem> {
        val items = mutableListOf(selectedShoe())

        if (binding.cbFoam.isChecked) {
            items.add(waterproofing)
        }

        if (binding.cbBootstrap.isChecked) {
            items.add(shoelaces)
        }

        if (binding.cbSocks.isChecked) {
            items.add(socks)
        }

        return items
    }

    private fun updateShoeImage() {
        val imageResource = when (binding.rgShoes.checkedRadioButtonId) {
            binding.rbOn2.id -> R.drawable.on_2
            binding.rbOn3.id -> R.drawable.on_3
            else -> R.drawable.on_1
        }

        binding.ivShoe.setImageResource(imageResource)
    }

    private fun updateTotal() {
        val total = selectedItems().sumOf { it.price }

        binding.tvTotal.text = getString(
            R.string.total_price_format,
            formatPrice(total)
        )
    }

    private fun itemWithPrice(item: PricedItem): String {
        return getString(
            R.string.item_with_price,
            getString(item.nameResource),
            formatPrice(item.price)
        )
    }

    private fun formatPrice(amount: Int): String {
        return priceFormatter.format(amount)
    }
}