package com.example.datastorage.Controladores

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.datastorage.Adapters.PeliculasListAdapter
import com.example.datastorage.Modelos.MovieDataResponse
import com.example.datastorage.Modelos.Pelicula
import com.example.datastorage.R
import com.example.datastorage.Servicios.IPeliculaApi
import com.example.datastorage.Servicios.PeliculaServiceAPI
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_users_list.*
import org.json.JSONArray
import org.json.JSONObject

class MovieActivity : AppCompatActivity(), IPeliculaApi {

    private var instancePeliculaApi :PeliculaServiceAPI? = null

    override fun onResponse(response: String) {
        //Toast.makeText(this@MovieActivity, ""+response,Toast.LENGTH_SHORT).show()
        handleJson(response)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        instancePeliculaApi = PeliculaServiceAPI.getInstance(this@MovieActivity,this@MovieActivity)

        instancePeliculaApi!!.getRequest(
                "https://api.themoviedb.org/3/movie/popular?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb&language=en-US&page=1")
    }

    override fun onDestroy() {
        super.onDestroy()
        instancePeliculaApi!!.destroy()
    }

    //https://api.themoviedb.org/3/search/movie?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb&language=en-US&query=mad%20max&page=1&include_adult=false

    private fun handleJson(jsonString: String?){
        val jsonObject = JSONObject(jsonString).getString("results")
        val list = ArrayList<Pelicula>()
        val jsonArray = JSONArray(jsonObject)
        var i = 0
        while (i  < 5){
            val jsonActual = jsonArray.getJSONObject(i)
            val movie = Pelicula(
                MovieDataResponse(
                    jsonActual.getInt("id"),
                    jsonActual.getString("adult"),
                    jsonActual.getString("overview"),
                    jsonActual.getInt("popularity"),
                    jsonActual.getString("poster_path"),
                    jsonActual.getString("release_date"),
                    jsonActual.getString("title"),
                    jsonActual.getInt("vote_average"),
                    jsonActual.getInt("vote_count")
                )
            )
            list.add(movie)
            i = i + 1

        }
        val adapater = PeliculasListAdapter(this,list)
        listMovies.adapter = adapater
    }

    override fun consultMovies(): List<Pelicula>? {return emptyList()}
    override fun savePelicula(movie: Pelicula) {}

    fun newMovie(view: View){
        instancePeliculaApi!!.destroy()
        val intent = Intent(this, AddMovieActivity::class.java)
        startActivity(intent)
    }

    fun myMovies(view: View){
        val intent = Intent(this, RedirectActivity::class.java)
        startActivity(intent)
    }
}