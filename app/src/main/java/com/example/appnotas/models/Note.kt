package com.example.appnotas.models

import android.graphics.Color

class Note(
    var content: String,
    var color: Int = Color.parseColor("#FF5E5E") // Almacenará el color como un valor entero (color en Android se maneja con enteros)
)