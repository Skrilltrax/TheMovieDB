package me.skrilltrax.themoviedb.ui.homepage.tv

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.model.tv.list.TVResultsItem
import me.skrilltrax.themoviedb.network.api.tv.TVListRepository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class TVListViewModel(private val tvListRepository: TVListRepository) : ViewModel() {

    private var popularShowsStatus : Boolean = false
    private var playingShowsStatus : Boolean = false
    private var upcomingShowsStatus : Boolean = false
    private var topRatedShowsStatus : Boolean = false

    private val _popularShowsList : MutableLiveData<ArrayList<TVResultsItem>> = MutableLiveData()
    private val _playingShowsList : MutableLiveData<ArrayList<TVResultsItem>> = MutableLiveData()
    private val _upcomingShowsList : MutableLiveData<ArrayList<TVResultsItem>> = MutableLiveData()
    private val _topRatedShowsList : MutableLiveData<ArrayList<TVResultsItem>> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val popularTVList : LiveData<ArrayList<TVResultsItem>>
        get() = _popularShowsList

    val playingTVList : LiveData<ArrayList<TVResultsItem>>
        get() = _playingShowsList

    val upcomingTVList : LiveData<ArrayList<TVResultsItem>>
        get() = _upcomingShowsList

    val topRatedTVList : LiveData<ArrayList<TVResultsItem>>
        get() = _topRatedShowsList

    fun fetchPopularTVList() {
        viewModelScope.launch {
            val popularList = tvListRepository.getPopularShowsList()
            if (popularList != null) {
                _popularShowsList.postValue(popularList.results as ArrayList<TVResultsItem>?)
                popularShowsStatus = true
                checkStatus()
            }
        }
    }

    fun fetchPlayingTVList() {
        viewModelScope.launch {
            val playingList = tvListRepository.getOnAirShowsList()
            if (playingList != null) {
                _playingShowsList.postValue(playingList.results as ArrayList<TVResultsItem>?)
                playingShowsStatus = true
                checkStatus()
            }
        }
    }

    fun fetchUpcomingTVList() {
        viewModelScope.launch {
            val upcomingList = tvListRepository.getAiringShowsList()
            if (upcomingList != null) {
                _upcomingShowsList.postValue(upcomingList.results as ArrayList<TVResultsItem>?)
                upcomingShowsStatus = true
                checkStatus()
            }
        }
    }

    fun fetchTopRatedTVList() {
        viewModelScope.launch {
            val topRatedList = tvListRepository.getTopRatedShowsList()
            if (topRatedList != null) {
                _topRatedShowsList.postValue(topRatedList.results as ArrayList<TVResultsItem>?)
                topRatedShowsStatus = true
                checkStatus()
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