package com.example.imd_biblioteca

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "livraria.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createBooksTable = """
            CREATE TABLE books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                publisher TEXT NOT NULL,
                isbn TEXT UNIQUE NOT NULL,
                description TEXT,
                imageUrl TEXT,
                year INTEGER
            )
        """.trimIndent()

        val createUsersTable = """
            CREATE TABLE users (
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createBooksTable)
        db.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS books")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }


    fun insertBook(book: livro): Boolean {
        val values = ContentValues().apply {
            put("title", book.title)
            put("author", book.author)
            put("publisher", book.publisher)
            put("isbn", book.isbn)
            put("description", book.description)
            put("imageUrl", book.imageUrl)
            put("year", book.year)
        }
        return writableDatabase.insert("books", null, values) > 0
    }


    @SuppressLint("Range")
    fun getAllBooks(): List<livro> {
        val books = mutableListOf<livro>()
        val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM books", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val author = cursor.getString(cursor.getColumnIndex("author"))
                val publisher = cursor.getString(cursor.getColumnIndex("publisher"))
                val isbn = cursor.getString(cursor.getColumnIndex("isbn"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"))
                val year = cursor.getInt(cursor.getColumnIndex("year"))
                books.add(livro(id, title, author, publisher, isbn, description, imageUrl, year))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return books
    }


    @SuppressLint("Range")
    fun getBookByIsbn(isbn: String): livro? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM books WHERE isbn = ?", arrayOf(isbn))

        if (cursor.moveToFirst()) {
            val book = livro(
                id = cursor.getLong(cursor.getColumnIndex("id")),
                title = cursor.getString(cursor.getColumnIndex("title")),
                author = cursor.getString(cursor.getColumnIndex("author")),
                publisher = cursor.getString(cursor.getColumnIndex("publisher")),
                isbn = cursor.getString(cursor.getColumnIndex("isbn")),
                description = cursor.getString(cursor.getColumnIndex("description")),
                imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl")),
                year = cursor.getInt(cursor.getColumnIndex("year"))
            )
            cursor.close()
            return book
        }

        cursor.close()
        return null
    }


    @SuppressLint("Range")
    fun getBookById(id: Long): livro? {
        val db = this.readableDatabase
        val cursor = db.query(
            "books",
            null,
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val book = livro(
                id = cursor.getLong(cursor.getColumnIndex("id")),
                title = cursor.getString(cursor.getColumnIndex("title")),
                author = cursor.getString(cursor.getColumnIndex("author")),
                publisher = cursor.getString(cursor.getColumnIndex("publisher")),
                isbn = cursor.getString(cursor.getColumnIndex("isbn")),
                description = cursor.getString(cursor.getColumnIndex("description")),
                imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl")),
                year = cursor.getInt(cursor.getColumnIndex("year"))
            )
            cursor.close()
            return book
        }

        cursor.close()
        return null
    }


    fun updateBook(book: livro): Boolean {
        val values = ContentValues().apply {
            put("title", book.title)
            put("author", book.author)
            put("publisher", book.publisher)
            put("isbn", book.isbn)
            put("description", book.description)
            put("imageUrl", book.imageUrl)
            put("year", book.year)
        }
        return writableDatabase.update("books", values, "id = ?", arrayOf(book.id.toString())) > 0
    }


    fun deleteBook(isbn: String): Boolean {
        return writableDatabase.delete("books", "isbn = ?", arrayOf(isbn)) > 0
    }


    fun registrarUsuario(username: String, password: String): Boolean {
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        return writableDatabase.insert("users", null, values) > 0
    }


    fun validarCredenciais(username: String, password: String): Boolean {
        // Senha específica para acesso direto
        if (username == "admin" && password == "admin123") {
            return true
        }

        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", arrayOf(username, password))
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }
}
