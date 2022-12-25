package com.example.cmo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cmo.data.local.pojo.AnimeQuote

@Dao
interface AnimeQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuote(quote: AnimeQuote)

    @Query("SELECT * FROM QUOTE_TABLE")
    fun getQuotes(): LiveData<List<AnimeQuote>>

    @Delete
    fun deleteQuote(quote: AnimeQuote)
}