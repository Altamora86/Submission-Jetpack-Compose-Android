package com.latihan.batiqu.data.local

data class Batik(
    val id: Int,
    val name: String,
    val origin: String,
    val image: Int,
    val description: String,
    val since: String,
    val price: String,
    val status: String,
    var isFavorite: Boolean = false
)

