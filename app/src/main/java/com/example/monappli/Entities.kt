package com.example.monappli

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmEntity(
    @PrimaryKey val id: Int
)

@Entity
data class SerieEntity(
    @PrimaryKey val id: Int
)

@Entity
data class ActeurEntity(
    @PrimaryKey val id: Int
)