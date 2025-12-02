package com.example.monappli

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): TmdbAPI {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())   // 👈 IMPORTANT
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // 👈 on passe le moshi
            .build()
            .create(TmdbAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tmdb_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFilmDao(database: AppDatabase): FilmDao = database.filmDao()

    @Provides
    fun provideSerieDao(database: AppDatabase): SerieDao = database.serieDao()

    @Provides
    fun provideActeurDao(database: AppDatabase): ActeurDao = database.acteurDao()
}