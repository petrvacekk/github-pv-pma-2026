
package com.example.myapp006fitcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class OutfitFragment : Fragment() {

    private val tops = listOf(
        "Black Tee",
        "Grey Hoodie",
        "Beige Jacket"
    )

    private val bottoms = listOf(
        "Black Denim"
    )

    private val shoes = listOf(
        "White Sneakers"
    )

    private var topIndex = 0
    private var bottomIndex = 0
    private var shoesIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_outfit,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val tvTop = view.findViewById<TextView>(R.id.tvTop)
        val tvBottom = view.findViewById<TextView>(R.id.tvBottom)
        val tvShoes = view.findViewById<TextView>(R.id.tvShoes)

        fun updateOutfit() {
            tvTop.text = tops[topIndex]
            tvBottom.text = bottoms[bottomIndex]
            tvShoes.text = shoes[shoesIndex]
        }

        view.findViewById<Button>(R.id.btnTop).setOnClickListener {
            topIndex = (topIndex + 1) % tops.size
            updateOutfit()
        }

        view.findViewById<Button>(R.id.btnBottom).setOnClickListener {
            bottomIndex = (bottomIndex + 1) % bottoms.size
            updateOutfit()
        }

        view.findViewById<Button>(R.id.btnShoes).setOnClickListener {
            shoesIndex = (shoesIndex + 1) % shoes.size
            updateOutfit()
        }

        view.findViewById<Button>(R.id.btnSaveOutfit).setOnClickListener {
            val outfit = Outfit(
                tops[topIndex],
                bottoms[bottomIndex],
                shoes[shoesIndex]
            )

            OutfitData.favorites.add(outfit)

            Toast.makeText(
                requireContext(),
                "Outfit uložen mezi oblíbené!",
                Toast.LENGTH_SHORT
            ).show()
        }

        updateOutfit()
    }
}
