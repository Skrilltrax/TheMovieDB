package me.skrilltrax.themoviedb.ui.tvdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.model.credits.CastItem
import me.skrilltrax.themoviedb.model.credits.CrewItem
import me.skrilltrax.themoviedb.model.detail.GenresItem
import me.skrilltrax.themoviedb.model.detail.tv.TVDetailResponse
import me.skrilltrax.themoviedb.model.list.tv.TVListResultItem
import me.skrilltrax.themoviedb.model.videos.VideoResultsItem
import me.skrilltrax.themoviedb.network.api.tv.TVDetailRepository
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class TVDetailViewModel(private val tvDetailRepository: TVDetailRepository) : ViewModel() {

    private var tvDetailStatus: Boolean = false
    private var crewStatus: Boolean = false
    private var castStatus: Boolean = false

    private val _tvDetail: MutableLiveData<TVDetailResponse> = MutableLiveData()
    private val _genres: MutableLiveData<List<GenresItem>> = MutableLiveData(listOf())
    private val _cast: MutableLiveData<List<CastItem>> = MutableLiveData(listOf())
    private val _crew: MutableLiveData<List<CrewItem>> = MutableLiveData(listOf())
    private val _videos: MutableLiveData<List<VideoResultsItem>> = MutableLiveData(listOf())
    private val _trailers: MutableLiveData<List<VideoResultsItem>> = MutableLiveData(listOf())
    private val _extraVideos: MutableLiveData<List<VideoResultsItem>> = MutableLiveData(listOf())
    private val _recommendations: MutableLiveData<List<TVListResultItem>> = MutableLiveData(listOf())

    val showId: MutableLiveData<String> = MutableLiveData("")
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val genres: LiveData<List<GenresItem>>
        get() = _genres

    val cast: LiveData<List<CastItem>>
        get() = _cast

    val crew: LiveData<List<CrewItem>>
        get() = _crew

    val tvDetail: LiveData<TVDetailResponse>
        get() = _tvDetail

    val videos: LiveData<List<VideoResultsItem>>
        get() = _videos

    val trailers: LiveData<List<VideoResultsItem>>
        get() = _trailers

    val extraVideos: LiveData<List<VideoResultsItem>>
        get() = _extraVideos

    val recommendations: LiveData<List<TVListResultItem>>
        get() = _recommendations

    @Suppress("UNCHECKED_CAST")
    fun fetchShowDetails() {
        viewModelScope.launch {
            val tvDetailData = tvDetailRepository.getShowDetails(showId.value!!)
            if (tvDetailData != null) {
                _tvDetail.postValue(tvDetailData)
                if (tvDetailData.genres != null)
                _genres.postValue(tvDetailData.genres as List<GenresItem>)
                tvDetailStatus = true
                checkStatus()
            }
        }
    }

    fun fetchCastAndCrew() {
        viewModelScope.launch {
            val movieCredits = tvDetailRepository.getCastCrew(showId.value!!)
            if (movieCredits != null) {
                _cast.postValue(movieCredits.cast as List<CastItem>)
                castStatus = true
                checkStatus()
                _crew.postValue(movieCredits.crew as List<CrewItem>)
                crewStatus = true
                checkStatus()
            }
        }
    }

    fun fetchVideos() {
        viewModelScope.launch {
            val movieVideos = tvDetailRepository.getVideos(showId.value!!)
            if (movieVideos != null) {
                Timber.d("videos : ${movieVideos.results?.size}}")
                _videos.postValue(movieVideos.results as List<VideoResultsItem>)
                checkStatus()
                sortVideos()
            }
        }
    }

    fun fetchRecommendations() {
        viewModelScope.launch {
            val recommendedVideos = tvDetailRepository.getRecommendations(showId.value!!)
            if (null != recommendedVideos) {
                _recommendations.postValue(recommendedVideos.results as List<TVListResultItem>)
            }
        }
    }

    private fun sortVideos() {
        val trailerList: ArrayList<VideoResultsItem> = arrayListOf()
        val extraList: ArrayList<VideoResultsItem> = arrayListOf()
        if (videos.value != null) {
            for (video in videos.value!!) {
                if (video.type?.contains("trailer", true)!!) {
                    trailerList.add(video)
                } else {
                    extraList.add(video)
                }
            }
        }
        _trailers.postValue(trailerList)
        _extraVideos.postValue(extraList)
    }

    private fun checkStatus() {
        if (tvDetailStatus && castStatus && crewStatus) {
            isLoading.postValue(false)
        }
    }
}
