package com.example.myapp006fitcheck

data class Outfit(
    val top: String,
    val bottom: String,
    val shoes: String
)

object OutfitData {
    val favorites = mutableListOf<Outfit>()
}
