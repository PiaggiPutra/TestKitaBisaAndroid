package com.bintang.mvvm_coroutines.repository

import androidx.lifecycle.MutableLiveData
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.database.TvDao
import com.bintang.entity.entities.Tv
import com.bintang.network.ApiResponse
import com.bintang.network.client.TvClient
import com.bintang.network.message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class TvRepository constructor(
  private val tvClient: TvClient,
  private val tvDao: TvDao
) : Repository {

  override var isLoading: Boolean = false

  init {
    Timber.d("Injection TvRepository")
  }

  suspend fun loadKeywordList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Keyword>>()
    val tv = tvDao.getTv(id)
    var keywords = tv.keywords
    if (keywords.isNullOrEmpty()) {
      isLoading = true
      tvClient.fetchKeywords(id) { response ->
        isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              keywords = data.keywords
              tv.keywords = keywords
              liveData.postValue(keywords)
              tvDao.updateTv(tv)
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
    val tv = tvDao.getTv(id)
    var videos = tv.videos
    if (videos.isNullOrEmpty()) {
      isLoading = true
      tvClient.fetchVideos(id) { response ->
        isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              videos = data.results
              tv.videos = videos
              liveData.postValue(videos)
              tvDao.updateTv(tv)
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
    val tv = tvDao.getTv(id)
    var reviews = tv.reviews
    if (reviews.isNullOrEmpty()) {
      isLoading = true
      tvClient.fetchReviews(id) { response ->
        isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              reviews = data.results
              tv.reviews = reviews
              liveData.postValue(reviews)
              tvDao.updateTv(tv)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveData.apply { postValue(reviews) }
  }

  fun getTv(id: Int) = tvDao.getTv(id)

  fun onClickFavourite(tv: Tv): Boolean {
    tv.favourite = !tv.favourite
    tvDao.updateTv(tv)
    return tv.favourite
  }
}
