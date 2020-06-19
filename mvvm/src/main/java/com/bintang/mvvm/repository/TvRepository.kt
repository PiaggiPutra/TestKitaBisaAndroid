package com.bintang.mvvm.repository

import androidx.lifecycle.MutableLiveData
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.database.TvDao
import com.bintang.entity.entities.Tv
import com.bintang.network.ApiResponse
import com.bintang.network.client.TvClient
import com.bintang.network.message
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class TvRepository @Inject constructor(
  private val tvClient: TvClient,
  private val tvDao: TvDao
) : Repository {

  override var isLoading: Boolean = false

  init {
    Timber.d("Injection TvRepository")
  }

  fun loadKeywordList(id: Int, error: (String) -> Unit): MutableLiveData<List<Keyword>> {
    val liveData = MutableLiveData<List<Keyword>>()
    val tv = tvDao.getTv(id)
    var keywords = tv.keywords
    if (keywords.isNullOrEmpty()) {
      this.isLoading = true
      tvClient.fetchKeywords(id) { response ->
        this.isLoading = false
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
    liveData.postValue(keywords)
    return liveData
  }

  fun loadVideoList(id: Int, error: (String) -> Unit): MutableLiveData<List<Video>> {
    val liveData = MutableLiveData<List<Video>>()
    val tv = tvDao.getTv(id)
    var videos = tv.videos
    if (videos.isNullOrEmpty()) {
      this.isLoading = true
      tvClient.fetchVideos(id) { response ->
        this.isLoading = false
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
    liveData.postValue(videos)
    return liveData
  }

  fun loadReviewsList(id: Int, error: (String) -> Unit): MutableLiveData<List<Review>> {
    val liveData = MutableLiveData<List<Review>>()
    val tv = tvDao.getTv(id)
    var reviews = tv.reviews
    if (reviews.isNullOrEmpty()) {
      this.isLoading = true
      tvClient.fetchReviews(id) { response ->
        this.isLoading = false
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
    liveData.postValue(reviews)
    return liveData
  }

  fun getTv(id: Int) = tvDao.getTv(id)

  fun onClickFavourite(tv: Tv): Boolean {
    tv.favourite = !tv.favourite
    tvDao.updateTv(tv)
    return tv.favourite
  }
}
