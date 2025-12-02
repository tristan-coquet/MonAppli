package com.example.monappli

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FilmEntity::class, SerieEntity::class, ActeurEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
    abstract fun serieDao(): SerieDao
    abstract fun acteurDao(): ActeurDao
}