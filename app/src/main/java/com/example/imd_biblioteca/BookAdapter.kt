package com.example.imd_biblioteca

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookAdapter(
    private var bookList: List<livro>,
    private val onBookClick: (livro) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


    fun updateBooks(newBooks: List<livro>) {
        bookList = newBooks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = bookList.size

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgBookCover: ImageView = itemView.findViewById(R.id.imgBookCover)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvPublisher: TextView = itemView.findViewById(R.id.tvPublisher)
        private val tvYear: TextView = itemView.findViewById(R.id.tvYear)

        fun bind(book: livro) {

            tvTitle.text = book.title
            tvPublisher.text = book.publisher
            tvYear.text = book.year.toString()


            Glide.with(itemView.context)
                .load(book.imageUrl)
                .into(imgBookCover)


            itemView.setOnClickListener {

                val context = itemView.context
                val intent = Intent(context, BookDetailsActivity::class.java).apply {
                    putExtra("BOOK_ID", book.id)
                }

                context.startActivity(intent)
            }
        }
    }
}
