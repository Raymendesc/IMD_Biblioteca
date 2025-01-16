package com.example.imd_biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditBookActivity : AppCompatActivity() {

    private lateinit var etIsbn: EditText
    private lateinit var etTitle: EditText
    private lateinit var etAuthor: EditText
    private lateinit var etPublisher: EditText
    private lateinit var etDescription: EditText
    private lateinit var etYear: EditText
    private lateinit var btnSearchByIsbn: Button
    private lateinit var btnSaveChanges: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        dbHelper = DatabaseHelper(this)

        etIsbn = findViewById(R.id.etIsbn)
        etTitle = findViewById(R.id.etTitle)
        etAuthor = findViewById(R.id.etAuthor)
        etPublisher = findViewById(R.id.etPublisher)
        etDescription = findViewById(R.id.etDescription)
        etYear = findViewById(R.id.etYear)
        btnSearchByIsbn = findViewById(R.id.btnSearchByIsbn)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)

        disableInputFields()

        btnSearchByIsbn.setOnClickListener {
            val isbn = etIsbn.text.toString().trim()

            if (isbn.isNotEmpty()) {
                val book = dbHelper.getBookByIsbn(isbn)
                if (book != null) {
                    etTitle.setText(book.title)
                    etAuthor.setText(book.author)
                    etPublisher.setText(book.publisher)
                    etDescription.setText(book.description)
                    etYear.setText(book.year.toString())

                    enableInputFields()
                } else {
                    Toast.makeText(this, "Livro não encontrado!", Toast.LENGTH_SHORT).show()
                    disableInputFields()
                }
            } else {
                Toast.makeText(this, "Por favor, insira o ISBN.", Toast.LENGTH_SHORT).show()
            }
        }

        btnSaveChanges.setOnClickListener {
            val isbn = etIsbn.text.toString().trim()
            val title = etTitle.text.toString().trim()
            val author = etAuthor.text.toString().trim()
            val publisher = etPublisher.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val year = etYear.text.toString().trim().toIntOrNull()

            if (isbn.isNotEmpty() && title.isNotEmpty() && author.isNotEmpty() && publisher.isNotEmpty() && description.isNotEmpty() && year != null) {
                val existingBook = dbHelper.getBookByIsbn(isbn)
                if (existingBook != null) {
                    val updatedBook = existingBook.copy(
                        title = title,
                        author = author,
                        publisher = publisher,
                        description = description,
                        year = year
                    )

                    if (dbHelper.updateBook(updatedBook)) {
                        Toast.makeText(this, "Livro atualizado com sucesso!", Toast.LENGTH_SHORT)
                            .show()
                        finish() // Fecha a atividade atual
                    } else {
                        Toast.makeText(this, "Erro ao atualizar livro.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Erro ao localizar o livro para atualização.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Todos os campos são obrigatórios.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun disableInputFields() {
        etTitle.isEnabled = false
        etAuthor.isEnabled = false
        etPublisher.isEnabled = false
        etDescription.isEnabled = false
        etYear.isEnabled = false
    }

    private fun enableInputFields() {
        etTitle.isEnabled = true
        etAuthor.isEnabled = true
        etPublisher.isEnabled = true
        etDescription.isEnabled = true
        etYear.isEnabled = true
    }
}
