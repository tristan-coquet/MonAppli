//TmdbAPI.kt
package com.example.monappli
//pour récupéré les infos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    // Films
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "fr"
    ): TmdbMovieResult

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "fr"
    ): TmdbMovieResult

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "credits",
        @Query("language") language: String = "fr"
    ): TmdbMovie

    // Séries
    @GET("trending/tv/week")
    suspend fun getTrendingSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "fr"
    ): TmdbSerieResult

    @GET("search/tv")
    suspend fun searchSeries(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "fr"
    ): TmdbSerieResult

    @GET("tv/{id}")
    suspend fun getSerieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "credits",
        @Query("language") language: String = "fr"
    ): TmdbSerie

    // Acteurs
    @GET("trending/person/week")
    suspend fun getTrendingActeurs(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "fr"
    ): TmdbActeurResult

    @GET("search/person")
    suspend fun searchActeurs(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "fr"
    ): TmdbActeurResult

    @GET("person/{id}")
    suspend fun getActeurDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "fr"
    ): TmdbActeur
}