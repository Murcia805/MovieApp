package com.example.datastorage.Controladores

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.Adapters.PeliculasListAdapter
import com.example.datastorage.Modelos.MovieDataResponse
import com.example.datastorage.Modelos.Pelicula
import com.example.datastorage.R
import com.example.datastorage.Servicios.IPeliculaApi
import com.example.datastorage.Servicios.PeliculaServiceAPI
import kotlinx.android.synthetic.main.activity_add_movie.*
import org.json.JSONArray
import org.json.JSONObject

class AddMovieActivity : AppCompatActivity(),IPeliculaApi {

    private var instancePeliculaApi :PeliculaServiceAPI? = null

    override fun onResponse(response: String) {
        val lista = handleJson(response)
        val adapter = PeliculasListAdapter(this,lista)
        listMoviesSearch.adapter = adapter

        val intent = Intent(this, MovieNewActivity::class.java)

        listMoviesSearch.setClickable(true)
        listMoviesSearch.setOnItemClickListener { adapterView, view, i, l ->

            Toast.makeText(this, "Item Clicked " + adapter.getName(i),Toast.LENGTH_SHORT).show()

            intent.putExtra(MovieNewActivity.ID_MOVIE, adapter.getID(i))
            intent.putExtra(MovieNewActivity.ADULT_MOVIE, adapter.getAdult(i))
            intent.putExtra(MovieNewActivity.IMAGE_MOVIE, adapter.getPosterpath(i))
            intent.putExtra(MovieNewActivity.NAME_MOVIE, adapter.getName(i))
            intent.putExtra(MovieNewActivity.DESCRIPTION_MOVIE, adapter.getOverview(i))
            intent.putExtra(MovieNewActivity.DATE_MOVIE, adapter.getReleasedate(i))
            intent.putExtra(MovieNewActivity.POPULAR_MOVIE, adapter.getPopularity(i)!!.toInt())
            intent.putExtra(MovieNewActivity.VOTES_AVERAGE_MOVIE, adapter.getVoteaverage(i)!!.toInt())
            intent.putExtra(MovieNewActivity.VOTES_COUNT_MOVIE, adapter.getVotecount(i)!!.toInt())

            startActivity(intent)
        }

    }

    override fun savePelicula(movie: Pelicula) {
    }

    override fun consultMovies(): List<Pelicula>? {
        return emptyList()
    }

    //https://api.themoviedb.org/3/search/movie?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb&language=en-US&query=mad%20max&page=1&include_adult=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)




    }

    override fun onDestroy() {
        super.onDestroy()
        instancePeliculaApi!!.destroy()
    }

    fun buscarPelicula(view: View){
        instancePeliculaApi = PeliculaServiceAPI.getInstance(this@AddMovieActivity,this@AddMovieActivity)
        val find = findViewById<TextView>(R.id.search)
        if (find.text.toString().isEmpty()){
            Toast.makeText(this@AddMovieActivity, "Ingrese algo para buscar",Toast.LENGTH_SHORT).show()
        }else{
            val str = find.text.toString().replace(" ", "%20")
            instancePeliculaApi!!.getRequest(
                    "https://api.themoviedb.org/3/search/movie?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb&language=en-US" +
                            "&query=" + str +
                            "&page=1&include_adult=true"
                )
        }
        instancePeliculaApi!!.destroy()
    }

    private fun handleJson(jsonString: String?): ArrayList<Pelicula>{
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
        return list
    }
}