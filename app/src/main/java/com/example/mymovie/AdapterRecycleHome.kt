package com.example.mymovie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.databinding.ItemRecycleHomeBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class AdapterRecycleHome(private val data: ArrayList<Movie>, private val movieEvent: MovieEvent) :
    RecyclerView.Adapter<AdapterRecycleHome.MovieHolder>() {


    inner class MovieHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        val subject = itemView.findViewById<TextView>(R.id.itemTxtSubject)
        val genre = itemView.findViewById<TextView>(R.id.itemTxtGenreHome)
        val numRatting = itemView.findViewById<TextView>(R.id.itemTxtRatting)
        val ratting = itemView.findViewById<RatingBar>(R.id.itemRattingHOme)
        val img = itemView.findViewById<ImageView>(R.id.itemImgHome)
        fun bindItem(position: Int) {
            subject.text = data[position].subject
            genre.text = data[position].genre
            numRatting.text = "(" + data[position].numOfRatting.toString() + " Ratings)"
            ratting.rating = data[position].ratting
            Glide.with(context)
                .load(data[position].ulr)
                .transform(RoundedCornersTransformation(16, 4))
                .into(img)

            itemView.setOnClickListener {

                movieEvent.short(data[adapterPosition], position)
            }
            itemView.setOnLongClickListener {

                movieEvent.long(data[adapterPosition], adapterPosition)

                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding =
            ItemRecycleHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding.root, parent.context)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    // add an item to list
    fun add(movie: Movie) {
        data.add(0, movie)
        notifyItemInserted(0)
    }
    // delete an item in list
    fun remove(movie: Movie, position: Int) {
        data.remove(movie)
        notifyItemRemoved(position)
    }
    // edit an item
    fun edit(movie: Movie, position: Int) {
        data.set(position , movie)
        notifyItemChanged(position)

    }
    // search an item in the list
    fun setData ( newData : ArrayList<Movie>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    interface MovieEvent {
        fun short(movie: Movie, position: Int)
        fun long(movie: Movie, position: Int)
    }
}