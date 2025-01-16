package com.example.imd_biblioteca

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvPublisher: TextView
    private lateinit var tvDescription: TextView
    private lateinit var imgBookCover: ImageView
    private lateinit var dbHelper: DatabaseHelper


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)


        tvTitle = findViewById(R.id.tvTitle)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvPublisher = findViewById(R.id.tvPublisher)
        tvDescription = findViewById(R.id.tvDescription)
        imgBookCover = findViewById(R.id.imgBookCover)


        dbHelper = DatabaseHelper(this)


        val bookId = intent.getLongExtra("BOOK_ID", -1)

        if (bookId != -1L) {

            val book = dbHelper.getBookById(bookId)  // Aqui chama o método de getBookById

            if (book != null) {

                tvTitle.text = book.title
                tvAuthor.text = book.author
                tvPublisher.text = book.publisher
                tvDescription.text = book.description


                Glide.with(this)
                    .load(book.imageUrl)
                    .into(imgBookCover)
            } else {

                Toast.makeText(this, "Livro não encontrado!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
