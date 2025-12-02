package com.example.monappli

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api: TmdbAPI,
    private val filmDao: FilmDao,
    private val serieDao: SerieDao,
    private val acteurDao: ActeurDao
) {
    private val apiKey = "5247bdc4c71b75427127dd75d614164c"

    // ========= FILMS (API + marquage isFav) =========

    suspend fun getTrendingMovies(): List<TmdbMovie> {
        val favIds = filmDao.getFavFilmsIds().toSet()
        val films = api.getTrendingMovies(apiKey).results
        return films.map { film ->
            if (film.id in favIds) film.copy(isFav = true) else film
        }
    }

    suspend fun searchMovies(query: String): List<TmdbMovie> {
        val favIds = filmDao.getFavFilmsIds().toSet()
        val films = api.searchMovies(apiKey, query).results
        return films.map { film ->
            if (film.id in favIds) film.copy(isFav = true) else film
        }
    }

    // ========= SERIES (API + marquage isFav) =========

    suspend fun getTrendingSeries(): List<TmdbSerie> {
        val favIds = serieDao.getFavSeriesIds().toSet()
        val series = api.getTrendingSeries(apiKey).results
        return series.map { serie ->
            if (serie.id in favIds) serie.copy(isFav = true) else serie
        }
    }

    suspend fun searchSeries(query: String): List<TmdbSerie> {
        val favIds = serieDao.getFavSeriesIds().toSet()
        val series = api.searchSeries(apiKey, query).results
        return series.map { serie ->
            if (serie.id in favIds) serie.copy(isFav = true) else serie
        }
    }

    // ========= ACTEURS (API + marquage isFav) =========

    suspend fun getTrendingActeurs(): List<TmdbActeur> {
        val favIds = acteurDao.getFavActeursIds().toSet()
        val acteurs = api.getTrendingActeurs(apiKey).results
        return acteurs.map { acteur ->
            if (acteur.id in favIds) acteur.copy(isFav = true) else acteur
        }
    }

    suspend fun searchActeurs(query: String): List<TmdbActeur> {
        val favIds = acteurDao.getFavActeursIds().toSet()
        val acteurs = api.searchActeurs(apiKey, query).results
        return acteurs.map { acteur ->
            if (acteur.id in favIds) acteur.copy(isFav = true) else acteur
        }
    }

    // ========= FAVORIS FILMS =========

    suspend fun getFilmsFav(): List<TmdbMovie> {
        val ids = filmDao.getFavFilmsIds()
        return ids.map { id ->
            api.getMovieDetails(id, apiKey).copy(isFav = true)
        }
    }

    suspend fun addFilmFav(id: Int) {
        filmDao.insertFilm(FilmEntity(id))
    }

    suspend fun removeFilmFav(id: Int) {
        filmDao.deleteFilm(id)
    }

    // ========= FAVORIS SERIES =========

    suspend fun getSeriesFav(): List<TmdbSerie> {
        val ids = serieDao.getFavSeriesIds()
        return ids.map { id ->
            api.getSerieDetails(id, apiKey).copy(isFav = true)
        }
    }

    suspend fun addSerieFav(id: Int) {
        serieDao.insertSerie(SerieEntity(id))
    }

    suspend fun removeSerieFav(id: Int) {
        serieDao.deleteSerie(id)
    }

    // ========= FAVORIS ACTEURS =========

    suspend fun getActeursFav(): List<TmdbActeur> {
        val ids = acteurDao.getFavActeursIds()
        return ids.map { id ->
            api.getActeurDetails(id, apiKey).copy(isFav = true)
        }
    }

    suspend fun addActeurFav(id: Int) {
        acteurDao.insertActeur(ActeurEntity(id))
    }

    suspend fun removeActeurFav(id: Int) {
        acteurDao.deleteActeur(id)
    }
}