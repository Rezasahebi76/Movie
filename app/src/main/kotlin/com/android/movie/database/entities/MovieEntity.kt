package com.android.movie.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    @ColumnInfo("poster_path")
    val posterPath: String?,
    @ColumnInfo("remote_id")
    val remoteId: Int,
    @ColumnInfo("next_page")
    val nextPage: Int?
)