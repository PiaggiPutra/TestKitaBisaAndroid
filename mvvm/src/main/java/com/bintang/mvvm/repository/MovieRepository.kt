package com.bintang.mvvm.repository

import androidx.lifecycle.MutableLiveData
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.database.MovieDao
import com.bintang.entity.entities.Movie
import com.bintang.network.ApiResponse
import com.bintang.network.client.MovieClient
import com.bintang.network.message
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class MovieRepository @Inject constructor(
  private val movieClient: MovieClient,
  private val movieDao: MovieDao
) : Repository {

  override var isLoading: Boolean = false

  init {
    Timber.d("Injection MovieRepository")
  }

  fun loadKeywordList(id: Int, error: (String) -> Unit): MutableLiveData<List<Keyword>> {
    val liveData = MutableLiveData<List<Keyword>>()
    val movie = movieDao.getMovie(id)
    var keywords = movie.keywords
    if (keywords.isNullOrEmpty()) {
      this.isLoading = true
      movieClient.fetchKeywords(id) { response ->
        this.isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              keywords = data.keywords
              movie.keywords = keywords
              liveData.postValue(keywords)
              movieDao.updateMovie(movie)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveData.postValue(keywords)
    return liveData
  }

  fun loadVideoList(id: Int, error: (String) -> Unit): MutableLiveData<List<Video>> {
    val liveData = MutableLiveData<List<Video>>()
    val movie = movieDao.getMovie(id)
    var videos = movie.videos
    if (videos.isNullOrEmpty()) {
      this.isLoading = true
      movieClient.fetchVideos(id) { response ->
        this.isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              videos = data.results
              movie.videos = videos
              liveData.postValue(videos)
              movieDao.updateMovie(movie)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveData.postValue(videos)
    return liveData
  }

  fun loadReviewsList(id: Int, error: (String) -> Unit): MutableLiveData<List<Review>> {
    val liveData = MutableLiveData<List<Review>>()
    val movie = movieDao.getMovie(id)
    var reviews = movie.reviews
    if (reviews.isNullOrEmpty()) {
      this.isLoading = true
      movieClient.fetchReviews(id) { response ->
        this.isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              reviews = data.results
              movie.reviews = reviews
              liveData.postValue(reviews)
              movieDao.updateMovie(movie)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveData.postValue(reviews)
    return liveData
  }

  fun getMovie(id: Int) = movieDao.getMovie(id)

  fun onClickFavourite(movie: Movie): Boolean {
    movie.favourite = !movie.favourite
    movieDao.updateMovie(movie)
    return movie.favourite
  }
}
