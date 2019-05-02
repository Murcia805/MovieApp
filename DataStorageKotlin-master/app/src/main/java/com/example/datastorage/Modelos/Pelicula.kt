package com.example.datastorage.Modelos

class Pelicula(val data: MovieDataResponse)

class MovieDataResponse(
    val id: Int?,
    val adult: String?,
    //val collection: List<CollectionChildrenResponse>,
    //val genres: List<GenresChildrenResponse>,
    val imdb_id: String?,
    val overview: String?,
    val popularity: Int?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Int?,
    val vote_count: Int?
)

class CollectionChildrenResponse(val data: CollectionDataResponse)

class CollectionDataResponse(
    val id: Int,
    val name: String,
    val poster_path: String,
    val backdrop_path: String
)

class GenresChildrenResponse(val data: GenresDataResponse)

class GenresDataResponse(
    val id: Int,
    val name: String
)