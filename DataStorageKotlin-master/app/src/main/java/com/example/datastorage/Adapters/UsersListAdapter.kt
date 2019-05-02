package com.example.datastorage.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.datastorage.R
import com.example.datastorage.Modelos.User

class UsersListAdapter(private val activity: Activity, usersList: List<User>?, listImages: MutableList<Int>?) : BaseAdapter(){
    private var usersList = ArrayList<User>()
    private var listImages = mutableListOf<Int>()

    init {
        this.usersList = usersList as ArrayList<User>
        this.listImages = listImages as MutableList<Int>
    }

    override fun getCount(): Int {
        return usersList.size
    }

    override fun getItem(i: Int): Any {
        return usersList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    fun getName(i: Int): String? {
        return usersList[i].name
    }

    fun getAge(i: Int): String? {
        return usersList[i].age.toString()
    }

    fun getEmail(i: Int): String? {
        return usersList[i].email
    }

    fun getPassword(i: Int): String? {
        return usersList[i].password
    }

    fun getItemImg(i: Int): Int {
        return listImages[i]
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.row_item, null)

        val image = vi.findViewById<ImageView>(R.id.userImage)
        val nombre = vi.findViewById<TextView>(R.id.Nombre)
        val edad = vi.findViewById<TextView>(R.id.Edad)

        nombre.text = usersList[i].name
        edad.text = usersList[i].age.toString()

        if (i >= listImages.size) {
            image.setImageResource(listImages[3])
        }

        else {
            image.setImageResource(listImages[i])
        }

        return vi
    }
}