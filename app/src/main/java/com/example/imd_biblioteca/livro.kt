package com.example.imd_biblioteca
import java.io.Serializable

data class livro(
    val id: Long,
    val title: String,
    val author: String,
    val publisher: String,
    val isbn: String,
    val description: String,
    val imageUrl: String,
    val year: Int
) : Serializable