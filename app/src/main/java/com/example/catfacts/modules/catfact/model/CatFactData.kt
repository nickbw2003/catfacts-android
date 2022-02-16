package com.example.catfacts.modules.catfact.model

data class CatFactData(
    val imageData: ByteArray,
    val text: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatFactData

        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }
}
