package com.example.datastorage.Adapters

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.Modelos.Pelicula
import com.example.datastorage.R
import com.example.datastorage.Servicios.IPeliculaApi
import java.net.URL

class PeliculasListAdapter(private val activity: Activity, list: List<Pelicula>?) : BaseAdapter(),IPeliculaApi{

    override fun onResponse(response: String) {}

    private var movieList = ArrayList<Pelicula>()

    init {
        this.movieList = list as ArrayList<Pelicula>
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.row_movie_item, null)

        val image = vi.findViewById<ImageView>(R.id.posterMovie)
        val nombre = vi.findViewById<TextView>(R.id.Nombre)
        val edad = vi.findViewById<TextView>(R.id.descripcion)

        nombre.text = movieList[i].data.title
        edad.text = movieList[i].data.overview

        DownLoadImageTask(image).execute("https://image.tmdb.org/t/p/w500"+movieList[i].data.poster_path)

        return vi
    }

    private class DownLoadImageTask(internal val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val urlOfImage = urls[0]
            return try {
                val inputStream = URL(urlOfImage).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) { // Catch the download exception
                e.printStackTrace()
                null
            }
        }
        override fun onPostExecute(result: Bitmap?) {
            if(result!=null){
                // Display the downloaded image into image view
                //Toast.makeText(imageView.context,"download success",Toast.LENGTH_SHORT).show()
                imageView.setImageBitmap(result)
            }else{
                Toast.makeText(imageView.context,"Error downloading",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun consultMovies(): List<Pelicula>? {
        return emptyList()
    }

    override fun savePelicula(movie: Pelicula) {}

    override fun getItem(position: Int): Any {
        return movieList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return movieList.size
    }

    fun getName(i: Int): String? {
        return movieList[i].data.title
    }

    fun getOverview(i: Int): String? {
        return movieList[i].data.overview
    }

    fun getAdult(i: Int): String? {
        return movieList[i].data.adult
    }

    fun getPopularity(i: Int): Int? {
        return movieList[i].data.popularity
    }

    fun getPosterpath(i: Int): String? {
        return movieList[i].data.poster_path
    }

    fun getReleasedate(i: Int): String? {
        return movieList[i].data.release_date
    }

    fun getVoteaverage(i: Int): Int? {
        return movieList[i].data.vote_average
    }

    fun getVotecount(i: Int): Int? {
        return movieList[i].data.vote_count
    }

    fun getID(i: Int): Int? {
        return movieList[i].data.id
    }

}