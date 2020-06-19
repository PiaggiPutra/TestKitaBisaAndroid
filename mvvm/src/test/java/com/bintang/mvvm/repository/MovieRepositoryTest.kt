
package com.bintang.mvvm.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.bintang.common.ApiUtil.getCall
import com.bintang.common.MockTestUtils.Companion.mockKeywordList
import com.bintang.common.MockTestUtils.Companion.mockMovie
import com.bintang.common.MockTestUtils.Companion.mockReviewList
import com.bintang.common.MockTestUtils.Companion.mockVideoList
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.database.MovieDao
import com.bintang.entity.response.KeywordListResponse
import com.bintang.entity.response.ReviewListResponse
import com.bintang.entity.response.VideoListResponse
import com.bintang.network.ApiResponse
import com.bintang.network.client.MovieClient
import com.bintang.network.service.MovieService
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieRepositoryTest {

  private lateinit var repository: MovieRepository
  private lateinit var client: MovieClient
  private val service = mock<MovieService>()
  private val movieDao = mock<MovieDao>()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    client = MovieClient(service)
    repository = MovieRepository(client, movieDao)
  }

  @Test
  fun loadKeywordListFromNetworkTest() {
    val mockResponse = KeywordListResponse(1, emptyList())
    whenever(service.fetchKeywords(1)).thenReturn(getCall(mockResponse))
    whenever(movieDao.getMovie(1)).thenReturn(mockMovie())

    val data = repository.loadKeywordList(1) { }
    val observer = mock<Observer<List<Keyword>>>()
    data.observeForever(observer)
    verify(movieDao).getMovie(1)

    val loadFromDB = movieDao.getMovie(1)
    data.postValue(loadFromDB.keywords)
    verify(observer, times(2)).onChanged(loadFromDB.keywords)

    val updatedData = mockMovie(keywords = mockKeywordList())
    whenever(movieDao.getMovie(1)).thenReturn(updatedData)
    data.postValue(updatedData.keywords)
    verify(observer).onChanged(updatedData.keywords)

    client.fetchKeywords(1) {
      when (it) {
        is ApiResponse.Success -> {
          assertEquals(it.data, CoreMatchers.`is`(mockResponse))
          assertEquals(it.data?.keywords, CoreMatchers.`is`(updatedData.keywords))
        }
        else -> MatcherAssert.assertThat(it,
          CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Test
  fun loadVideoListFromNetworkTest() {
    val mockResponse = VideoListResponse(1, emptyList())
    whenever(service.fetchVideos(1)).thenReturn(getCall(mockResponse))
    whenever(movieDao.getMovie(1)).thenReturn(mockMovie())

    val data = repository.loadVideoList(1) { }
    val observer = mock<Observer<List<Video>>>()
    data.observeForever(observer)
    verify(movieDao).getMovie(1)

    val loadFromDB = movieDao.getMovie(1)
    data.postValue(loadFromDB.videos)
    verify(observer, times(2)).onChanged(loadFromDB.videos)

    val updatedData = mockMovie(videos = mockVideoList())
    whenever(movieDao.getMovie(1)).thenReturn(updatedData)
    data.postValue(updatedData.videos)
    verify(observer).onChanged(updatedData.videos)

    client.fetchVideos(1) {
      when (it) {
        is ApiResponse.Success -> {
          assertEquals(it.data, CoreMatchers.`is`(mockResponse))
          assertEquals(it.data?.results, CoreMatchers.`is`(updatedData.videos))
        }
        else -> MatcherAssert.assertThat(it,
          CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Test
  fun loadReviewListFromNetworkTest() {
    val mockResponse = ReviewListResponse(1, 0, emptyList(), 0, 0)
    whenever(service.fetchReviews(1)).thenReturn(getCall(mockResponse))
    whenever(movieDao.getMovie(1)).thenReturn(mockMovie())

    val data = repository.loadReviewsList(1) { }
    val observer = mock<Observer<List<Review>>>()
    data.observeForever(observer)
    verify(movieDao).getMovie(1)

    val loadFromDB = movieDao.getMovie(1)
    data.postValue(loadFromDB.reviews)
    verify(observer, times(2)).onChanged(loadFromDB.reviews)

    val updatedData = mockMovie(reviews = mockReviewList())
    whenever(movieDao.getMovie(1)).thenReturn(updatedData)
    data.postValue(updatedData.reviews)
    verify(observer).onChanged(updatedData.reviews)

    client.fetchReviews(1) {
      when (it) {
        is ApiResponse.Success -> {
          assertEquals(it.data, CoreMatchers.`is`(mockResponse))
          assertEquals(it.data?.results, CoreMatchers.`is`(updatedData.videos))
        }
        else -> MatcherAssert.assertThat(it,
          CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}
