package com.example.imd_biblioteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class gerenciamento_livros : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciamento_livros)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        bookAdapter = BookAdapter(emptyList()) { book ->

            val intent = Intent(this, BookDetailsActivity::class.java)
                .apply {
                    putExtra("BOOK_ID", book.id)
                }
            startActivity(intent)
        }
        recyclerView.adapter = bookAdapter

        loadBooks()

        findViewById<Button>(R.id.btnAddBook).setOnClickListener {
            val intent = Intent(this, cadastro_livros::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnListBooks).setOnClickListener {
            loadBooks()
        }

        findViewById<Button>(R.id.btnUpdateBook).setOnClickListener {
            val intent = Intent(this, EditBookActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnDeleteBook).setOnClickListener {
            val intent = Intent(this, DeleteBookActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadBooks() {
        val books = dbHelper.getAllBooks()
        bookAdapter.updateBooks(books)
    }
}
