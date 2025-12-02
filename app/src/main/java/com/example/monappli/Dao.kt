package com.example.monappli

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmDao {
    @Query("SELECT id FROM filmentity")
    suspend fun getFavFilmsIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: FilmEntity)

    @Query("DELETE FROM filmentity WHERE id = :id")
    suspend fun deleteFilm(id: Int)
}

@Dao
interface SerieDao {
    @Query("SELECT id FROM serieentity")
    suspend fun getFavSeriesIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie: SerieEntity)

    @Query("DELETE FROM serieentity WHERE id = :id")
    suspend fun deleteSerie(id: Int)
}

@Dao
interface ActeurDao {
    @Query("SELECT id FROM acteurentity")
    suspend fun getFavActeursIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActeur(acteur: ActeurEntity)

    @Query("DELETE FROM acteurentity WHERE id = :id")
    suspend fun deleteActeur(id: Int)
}