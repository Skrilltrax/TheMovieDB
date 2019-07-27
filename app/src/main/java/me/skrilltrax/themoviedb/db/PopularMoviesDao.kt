/*
package me.skrilltrax.themoviedb.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem

@Dao
interface PopularMoviesDao {

    @Insert
    fun insertList(movieResultsItemList: List<MovieResultsItem>)

    @Query("SELECT * FROM MovieResultsItem")
    fun getAll(): List<MovieResultsItem>

    @Query("SELECT * FROM MovieResultsItem WHERE type is :type")
    fun getListType(type: String): List<MovieResultsItem>

    @Delete
    fun delete(movieResultsItem: MovieResultsItem)

    @Delete
    fun delete(movieResultsItemList: List<MovieResultsItem>)
}*/
