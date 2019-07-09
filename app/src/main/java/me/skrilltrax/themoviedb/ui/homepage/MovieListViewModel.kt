package me.skrilltrax.themoviedb.ui.homepage

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem
import me.skrilltrax.themoviedb.network.api.movie.MovieListRepository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class MovieListViewModel : ViewModel() {

    private var popularMovieStatus : Boolean = false
    private var playingMovieStatus : Boolean = false
    private var upcomingMovieStatus : Boolean = false
    private var topRatedMovieStatus : Boolean = false

    private val movieListRepository: MovieListRepository = MovieListRepository()
    private val _popularMovieList : MutableLiveData<ArrayList<MovieResultsItem>> = MutableLiveData()
    private val _playingMovieList : MutableLiveData<ArrayList<MovieResultsItem>> = MutableLiveData()
    private val _upcomingMovieList : MutableLiveData<ArrayList<MovieResultsItem>> = MutableLiveData()
    private val _topRatedMovieList : MutableLiveData<ArrayList<MovieResultsItem>> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val popularMovieList : LiveData<ArrayList<MovieResultsItem>>
        get() = _popularMovieList

    val playingMovieList : LiveData<ArrayList<MovieResultsItem>>
        get() = _playingMovieList

    val upcomingMovieList : LiveData<ArrayList<MovieResultsItem>>
        get() = _upcomingMovieList

    val topRatedMovieList : LiveData<ArrayList<MovieResultsItem>>
        get() = _topRatedMovieList

    fun fetchPopularMovieList() {
        viewModelScope.launch {
            val popularList = movieListRepository.getPopularMovieList()
            if (popularList != null) {
                _popularMovieList.postValue(popularList.results as ArrayList<MovieResultsItem>?)
                popularMovieStatus = true
                checkStatus()
            }
        }
    }

    fun fetchPlayingMovieList() {
        viewModelScope.launch {
            val playingList = movieListRepository.getPlayingMovieList()
            if (playingList != null) {
                _playingMovieList.postValue(playingList.results as ArrayList<MovieResultsItem>?)
                playingMovieStatus = true
                checkStatus()
            }
        }
    }

    fun fetchUpcomingMovieList() {
        viewModelScope.launch {
            val upcomingList = movieListRepository.getUpcomingMovieList()
            if (upcomingList != null) {
                _upcomingMovieList.postValue(upcomingList.results as ArrayList<MovieResultsItem>?)
                upcomingMovieStatus = true
                checkStatus()
            }
        }
    }

    fun fetchTopRatedMovieList() {
        viewModelScope.launch {
            val topRatedList = movieListRepository.getTopRatedMovieList()
            if (topRatedList != null) {
                _topRatedMovieList.postValue(topRatedList.results as ArrayList<MovieResultsItem>?)
                topRatedMovieStatus = true
                checkStatus()
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