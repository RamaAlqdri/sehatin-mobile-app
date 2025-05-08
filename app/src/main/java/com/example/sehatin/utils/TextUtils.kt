package com.example.sehatin.utils

fun String.capitalizeWords(): String {
    return this.split(" ")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}
