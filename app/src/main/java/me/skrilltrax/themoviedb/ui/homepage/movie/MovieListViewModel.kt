package me.skrilltrax.themoviedb.ui.homepage.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.model.list.movie.MovieListResultItem
import me.skrilltrax.themoviedb.network.api.movie.MovieListRepository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class MovieListViewModel @ViewModelInject constructor(private val movieListRepository: MovieListRepository) : ViewModel() {

    private var popularMovieStatus: Boolean = false
    private var playingMovieStatus: Boolean = false
    private var upcomingMovieStatus: Boolean = false
    private var topRatedMovieStatus: Boolean = false

    private val _popularMovieList: MutableLiveData<ArrayList<MovieListResultItem>> =
        MutableLiveData()
    private val _playingMovieList: MutableLiveData<ArrayList<MovieListResultItem>> =
        MutableLiveData()
    private val _upcomingMovieList: MutableLiveData<ArrayList<MovieListResultItem>> =
        MutableLiveData()
    private val _topRatedMovieList: MutableLiveData<ArrayList<MovieListResultItem>> =
        MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val popularMovieList: LiveData<ArrayList<MovieListResultItem>>
        get() = _popularMovieList

    val playingMovieList: LiveData<ArrayList<MovieListResultItem>>
        get() = _playingMovieList

    val upcomingMovieList: LiveData<ArrayList<MovieListResultItem>>
        get() = _upcomingMovieList

    val topRatedMovieList: LiveData<ArrayList<MovieListResultItem>>
        get() = _topRatedMovieList

    init {
        fetchPopularMovieList()
        fetchPlayingMovieList()
        fetchUpcomingMovieList()
        fetchTopRatedMovieList()
    }

    fun fetchPopularMovieList() {
        if (!popularMovieStatus) {
            viewModelScope.launch {
                val popularList = movieListRepository.getPopularMovieList()
                if (popularList != null) {
                    _popularMovieList.postValue(popularList.results as ArrayList<MovieListResultItem>?)
                    popularMovieStatus = true
                    checkStatus()
                }
            }
        }
    }

    fun fetchPlayingMovieList() {
        if (!playingMovieStatus) {
            viewModelScope.launch {
                val playingList = movieListRepository.getPlayingMovieList()
                if (playingList != null) {
                    _playingMovieList.postValue(playingList.results as ArrayList<MovieListResultItem>?)
                    playingMovieStatus = true
                    checkStatus()
                }
            }
        }
    }

    fun fetchUpcomingMovieList() {
        if (!upcomingMovieStatus) {
            viewModelScope.launch {
                val upcomingList = movieListRepository.getUpcomingMovieList()
                if (upcomingList != null) {
                    _upcomingMovieList.postValue(upcomingList.results as ArrayList<MovieListResultItem>?)
                    upcomingMovieStatus = true
                    checkStatus()
                }
            }
        }
    }

    fun fetchTopRatedMovieList() {
        if (!topRatedMovieStatus) {
            viewModelScope.launch {
                val topRatedList = movieListRepository.getTopRatedMovieList()
                if (topRatedList != null) {
                    _topRatedMovieList.postValue(topRatedList.results as ArrayList<MovieListResultItem>?)
                    topRatedMovieStatus = true
                    checkStatus()
                }
            }
        }
    }

    private fun checkStatus() {
        if (popularMovieStatus && upcomingMovieStatus && playingMovieStatus && topRatedMovieStatus) {
            isLoading.postValue(false)
            Timber.d("set isLoading false")
        }
    }
}
