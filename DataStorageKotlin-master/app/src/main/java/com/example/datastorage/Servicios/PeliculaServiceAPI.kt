package com.example.datastorage.Servicios

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class PeliculaServiceAPI {
    private var mRequestQueue: RequestQueue ?= null
    private var context: Context  ?= null
    private var iPelicula: IPeliculaApi ?= null

    var imageLoader: ImageLoader?= null
        private set

    val requestQueue: RequestQueue?
        get(){
            if(mRequestQueue == null){
                mRequestQueue = Volley.newRequestQueue(context!!.applicationContext)
            }
            return mRequestQueue
        }

    private constructor(context: Context, interfacePelicula: IPeliculaApi) {
        this.context = context
        this.iPelicula = interfacePelicula
        mRequestQueue = requestQueue
        this.imageLoader = ImageLoader(mRequestQueue, object: ImageLoader.ImageCache{
            private  val mCache = LruCache<String,Bitmap>(10)
            override fun getBitmap(url: String?): Bitmap? {
                return mCache.get(url!!)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                mCache.put(url!!,bitmap!!)
            }
        })
    }

    private constructor(context: Context) {
        this.context = context
        mRequestQueue = requestQueue
        this.imageLoader = ImageLoader(mRequestQueue, object: ImageLoader.ImageCache{
            private  val mCache = LruCache<String,Bitmap>(10)
            override fun getBitmap(url: String): Bitmap? {
                return mCache.get(url)
            }

            override fun putBitmap(url: String, bitmap: Bitmap) {
                mCache.put(url,bitmap)
            }
        })
    }

    fun <T> addToRequestQueue(req:Request<T>){
        requestQueue?.add(req)
    }

    //get method
    fun getRequest(url:String){
        val getRequest = JsonObjectRequest(Request.Method.GET,url,null,Response.Listener { response ->
            iPelicula!!.onResponse(response.toString())
        },Response.ErrorListener { error ->
            iPelicula!!.onResponse(error.message!!)
        })

        addToRequestQueue(getRequest)
    }

    fun postRequest(url: String){
        val postRequest = object : StringRequest ( Request.Method.POST, url, Response.Listener { response ->
            iPelicula!!.onResponse(response.toString())
        },Response.ErrorListener { error ->
            iPelicula!!.onResponse(error.message!!)
        })

        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                //AQUI AGREGO LOS VALORES A ENVIARRRR
                params["name"] = "Eddy Lee"
                return params
            }
        }

        addToRequestQueue(postRequest)
    }

    fun deleteRequest(url:String){
        val deleteRequest = StringRequest(Request.Method.DELETE,url,Response.Listener { response ->
            iPelicula!!.onResponse(response)
        },Response.ErrorListener { error ->
            iPelicula!!.onResponse(error.message!!)
        })

        addToRequestQueue(deleteRequest)
    }

    fun destroy() {
        mInstance = null
    }

    companion object {
        private var mInstance : PeliculaServiceAPI?=null
        @Synchronized
        fun getInstance(context: Context): PeliculaServiceAPI{
            if (mInstance == null){
                mInstance = PeliculaServiceAPI(context)
            }
            return mInstance!!
        }

        @Synchronized
        fun getInstance(context: Context, iPeliculaApi: IPeliculaApi): PeliculaServiceAPI{
            if (mInstance == null){
                mInstance = PeliculaServiceAPI(context,iPeliculaApi)
            }
            return mInstance!!
        }
    }
}