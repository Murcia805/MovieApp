package com.example.datastorage.Servicios

import com.example.datastorage.Modelos.Pelicula

interface IPeliculaApi{
    fun onResponse(response: String)
    fun savePelicula(movie: Pelicula)
    fun consultMovies(): List<Pelicula>?
}