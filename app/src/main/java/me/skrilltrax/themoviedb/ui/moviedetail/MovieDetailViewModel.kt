package me.skrilltrax.themoviedb.ui.moviedetail

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
import me.skrilltrax.themoviedb.model.detail.movie.MovieDetailResponse
import me.skrilltrax.themoviedb.model.list.movie.MovieListResponse
import me.skrilltrax.themoviedb.model.list.movie.MovieListResultItem
import me.skrilltrax.themoviedb.model.videos.VideoResponse
import me.skrilltrax.themoviedb.model.videos.VideoResultsItem
import me.skrilltrax.themoviedb.network.api.movie.MovieDetailRepository

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModel @ViewModelInject constructor(private val movieDetailRepository: MovieDetailRepository) :
    ViewModel() {

    private val _movieDetail: MutableLiveData<MovieDetailResponse> = MutableLiveData()
    private val _genres: MutableLiveData<List<GenresItem>> = MutableLiveData(listOf())
    private val _cast: MutableLiveData<List<CastItem>> = MutableLiveData(listOf())
    private val _crew: MutableLiveData<List<CrewItem>> = MutableLiveData(listOf())
    private val _videos: MutableLiveData<List<VideoResultsItem>> = MutableLiveData(listOf())
    private val _trailers: MutableLiveData<List<VideoResultsItem>> = MutableLiveData(listOf())
    private val _extraVideos: MutableLiveData<List<VideoResultsItem>> = MutableLiveData(listOf())
    private val _recommendations: MutableLiveData<List<MovieListResultItem>> =
        MutableLiveData(listOf())

    val movieId: MutableLiveData<String> = MutableLiveData("")
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val genres: LiveData<List<GenresItem>>
        get() = _genres

    val cast: LiveData<List<CastItem>>
        get() = _cast

    val crew: LiveData<List<CrewItem>>
        get() = _crew

    val movieDetail: LiveData<MovieDetailResponse>
        get() = _movieDetail

    val videos: LiveData<List<VideoResultsItem>>
        get() = _videos

    val trailers: LiveData<List<VideoResultsItem>>
        get() = _trailers

    val extraVideos: LiveData<List<VideoResultsItem>>
        get() = _extraVideos

    val recommendations: LiveData<List<MovieListResultItem>>
        get() = _recommendations

    fun fetchMovieDetailsWithExtras() {
        viewModelScope.launch {
            val movieDetailExtraData = movieDetailRepository.getMovieDetailsWithExtras(movieId.value!!)
            // TODO: Handle case when no movie detail is returned.
            updateMovieDetails(movieDetailExtraData)
            updateGenres(movieDetailExtraData?.genres)
            updateVideos(movieDetailExtraData?.videos)
            updateCredits(movieDetailExtraData?.credits)
            updateRecommendations(movieDetailExtraData?.recommendations)
            isLoading.postValue(false)
        }
    }

    fun fetchMovieDetails() {
        viewModelScope.launch {
            val movieDetailData = movieDetailRepository.getMovieDetails(movieId.value!!)
            // TODO: Handle case when no movie detail is returned.
            updateMovieDetails(movieDetailData)
            updateGenres(movieDetailData?.genres)
        }
    }

    private fun updateMovieDetails(movieDetailData: MovieDetailResponse?) {
        if (movieDetailData is MovieDetailResponse) {
            _movieDetail.postValue(movieDetailData)
        }
    }

    private fun updateGenres(genres: List<GenresItem>?) {
        if (genres is List<GenresItem>) {
            _genres.postValue(genres as List<GenresItem>)
        }
    }

    fun fetchCastAndCrew() {
        viewModelScope.launch {
            val credits = movieDetailRepository.getCastCrew(movieId.value!!)
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
            val videos = movieDetailRepository.getVideos(movieId.value!!)
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
            val recommendations = movieDetailRepository.getRecommendations(movieId.value!!)
            updateRecommendations(recommendations)
        }
    }

    private fun updateRecommendations(recommendations: MovieListResponse?) {
        if (recommendations is MovieListResponse) {
            _recommendations.postValue(recommendations.results as List<MovieListResultItem>)
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
