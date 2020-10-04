package me.skrilltrax.themoviedb.ui.tvdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.model.credits.CastItem
import me.skrilltrax.themoviedb.model.credits.CreditsResponse
import me.skrilltrax.themoviedb.model.credits.CrewItem
import me.skrilltrax.themoviedb.model.detail.GenresItem
import me.skrilltrax.themoviedb.model.detail.tv.TVDetailResponse
import me.skrilltrax.themoviedb.model.list.tv.TVListResponse
import me.skrilltrax.themoviedb.model.list.tv.TVListResultItem
import me.skrilltrax.themoviedb.model.videos.VideoResponse
import me.skrilltrax.themoviedb.model.videos.VideoResultsItem
import me.skrilltrax.themoviedb.network.api.tv.TVDetailRepository

@Suppress("UNCHECKED_CAST")
class TVDetailViewModel @ViewModelInject constructor(private val tvDetailRepository: TVDetailRepository) : ViewModel() {

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

    fun fetchShowDetailsWithExtras() {
        viewModelScope.launch {
            val tvDetailExtraData = tvDetailRepository.getShowDetailsWithExtras(showId.value!!)
            // TODO: Handle case when no movie detail is returned.
            updateMovieDetails(tvDetailExtraData)
            updateGenres(tvDetailExtraData?.genres)
            updateVideos(tvDetailExtraData?.videos)
            updateCredits(tvDetailExtraData?.credits)
            updateRecommendations(tvDetailExtraData?.recommendations)
            isLoading.postValue(false)
        }
    }

    fun fetchShowDetails() {
        viewModelScope.launch {
            val tvDetailData = tvDetailRepository.getShowDetails(showId.value!!)
            // TODO: Handle case when no movie detail is returned.
            updateMovieDetails(tvDetailData)
            updateGenres(tvDetailData?.genres)
        }
    }

    private fun updateMovieDetails(tvDetailData: TVDetailResponse?) {
        if (tvDetailData is TVDetailResponse) {
            _tvDetail.postValue(tvDetailData)
        }
    }

    private fun updateGenres(genres: List<GenresItem>?) {
        if (genres is List<GenresItem>) {
            _genres.postValue(genres)
        }
    }

    fun fetchCastAndCrew() {
        viewModelScope.launch {
            val credits = tvDetailRepository.getCastCrew(showId.value!!)
            updateCredits(credits)
        }
    }

    private fun updateCredits(credits: CreditsResponse?) {
        if (credits is CreditsResponse) {
            _cast.postValue(credits.cast as List<CastItem>)
            _crew.postValue(credits.crew as List<CrewItem>)
        }
    }

    fun fetchVideos() {
        viewModelScope.launch {
            val videos = tvDetailRepository.getVideos(showId.value!!)
            updateVideos(videos)
        }
    }

    private fun updateVideos(videos: VideoResponse?) {
        if (videos is VideoResponse) {
            _videos.postValue(videos.results as List<VideoResultsItem>)
            sortVideos()
        }
    }

    fun fetchRecommendations() {
        viewModelScope.launch {
            val recommendations = tvDetailRepository.getRecommendations(showId.value!!)
            updateRecommendations(recommendations)
        }
    }

    private fun updateRecommendations(recommendations: TVListResponse?) {
        if (recommendations is TVListResponse) {
            _recommendations.postValue(recommendations.results as List<TVListResultItem>)
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
}
