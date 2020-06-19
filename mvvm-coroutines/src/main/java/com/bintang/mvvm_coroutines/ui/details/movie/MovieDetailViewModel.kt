package com.bintang.mvvm_coroutines.ui.details.movie

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.entities.Movie
import com.bintang.mvvm_coroutines.base.LiveCoroutinesViewModel
import com.bintang.mvvm_coroutines.repository.MovieRepository
import timber.log.Timber

class MovieDetailViewModel constructor(
  private val movieRepository: MovieRepository
) : LiveCoroutinesViewModel() {

  private val movieIdLiveData: MutableLiveData<Int> = MutableLiveData()
  val keywordListLiveData: LiveData<List<Keyword>>
  val videoListLiveData: LiveData<List<Video>>
  val reviewListLiveData: LiveData<List<Review>>
  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  private lateinit var movie: Movie
  val favourite = ObservableBoolean()

  init {
    Timber.d("Injection MovieDetailViewModel")

    this.keywordListLiveData = movieIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadKeywordList(id) { toastLiveData.postValue(it) }
      }
    }

    this.videoListLiveData = movieIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadVideoList(id) { toastLiveData.postValue(it) }
      }
    }

    this.reviewListLiveData = movieIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadReviewsList(id) { toastLiveData.postValue(it) }
      }
    }
  }

  fun postMovieId(id: Int) {
    this.movieIdLiveData.postValue(id)
    this.movie = movieRepository.getMovie(id)
    this.favourite.set(this.movie.favourite)
  }

  fun getMovie() = this.movie

  fun onClickedFavourite(movie: Movie) =
    favourite.set(movieRepository.onClickFavourite(movie))
}
