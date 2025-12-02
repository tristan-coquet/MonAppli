package com.example.monappli

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // commutateur général « n’afficher que les favoris »
    private var showFavOnly = false

    // ====== FLOWS ======
    private val _movies = MutableStateFlow<List<TmdbMovie>>(emptyList())
    val movies: StateFlow<List<TmdbMovie>> = _movies

    private val _series = MutableStateFlow<List<TmdbSerie>>(emptyList())
    val series: StateFlow<List<TmdbSerie>> = _series

    private val _acteurs = MutableStateFlow<List<TmdbActeur>>(emptyList())
    val acteurs: StateFlow<List<TmdbActeur>> = _acteurs

    init {
        getFilmsInitiaux()
        getSeriesInitiales()
        getActeursInitiaux()
    }

    // ================== FILMS ==================

    fun getFilmsInitiaux() {
        Log.d("MainViewModel", "🎬 getFilmsInitiaux appelé")
        viewModelScope.launch {
            try {
                val result = repository.getTrendingMovies()
                Log.d("MainViewModel", "✅ Films reçus : ${result.size}")
                _movies.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur films initiaux : ${e.message}", e)
            }
        }
    }

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            getFilmsInitiaux()
            return
        }

        Log.d("MainViewModel", "🔍 Recherche films : '$query'")
        viewModelScope.launch {
            try {
                val result = repository.searchMovies(query)
                Log.d("MainViewModel", "✅ Films trouvés : ${result.size}")
                _movies.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur recherche films : ${e.message}", e)
            }
        }
    }

    // ================== SERIES ==================

    fun getSeriesInitiales() {
        Log.d("MainViewModel", "📺 getSeriesInitiales appelé")
        viewModelScope.launch {
            try {
                val result = repository.getTrendingSeries()
                Log.d("MainViewModel", "✅ Séries reçues : ${result.size}")
                _series.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur séries init : ${e.message}", e)
            }
        }
    }

    fun searchSeries(query: String) {
        if (query.isBlank()) {
            getSeriesInitiales()
            return
        }
        Log.d("MainViewModel", "🔍 Recherche série : '$query'")
        viewModelScope.launch {
            try {
                val result = repository.searchSeries(query)
                Log.d("MainViewModel", "✅ Séries trouvées : ${result.size}")
                _series.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur recherche série : ${e.message}", e)
            }
        }
    }

    // ================== ACTEURS ==================

    fun getActeursInitiaux() {
        Log.d("MainViewModel", "🎭 getActeursInitiaux appelé")
        viewModelScope.launch {
            try {
                val result = repository.getTrendingActeurs()
                Log.d("MainViewModel", "✅ Acteurs reçus : ${result.size}")
                _acteurs.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur acteurs init : ${e.message}", e)
            }
        }
    }

    fun searchActeurs(query: String) {
        if (query.isBlank()) {
            getActeursInitiaux()
            return
        }
        Log.d("MainViewModel", "🔍 Recherche acteur : '$query'")
        viewModelScope.launch {
            try {
                val result = repository.searchActeurs(query)
                Log.d("MainViewModel", "✅ Acteurs trouvés : ${result.size}")
                _acteurs.value = result
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur recherche acteur : ${e.message}", e)
            }
        }
    }

    // ================== TOGGLE FAVORIS ==================

    fun toggleFilmFav(film: TmdbMovie) {
        viewModelScope.launch {
            try {
                if (film.isFav) {
                    repository.removeFilmFav(film.id)
                } else {
                    repository.addFilmFav(film.id)
                }

                val updated = _movies.value.map { m ->
                    if (m.id == film.id) m.copy(isFav = !film.isFav) else m
                }

                _movies.value = if (showFavOnly) {
                    updated.filter { it.isFav }
                } else {
                    updated
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur toggleFilmFav : ${e.message}", e)
            }
        }
    }

    fun toggleSerieFav(serie: TmdbSerie) {
        viewModelScope.launch {
            try {
                if (serie.isFav) {
                    repository.removeSerieFav(serie.id)
                } else {
                    repository.addSerieFav(serie.id)
                }

                val updated = _series.value.map { s ->
                    if (s.id == serie.id) s.copy(isFav = !serie.isFav) else s
                }

                _series.value = if (showFavOnly) {
                    updated.filter { it.isFav }
                } else {
                    updated
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur toggleSerieFav : ${e.message}", e)
            }
        }
    }

    fun toggleActeurFav(acteur: TmdbActeur) {
        viewModelScope.launch {
            try {
                if (acteur.isFav) {
                    repository.removeActeurFav(acteur.id)
                } else {
                    repository.addActeurFav(acteur.id)
                }

                val updated = _acteurs.value.map { a ->
                    if (a.id == acteur.id) a.copy(isFav = !acteur.isFav) else a
                }

                _acteurs.value = if (showFavOnly) {
                    updated.filter { it.isFav }
                } else {
                    updated
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur toggleActeurFav : ${e.message}", e)
            }
        }
    }

    // ================== COMMUTATEUR GENERAL FAVORIS ==================

    fun setShowFavOnly(onlyFav: Boolean) {
        showFavOnly = onlyFav
        viewModelScope.launch {
            try {
                if (showFavOnly) {
                    // n'afficher que les favoris
                    val filmsFav = repository.getFilmsFav()
                    val seriesFav = repository.getSeriesFav()
                    val acteursFav = repository.getActeursFav()

                    _movies.value = filmsFav
                    _series.value = seriesFav
                    _acteurs.value = acteursFav
                } else {
                    // recharger les listes complètes depuis TMDB
                    val films = repository.getTrendingMovies()
                    val seriesList = repository.getTrendingSeries()
                    val acteursList = repository.getTrendingActeurs()

                    _movies.value = films
                    _series.value = seriesList
                    _acteurs.value = acteursList
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "❌ Erreur setShowFavOnly : ${e.message}", e)
            }
        }
    }
}
//ceci est un test