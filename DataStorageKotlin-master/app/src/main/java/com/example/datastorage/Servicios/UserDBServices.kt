package com.example.datastorage.Servicios

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import com.example.datastorage.Modelos.MovieDataResponse
import com.example.datastorage.Modelos.Pelicula
import com.example.datastorage.Modelos.User

class UserDBServices(context: Context) : SQLiteOpenHelper(context, "UserDBService", null, 1), IUserServices,IPeliculaApi
{
    override fun onResponse(response: String) {}

    override fun onCreate(db: SQLiteDatabase?) {
        val sql1 : String = "CREATE TABLE users(idUser int primarykey," +
                           " name text," +
                           " email text," +
                           " age integer," +
                           " password text)"
        db?.execSQL(sql1)

        val sql2 : String = "CREATE TABLE peliculas(idMovie int primarykey," +
                            "adult text," +
                            "imdb_id text," +
                            "overview text," +
                            "popularity integer," +
                            "poster_path text," +
                            "release_date text," +
                            "title text," +
                            "vote_average integer," +
                            "vote_count integer)"

        db?.execSQL(sql2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("Sin implementación")
    }

    override fun verifyUser(user: User) : Boolean {
        val sql : String = "SELECT email, password FROM users" +
                           " where email='${user.email}'"

        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var returnValue : Boolean = false

        if(result.moveToFirst()) {
            if (user.email.equals(result.getString(0)) && user.password.equals(result.getString(1))) {
                returnValue = true
            }
        }
        this.close()
        return returnValue
    }

    override fun saveUser(user: User) {
        var localUser = ContentValues()
        localUser.put("name", user.name)
        localUser.put("email", user.email)
        localUser.put("age", user.age)
        localUser.put("password", user.password)
        this.executeModification(localUser)
    }

    override fun savePelicula(movie: Pelicula){
        var localPelicula = ContentValues()
        localPelicula.put("idMovie",movie.data.id)
        localPelicula.put("adult",movie.data.adult)
        localPelicula.put("imdb_id",movie.data.imdb_id)
        localPelicula.put("overview",movie.data.overview)
        localPelicula.put("popularity",movie.data.popularity)
        localPelicula.put("poster_path",movie.data.poster_path)
        localPelicula.put("release_date",movie.data.release_date)
        localPelicula.put("title",movie.data.title)
        localPelicula.put("vote_average",movie.data.vote_average)
        localPelicula.put("vote_count",movie.data.vote_count)
        this.executeModificacionMovie(localPelicula)
    }

    override fun consultUsers(): List<User>? {
        val sql : String = "SELECT idUser, name, email, age, password FROM users"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var listUsers : MutableList<User>? = ArrayList<User>()
        result.moveToFirst()

        while(!result.isAfterLast)
        {
            var user : User = User(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                result.getString(4)
            )
            listUsers?.add(user)
            result.moveToNext()
        }
        return listUsers
    }

    override fun consultMovies(): List<Pelicula>? {
        val sql : String = "Select idMovie, adult,imdb_id,overview,popularity,poster_path,release_date,title,vote_average,vote_count FROM peliculas"
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
                    result.getString(3),
                    result.getInt(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getInt(8),
                    result.getInt(9)
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

    private fun executeModification(user: ContentValues) {
        val bd = this.writableDatabase
        bd.insert("users", null, user)
        bd.close()
    }

    private fun executeModificacionMovie(pelicula : ContentValues){
        val bd = this.writableDatabase
        bd.insert("peliculas",null,pelicula)
        bd.close()
    }
}