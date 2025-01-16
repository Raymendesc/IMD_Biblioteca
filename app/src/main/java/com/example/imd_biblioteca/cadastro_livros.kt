package com.example.imd_biblioteca

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class cadastro_livros : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etAuthor: EditText
    private lateinit var etPublisher: EditText
    private lateinit var etIsbn: EditText
    private lateinit var etDescription: EditText
    private lateinit var etImageUrl: EditText
    private lateinit var etYear: EditText // Novo campo para o ano
    private lateinit var btnSaveBook: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_livros)


        etTitle = findViewById(R.id.etTitle)
        etAuthor = findViewById(R.id.etAuthor)
        etPublisher = findViewById(R.id.etPublisher)
        etIsbn = findViewById(R.id.etIsbn)
        etDescription = findViewById(R.id.etDescription)
        etImageUrl = findViewById(R.id.etImageUrl)
        etYear = findViewById(R.id.etYear) // Referência para o campo de ano
        btnSaveBook = findViewById(R.id.btnSaveBook)


        dbHelper = DatabaseHelper(this)


        btnSaveBook.setOnClickListener {
            val title = etTitle.text.toString()
            val author = etAuthor.text.toString()
            val publisher = etPublisher.text.toString()
            val isbn = etIsbn.text.toString()
            val description = etDescription.text.toString()
            val imageUrl = etImageUrl.text.toString()


            val year = etYear.text.toString().toIntOrNull() ?: 0 // Valor default de 0 se o ano não for informado

            if (title.isNotEmpty() && author.isNotEmpty() && publisher.isNotEmpty() && isbn.isNotEmpty()) {

                val book = livro(0, title, author, publisher, isbn, description, imageUrl, year)


                if (dbHelper.insertBook(book)) {
                    Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao cadastrar o livro.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
