package com.example.imd_biblioteca

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DeleteBookActivity : AppCompatActivity() {

    private lateinit var etIsbnToDelete: EditText
    private lateinit var btnDeleteBook: Button
    private lateinit var tvDeleteResult: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_book)


        etIsbnToDelete = findViewById(R.id.etIsbnToDelete)
        btnDeleteBook = findViewById(R.id.btnDeleteBook)
        tvDeleteResult = findViewById(R.id.tvDeleteResult)


        dbHelper = DatabaseHelper(this)


        btnDeleteBook.setOnClickListener {
            val isbn = etIsbnToDelete.text.toString().trim()

            if (isbn.isNotEmpty()) {

                val isDeleted = dbHelper.deleteBook(isbn)

                if (isDeleted) {

                    tvDeleteResult.text = "Livro excluído com sucesso!"
                } else {

                    tvDeleteResult.text = "Livro não encontrado ou erro ao excluir."
                }
            } else {

                Toast.makeText(this, "Por favor, informe o ISBN.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
