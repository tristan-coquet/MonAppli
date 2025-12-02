package com.example.monappli

//Contient TmbdMovie,Serie et Acteur avec tout ses atribut que nous récupérons

data class TmdbMovieResult(
    val page: Int = 0,
    val results: List<TmdbMovie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class TmdbMovie(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val media_type: String? = null,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0,
    // ✅ Pour la compatibilité avec KnownFor
    val name: String? = null,
    val first_air_date: String? = null,
    var isFav: Boolean = false //
)

// Séries
data class TmdbSerieResult(
    val page: Int = 0,
    val results: List<TmdbSerie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class TmdbSerie(
    val backdrop_path: String? = null,
    val first_air_date: String = "",
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val media_type: String? = null,
    val name: String = "",
    val origin_country: List<String> = listOf(),
    val original_language: String = "",
    val original_name: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0 ,
    var isFav: Boolean = false
)

// Acteurs
data class TmdbActeurResult(
    val page: Int = 0,
    val results: List<TmdbActeur> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

// ✅ NOUVEAU : Modèle générique pour films ET séries dans known_for
data class KnownFor(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val id: Int = 0,
    val title: String? = null,              // Films
    val name: String? = null,               // Séries TV
    val original_language: String = "",
    val original_title: String? = null,     // Films
    val original_name: String? = null,      // Séries TV
    val overview: String = "",
    val poster_path: String? = null,
    val media_type: String? = null,         // "movie" ou "tv"
    val genre_ids: List<Int> = listOf(),
    val popularity: Double = 0.0,
    val release_date: String? = null,       // Films
    val first_air_date: String? = null,     // Séries TV
    val vote_average: Double = 0.0,
    val vote_count: Int = 0,
    val origin_country: List<String>? = null
)

// ✅ ACTEUR CORRIGÉ
data class TmdbActeur(
    val adult: Boolean = false,
    val gender: Int = 0,
    val id: Int = 0,
    val known_for: List<KnownFor> = listOf(),  // ✅ Changé de TmdbMovie à KnownFor
    val known_for_department: String? = null,
    val media_type: String? = null,
    val name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String? = null,
    var isFav: Boolean = false
)