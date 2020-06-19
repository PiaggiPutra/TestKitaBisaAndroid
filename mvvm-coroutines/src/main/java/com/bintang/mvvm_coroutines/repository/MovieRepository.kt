package com.bintang.mvvm_coroutines.repository

import androidx.lifecycle.MutableLiveData
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.database.MovieDao
import com.bintang.entity.entities.Movie
import com.bintang.network.ApiResponse
import com.bintang.network.client.MovieClient
import com.bintang.network.message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieRepository constructor(
  private val movieClient: MovieClient,
  private val movieDao: MovieDao
) : Repository {

  override var isLoading: Boolean = false

  init {
    Timber.d("Injection MovieRepository")
  }

  suspend fun loadKeywordList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Keyword>>()
    val movie = movieDao.getMovie(id)
    var keywords = movie.keywords
    if (keywords.isNullOrEmpty()) {
      isLoading = true
      movieClient.fetchKeywords(id) { response ->
        isLoading = false
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
    liveData.apply { postValue(keywords) }
  }

  suspend fun loadVideoList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Video>>()
    val movie = movieDao.getMovie(id)
    var videos = movie.videos
    if (videos.isNullOrEmpty()) {
      isLoading = true
      movieClient.fetchVideos(id) { response ->
        isLoading = false
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
    liveData.apply { postValue(videos) }
  }

  suspend fun loadReviewsList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Review>>()
    val movie = movieDao.getMovie(id)
    var reviews = movie.reviews
    if (reviews.isNullOrEmpty()) {
      isLoading = true
      movieClient.fetchReviews(id) { response ->
        isLoading = false
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
    liveData.apply { postValue(reviews) }
  }

  fun getMovie(id: Int) = movieDao.getMovie(id)

  fun onClickFavourite(movie: Movie): Boolean {
    movie.favourite = !movie.favourite
    movieDao.updateMovie(movie)
    return movie.favourite
  }
}
