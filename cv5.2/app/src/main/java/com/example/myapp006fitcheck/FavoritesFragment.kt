
package com.example.myapp006fitcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_favorites,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<LinearLayout>(
            R.id.favoritesContainer
        )

        val emptyText = view.findViewById<TextView>(
            R.id.tvEmpty
        )

        if (OutfitData.favorites.isNotEmpty()) {
            emptyText.visibility = View.GONE
        }

        OutfitData.favorites.forEachIndexed { index, outfit ->

            val outfitText = TextView(requireContext()).apply {
                text = """
                    OUTFIT ${index + 1}

                    ${outfit.top}
                    ${outfit.bottom}
                    ${outfit.shoes}
                """.trimIndent()

                textSize = 17f
                setTextColor(android.graphics.Color.BLACK)
                setBackgroundColor(
                    android.graphics.Color.rgb(245, 245, 245)
                )
                setPadding(24, 24, 24, 24)
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = (12 * resources.displayMetrics.density).toInt()
            }

            container.addView(outfitText, params)
        }
    }
}
