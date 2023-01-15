package com.example.cmo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cmo.data.local.pojo.Details

@Dao
interface DetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetails(vararg details: Details)

    @Delete
    fun deleteDetails(details: Details)

    @Query("SELECT * FROM details_table")
    fun getDetailsByTitle(): LiveData<List<Details>>

    @Query("DELETE FROM details_table")
    fun deleteAll()
}