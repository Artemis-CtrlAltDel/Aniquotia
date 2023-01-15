package com.example.cmo.di

import android.content.Context
import androidx.room.Room
import com.example.cmo.data.AniquotiaDb
import com.example.cmo.data.local.DetailsDao
import com.example.cmo.data.local.QuotesDao
import com.example.cmo.data.network.DetailsApi
import com.example.cmo.data.network.QuotesApi
import com.example.cmo.data.repository.DetailsRepository
import com.example.cmo.data.repository.QuotesRepository
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

    @Singleton
    @Provides
    fun provideAnimeQuotesApi() =
        Retrofit.Builder()
            .baseUrl(Constants.ANIME_QUOTES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(QuotesApi::class.java)

    @Singleton
    @Provides
    fun provideAnimeDetailsApi() =
        Retrofit.Builder()
            .baseUrl(Constants.ANIME_DETAILS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(DetailsApi::class.java)

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AniquotiaDb::class.java, "anime_quote_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideAnimeQuotesDao(db: AniquotiaDb) =
        db.animeQuoteDao()

    @Singleton
    @Provides
    fun provideAnimeDetailsDao(db: AniquotiaDb) =
        db.animeDetailsDao()

    @Singleton
    @Provides
    fun provideAnimeQuotesRepository(api: QuotesApi, dao: QuotesDao) =
        QuotesRepository(api, dao)

    @Singleton
    @Provides
    fun provideAnimeDetailsRepository(api: DetailsApi, dao: DetailsDao) =
        DetailsRepository(api, dao)
}