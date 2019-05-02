package com.example.datastorage.Controladores

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.datastorage.Adapters.PeliculasListAdapter
import com.example.datastorage.Adapters.UsersListAdapter
import com.example.datastorage.Modelos.MovieDataResponse
import com.example.datastorage.Modelos.Pelicula
import com.example.datastorage.Modelos.User
import com.example.datastorage.R
import com.example.datastorage.Servicios.UserDBServices
import kotlinx.android.synthetic.main.activity_users_list.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


class UsersListActivity : AppCompatActivity()
{
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        val url = "https://api.themoviedb.org/3/movie/76341?api_key=15d2ea6d0dc1d476efbca3eba2b9bbfb"

        AsyncTaskHandleJson().execute(url)

        /*var listImg: MutableList<Int> = mutableListOf<Int>(R.drawable.user, R.drawable.user1, R.drawable.user2, R.drawable.usern)
        val listPosts: List<User>? = UserDBServices(this).consultUsers()


        listView = findViewById<ListView>(R.id.listUsers) as ListView
        val adapter = UsersListAdapter(this, listPosts, listImg)
        listView.adapter = adapter

        val intent = Intent(this, ProfileActivity::class.java)

        listView.setClickable(true)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "Item Clicked " + adapter.getName(i),Toast.LENGTH_SHORT).show()

            intent.putExtra(ProfileActivity.IMAGE_PROFILE, adapter.getItemImg(i))
            intent.putExtra(ProfileActivity.NAME_PROFILE, adapter.getName(i))
            intent.putExtra(ProfileActivity.AGE_PROFILE, adapter.getAge(i)!!.toInt())
            intent.putExtra(ProfileActivity.EMAIL_PROFILE, adapter.getEmail(i))
            intent.putExtra(ProfileActivity.PASSW_PROFILE, adapter.getPassword(i))

            startActivity(intent)
        }*/
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>(){

        override fun doInBackground(vararg url: String?): String {
            var text : String
            val con = URL(url[0]).openConnection() as HttpURLConnection
            try {
                con.connect()
                text = con.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }finally {
                con.disconnect()
            }

            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

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
        listUsers.adapter = adapater
    }

    fun goMainActivity(view: View)
    {
        val intent = Intent(this, RedirectActivity::class.java)
        startActivity(intent)
    }
}
