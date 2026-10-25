
package com.example.myapp006fitcheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapp006fitcheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Výchozí obrazovka - Šatník
        if (savedInstanceState == null) {
            showFragment(WardrobeFragment())
        }

        // Navigace - Šatník
        binding.btnWardrobe.setOnClickListener {
            showFragment(WardrobeFragment())
        }

        // Navigace - Outfity
        binding.btnOutfits.setOnClickListener {
            showFragment(OutfitFragment())
        }

        // Navigace - Oblíbené
        binding.btnFavorites.setOnClickListener {
            showFragment(FavoritesFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
