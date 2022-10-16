package com.example.mymovie

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovie.databinding.ActivityHomeBinding
import com.example.mymovie.databinding.DialogBtnAddHomeBinding
import com.example.mymovie.databinding.DialogDeleteHomeBinding
import com.example.mymovie.databinding.DialogEditHomeBinding
import java.util.*
import kotlin.collections.ArrayList

class FragmentHome : Fragment(), AdapterRecycleHome.MovieEvent {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var data: ArrayList<Movie>
    private lateinit var myAdapter: AdapterRecycleHome
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = ActivityHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        data = arrayListOf(

            Movie("Avengers",
                "Action",
                "https://www.plaza.ir/wp-content/uploads/2019/05/avengers-endgame-23.jpg",
                4.5f,
                125
            ),
            Movie("Moon Knight",
                "Action",
                "https://www.comicon.ir/wp-content/uploads/2019/12/moon-knight.jpg",
                4.5f, 15),
            Movie("The GrayMan",
                "Drama",
                "https://zbcdn.cloud/files/cache/80231_greyman_image.2d42c6.jpeg",
                2.5f, 64),
            Movie("Dictator",
                "Comedy",
                "https://bayanbox.ir/view/5832553765737596298/dictator-poster-sacha-baron-cohen.jpg",
                5f, 98),
            Movie("See",
                "Action",
                "https://upload.wikimedia.org/wikipedia/fa/a/a0/See_%28TV_series%29_poster.jpg",
                3.5f, 76),
            Movie("The House of Dragons",
                "Action",
                "https://www.film2serial.ir/wp-content/uploads/2022/08/House-of-the-Dragon-2022.jpg",
                4.5f, 135)

        )
        myAdapter = AdapterRecycleHome(data.clone() as ArrayList<Movie>, this)
        binding.recycleHome.adapter = myAdapter
        binding.recycleHome.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        add(data)
        search(data)

    }

    fun search(data: ArrayList<Movie>) {
        binding.editTextSearchHome.addTextChangedListener {

            if (it!!.isNotEmpty()) {
                val clone = data.clone() as ArrayList<Movie>
                val filter = clone.filter { movie ->
                    movie.subject.contains(it)
                }
                myAdapter.setData(ArrayList(filter))
            } else {

                myAdapter.setData(data.clone() as ArrayList<Movie>)
            }


        }
    }

    fun add(data: ArrayList<Movie>) {
        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(context).create()
            val view = DialogBtnAddHomeBinding.inflate(layoutInflater)
            dialog.setView(view.root)
            dialog.setCancelable(true)
            dialog.show()
            view.dialogAddBtnCancel.setOnClickListener {
                dialog.dismiss()
            }
            view.dialogAddBtnAccept.setOnClickListener {
                val subject = view.dialogAddSubject.text.toString()
                val genre = view.dialogAddGenre.text.toString()
                val url = view.dialogAddImg.text.toString()
                val min = 0
                val max = 5
                val ratting = min + Random().nextFloat() * (max - min)
                val numRatting = (0..150).random()
                if (subject.isNotEmpty() && genre.isNotEmpty() && url.isNotEmpty()) {
                    val movie = Movie(subject , genre , url ,ratting ,numRatting)
                    dialog.dismiss()
                    myAdapter.add(movie)
                    binding.recycleHome.scrollToPosition(0)
                } else {
                    Toast.makeText(context, "لطفا مقادیر را وارد کنید", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun short(movie: Movie, position: Int) {
        val dialog = AlertDialog.Builder(context).create()
        val view = DialogEditHomeBinding.inflate(layoutInflater)
        dialog.setView(view.root)
        dialog.setCancelable(true)
        view.dialogEditTxtSubject.setText(movie.subject).toString()
        view.dialogEditTxtGenre.setText(movie.genre).toString()
        view.dialogEditTxtImg.setText(movie.ulr).toString()
        view.dialogEditBtnAccept.setOnClickListener {
            val subject = view.dialogEditTxtSubject.text.toString()
            val genre = view.dialogEditTxtGenre.text.toString()
            val url = view.dialogEditTxtImg.text.toString()

            if (subject.isNotEmpty() && genre.isNotEmpty() && url.isNotEmpty()) {
                val newMovie = Movie(subject, genre, url, movie.ratting, movie.numOfRatting)
                dialog.dismiss()
                myAdapter.edit(newMovie, position)
            } else {
                Toast.makeText(context, "لطفا اطلاعات را کامل وارد کنید", Toast.LENGTH_SHORT).show()
            }
        }
        view.dialogEditBtnCancel.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    override fun long(movie: Movie, position: Int) {
        val dialog = AlertDialog.Builder(context).create()
        val view = DialogDeleteHomeBinding.inflate(layoutInflater)
        dialog.setView(view.root)
        dialog.setCancelable(true)
        dialog.show()
        view.dialogDeleteBtnNo.setOnClickListener { dialog.dismiss() }
        view.dialogDeleteBtnYes.setOnClickListener {
            dialog.dismiss()
            myAdapter.remove(movie, position)

        }


    }
}