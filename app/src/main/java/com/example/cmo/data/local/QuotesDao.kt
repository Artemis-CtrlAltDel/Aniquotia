package com.example.cmo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cmo.data.local.pojo.Quote

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuote(quote: Quote)

    @Query("SELECT * FROM quotes_table ORDER BY savedAtTimestamp DESC")
    fun getQuotes(): LiveData<List<Quote>>

    @Delete
    fun deleteQuote(quote: Quote)
}