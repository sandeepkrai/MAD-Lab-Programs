package com.example.b1question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable

val books = listOf<String>(
    "ABC",
    "DEF"
    ,"GHI"
)


val complexBook = listOf<Book>(
    Book("Hello", "Sandy"),
    Book("Howdy", "Sandeep")
)

var selectedComplexBook = mutableListOf<Book>()

data class Book(val name: String, val author: String)



var selectedBook = mutableListOf<String>()