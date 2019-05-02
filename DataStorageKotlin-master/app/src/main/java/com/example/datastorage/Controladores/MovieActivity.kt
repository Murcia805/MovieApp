package com.example.datastorage.Controladores

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
import org.json.JSONObject

class MovieActivity : AppCompatActivity(), IPeliculaApi {
    override fun onResponse(response: String) {
        Toast.makeText(this@MovieActivity, ""+response,Toast.LENGTH_SHORT).show()
        handleJson(response)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        PeliculaServiceAPI.getInstance(this@MovieActivity,this@MovieActivity)
            .getRequest("https://api.themoviedb.org/3/movie/76341?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb")
    }

    //https://api.themoviedb.org/3/search/movie?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb&language=en-US&query=mad%20max&page=1&include_adult=false

    private fun handleJson(jsonString: String?){
        val jsonObject = JSONObject(jsonString)
        val movie = Pelicula(
            MovieDataResponse(
                jsonObject.getInt("id"),
                jsonObject.getString("adult"),
                //jsonArray("collection"),
                //jsonObject.getInt("genres"),
                jsonObject.getString("imdb_id"),
                jsonObject.getString("overview"),
                jsonObject.getInt("popularity"),
                jsonObject.getString("poster_path"),
                jsonObject.getString("release_date"),
                jsonObject.getString("title"),
                jsonObject.getInt("vote_average"),
                jsonObject.getInt("vote_count")
            )
        )

        val list = ArrayList<Pelicula>()
        list.add(movie)
        val adapater = PeliculasListAdapter(this,list)
        listMovies.adapter = adapater
    }

    override fun consultMovies(): List<Pelicula>? {return emptyList()}
    override fun savePelicula(movie: Pelicula) {}

    fun getMethod(view: View){

    }

    fun postMethod(){
    }
}