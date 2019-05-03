package com.example.datastorage.Servicios

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.datastorage.Modelos.MovieDataResponse
import com.example.datastorage.Modelos.Pelicula

class MovieDBServices(context: Context) : SQLiteOpenHelper(context, "MovieDBServices", null, 1), IPeliculaApi{
    override fun onCreate(db: SQLiteDatabase?) {
        val sql : String = "CREATE TABLE peliculas(idMovie int primarykey," +
                "adult text," +
                "overview text," +
                "popularity integer," +
                "poster_path text," +
                "release_date text," +
                "title text," +
                "vote_average integer," +
                "vote_count integer)"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) { }

    override fun onResponse(response: String) { }

    override fun savePelicula(movie: Pelicula) {
        var localPelicula = ContentValues()
        localPelicula.put("idMovie",movie.data.id)
        localPelicula.put("adult",movie.data.adult)
        localPelicula.put("overview",movie.data.overview)
        localPelicula.put("popularity",movie.data.popularity)
        localPelicula.put("poster_path",movie.data.poster_path)
        localPelicula.put("release_date",movie.data.release_date)
        localPelicula.put("title",movie.data.title)
        localPelicula.put("vote_average",movie.data.vote_average)
        localPelicula.put("vote_count",movie.data.vote_count)
        this.executeModificacion(localPelicula)
    }

    override fun consultMovies(): List<Pelicula>? {
        val sql : String = "Select idMovie, adult,overview,popularity,poster_path,release_date,title,vote_average,vote_count FROM peliculas"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var listPeliculas : MutableList<Pelicula>? = ArrayList<Pelicula>()
        result.moveToFirst()

        while(!result.isAfterLast)
        {

            var pelicula : Pelicula = Pelicula(
                MovieDataResponse(
                    result.getInt(0),
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getInt(7),
                    result.getInt(8)
                )
            )

            listPeliculas?.add(pelicula)
            result.moveToNext()
        }

        return listPeliculas
    }

    private fun executeQuery(sql: String, bd : SQLiteDatabase) : Cursor {
        val consulta : Cursor = bd.rawQuery(sql,null)
        return consulta
    }

    private fun executeModificacion(pelicula : ContentValues){
        val bd = this.writableDatabase
        bd.insert("peliculas",null,pelicula)
        bd.close()
    }

}