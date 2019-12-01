package me.skrilltrax.themoviedb.ui.homepage.movie

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.model.list.ListResultItem
import me.skrilltrax.themoviedb.network.api.movie.MovieListRepository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class MovieListViewModel(private val movieListRepository: MovieListRepository) : ViewModel() {

    private var popularMovieStatus : Boolean = false
    private var playingMovieStatus : Boolean = false
    private var upcomingMovieStatus : Boolean = false
    private var topRatedMovieStatus : Boolean = false

    private val _popularMovieList : MutableLiveData<ArrayList<ListResultItem>> = MutableLiveData()
    private val _playingMovieList : MutableLiveData<ArrayList<ListResultItem>> = MutableLiveData()
    private val _upcomingMovieList : MutableLiveData<ArrayList<ListResultItem>> = MutableLiveData()
    private val _topRatedMovieList : MutableLiveData<ArrayList<ListResultItem>> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val popularMovieList : LiveData<ArrayList<ListResultItem>>
        get() = _popularMovieList

    val playingMovieList : LiveData<ArrayList<ListResultItem>>
        get() = _playingMovieList

    val upcomingMovieList : LiveData<ArrayList<ListResultItem>>
        get() = _upcomingMovieList

    val topRatedMovieList : LiveData<ArrayList<ListResultItem>>
        get() = _topRatedMovieList

    fun fetchPopularMovieList() {
        if(!popularMovieStatus) {
            viewModelScope.launch {
                val popularList = movieListRepository.getPopularMovieList()
                if (popularList != null) {
                    _popularMovieList.postValue(popularList.results as ArrayList<ListResultItem>?)
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
                    _playingMovieList.postValue(playingList.results as ArrayList<ListResultItem>?)
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
                    _upcomingMovieList.postValue(upcomingList.results as ArrayList<ListResultItem>?)
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
                    _topRatedMovieList.postValue(topRatedList.results as ArrayList<ListResultItem>?)
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