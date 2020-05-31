package me.skrilltrax.themoviedb.ui.homepage.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.model.list.tv.TVListResultItem
import me.skrilltrax.themoviedb.network.api.tv.TVListRepository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class TVListViewModel(private val tvListRepository: TVListRepository) : ViewModel() {

    private var popularShowsStatus: Boolean = false
    private var playingShowsStatus: Boolean = false
    private var upcomingShowsStatus: Boolean = false
    private var topRatedShowsStatus: Boolean = false

    private val _popularShowsList: MutableLiveData<ArrayList<TVListResultItem>> = MutableLiveData()
    private val _playingShowsList: MutableLiveData<ArrayList<TVListResultItem>> = MutableLiveData()
    private val _upcomingShowsList: MutableLiveData<ArrayList<TVListResultItem>> = MutableLiveData()
    private val _topRatedShowsList: MutableLiveData<ArrayList<TVListResultItem>> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val popularShowsList: LiveData<ArrayList<TVListResultItem>>
        get() = _popularShowsList

    val playingShowsList: LiveData<ArrayList<TVListResultItem>>
        get() = _playingShowsList

    val upcomingShowsList: LiveData<ArrayList<TVListResultItem>>
        get() = _upcomingShowsList

    val topRatedShowsList: LiveData<ArrayList<TVListResultItem>>
        get() = _topRatedShowsList

    init {
        fetchPopularShowsList()
        fetchPlayingShowsList()
        fetchUpcomingShowsList()
        fetchTopRatedShowsList()
    }

    fun fetchPopularShowsList() {
        if (!popularShowsStatus) {
            viewModelScope.launch {
                val popularList = tvListRepository.getPopularShowsList()
                if (popularList != null) {
                    _popularShowsList.postValue(popularList.results as ArrayList<TVListResultItem>?)
                    popularShowsStatus = true
                    checkStatus()
                }
            }
        }
    }

    fun fetchPlayingShowsList() {
        if (!playingShowsStatus) {
            viewModelScope.launch {
                val playingList = tvListRepository.getOnAirShowsList()
                if (playingList != null) {
                    _playingShowsList.postValue(playingList.results as ArrayList<TVListResultItem>?)
                    playingShowsStatus = true
                    checkStatus()
                }
            }
        }
    }

    fun fetchUpcomingShowsList() {
        if (!upcomingShowsStatus) {
            viewModelScope.launch {
                val upcomingList = tvListRepository.getAiringShowsList()
                if (upcomingList != null) {
                    _upcomingShowsList.postValue(upcomingList.results as ArrayList<TVListResultItem>?)
                    upcomingShowsStatus = true
                    checkStatus()
                }
            }
        }
    }

    fun fetchTopRatedShowsList() {
        if (!topRatedShowsStatus) {
            viewModelScope.launch {
                val topRatedList = tvListRepository.getTopRatedShowsList()
                if (topRatedList != null) {
                    _topRatedShowsList.postValue(topRatedList.results as ArrayList<TVListResultItem>?)
                    topRatedShowsStatus = true
                    checkStatus()
                }
            }
        }
    }

    private fun checkStatus() {
        if (popularShowsStatus && upcomingShowsStatus && playingShowsStatus && topRatedShowsStatus) {
            isLoading.postValue(false)
            Timber.d("set isLoading false")
        }
    }
}
