package com.example.imd_biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookListActivity : AppCompatActivity() {

    private lateinit var recyclerViewBooks: RecyclerView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)
        dbHelper = DatabaseHelper(this)


        recyclerViewBooks.layoutManager = LinearLayoutManager(this)


        loadBooks()
    }

    private fun loadBooks() {
        val books = dbHelper.getAllBooks()

        if (books.isNotEmpty()) {
            val bookAdapter = BookAdapter(books) { book ->

                val intent = Intent(this, BookDetailsActivity::class.java).apply {
                    putExtra("BOOK_ID", book.id)
                }
                startActivity(intent)
            }
        }
    }
}
