package com.example.cmo.di

import android.content.Context
import androidx.room.Room
import com.example.cmo.data.AnimeQuoteDb
import com.example.cmo.data.network.AnimeQuoteApi
import com.example.cmo.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideAnimeQuoteApiService() =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(AnimeQuoteApi::class.java)

    @JvmStatic
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AnimeQuoteDb::class.java, "anime_quote_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideDao(db: AnimeQuoteDb) =
        db.animeQuoteDao()
}