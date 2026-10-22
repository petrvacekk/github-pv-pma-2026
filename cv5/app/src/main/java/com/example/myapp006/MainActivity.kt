
package com.example.myapp006

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp006.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Po spuštění zobrazíme Fragment A
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FragmentA())
                .commit()
        }

        // Přepnutí na Fragment A
        binding.btnFragmentA.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FragmentA())
                .commit()
        }

        // Přepnutí na Fragment B
        binding.btnFragmentB.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FragmentB())
                .commit()
        }
    }
}
