package com.example.cmo.data.local.pojo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "quotes_table")
data class Quote(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val anime: String,
    val character: String,
    val quote: String,

    var isBookmarked: Boolean = false,
    var bookmarkCount: Long = 0
): Serializable, Parcelable {

    @IgnoredOnParcel
    var savedAtTimestamp: Long = 0L
}