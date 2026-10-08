package com.example.myapp004objednavka

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp004objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //1. Binding - deklarace binding objektu s odloženou inicializací
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()

        //Binding - nafouknutí (inflate) layoutu do binding instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Nastavení kořenového pohledu (root) do okna aktivity
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

        //Změna obrázku v závislosti na vybraném radiobuttonu
        binding.rbOn1.setOnClickListener {
            binding.ivShoe.setImageResource(R.drawable.on_1)
        }

        binding.rbOn2.setOnClickListener {
            binding.ivShoe.setImageResource(R.drawable.on_2)
        }

        binding.rbOn3.setOnClickListener {
            binding.ivShoe.setImageResource(R.drawable.on_3)
        }

        binding.btnOrder.setOnClickListener {
            val shoe = when (binding.rgShoes.checkedRadioButtonId) {
                binding.rbOn1.id -> binding.rbOn1
                binding.rbOn2.id -> binding.rbOn2
                binding.rbOn3.id -> binding.rbOn3

                //Záložní možnost, kdyby nebylo vybráno nic
                else -> binding.rbOn1
            }

            val foam = binding.cbFoam.isChecked
            val bootstrap = binding.cbBootstrap.isChecked
            val socks = binding.cbSocks.isChecked

            val orderText = "Souhrn objednávky: " + "${shoe.text}" +
                    (if (foam) ";lepší tlumení" else "") +
                    (if (bootstrap) ";tkaničky navíc" else "") +
                    (if (socks) ";ponožky On" else "")

            binding.tvOrder.text = orderText
        }
    }
}