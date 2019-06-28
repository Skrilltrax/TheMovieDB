package me.skrilltrax.themoviedb.ui.moviedetail

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.model.movie.credits.CastItem
import me.skrilltrax.themoviedb.model.movie.credits.CrewItem
import me.skrilltrax.themoviedb.model.movie.detail.GenresItem
import me.skrilltrax.themoviedb.model.movie.detail.MovieDetailResponse
import me.skrilltrax.themoviedb.network.api.movie.MovieDetailRepository

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private var movieDetailStatus: Boolean = false
    private var crewStatus: Boolean = false
    private var castStatus: Boolean = false

    private val movieDetailRepository: MovieDetailRepository = MovieDetailRepository()
    private val _movieDetail: MutableLiveData<MovieDetailResponse> = MutableLiveData()
    private val _genres: MutableLiveData<List<GenresItem>> = MutableLiveData(listOf())
    private val _cast: MutableLiveData<List<CastItem>> = MutableLiveData(listOf())
    private val _crew: MutableLiveData<List<CrewItem>> = MutableLiveData(listOf())

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


    @Suppress("UNCHECKED_CAST")
    fun fetchMovieDetails() {
        viewModelScope.launch {
            val movieDetailData = movieDetailRepository.getMovieDetails(movieId.value!!)
            if (movieDetailData != null) {
                _movieDetail.postValue(movieDetailData)
                if (movieDetailData.genres != null)
                _genres.postValue(movieDetailData.genres as List<GenresItem>)
                movieDetailStatus = true
                checkStatus()
            }
        }
    }

    fun fetchCastAndCrew() {
        viewModelScope.launch {
            val movieCredits = movieDetailRepository.getCastCrew(movieId.value!!)
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

    private fun checkStatus() {
        if (movieDetailStatus && castStatus && crewStatus) {
            isLoading.postValue(false)
        }
    }
}