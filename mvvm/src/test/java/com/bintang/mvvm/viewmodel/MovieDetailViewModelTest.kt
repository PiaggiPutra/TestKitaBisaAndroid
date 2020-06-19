
package com.bintang.mvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
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
import com.bintang.mvvm.repository.MovieRepository
import com.bintang.mvvm.ui.details.movie.MovieDetailViewModel
import com.bintang.network.client.MovieClient
import com.bintang.network.service.MovieService
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailViewModelTest {

  private lateinit var viewModel: MovieDetailViewModel
  private lateinit var repository: MovieRepository
  private val service = mock<MovieService>()
  private val client = MovieClient(service)
  private val movieDao = mock<MovieDao>()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    this.repository = MovieRepository(client, movieDao)
    this.viewModel = MovieDetailViewModel(repository)
  }

  @Test
  fun loadKeywordListFromNetwork() {
    val loadFromDB = mockMovie()
    whenever(movieDao.getMovie(1)).thenReturn(loadFromDB)

    val mockResponse = KeywordListResponse(1, mockKeywordList())
    whenever(service.fetchKeywords(1)).thenReturn(getCall(mockResponse))

    val data = viewModel.keywordListLiveData
    val observer = mock<Observer<List<Keyword>>>()
    data.observeForever(observer)

    viewModel.postMovieId(1)
    viewModel.postMovieId(1)

    verify(movieDao, atLeastOnce()).getMovie(1)
    verify(service, atLeastOnce()).fetchKeywords(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB.keywords)
    data.removeObserver(observer)
  }

  @Test
  fun loadVideoListFromNetwork() {
    val loadFromDB = mockMovie()
    whenever(movieDao.getMovie(1)).thenReturn(loadFromDB)

    val mockResponse = VideoListResponse(1, mockVideoList())
    whenever(service.fetchVideos(1)).thenReturn(getCall(mockResponse))

    val data = viewModel.videoListLiveData
    val observer = mock<Observer<List<Video>>>()
    data.observeForever(observer)

    viewModel.postMovieId(1)
    viewModel.postMovieId(1)

    verify(movieDao, atLeastOnce()).getMovie(1)
    verify(service, atLeastOnce()).fetchVideos(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB.videos)
    data.removeObserver(observer)
  }

  @Test
  fun loadReviewsListFromNetwork() {
    val loadFromDB = mockMovie()
    whenever(movieDao.getMovie(1)).thenReturn(loadFromDB)

    val mockResponse = ReviewListResponse(1, 0, mockReviewList(), 0, 0)
    whenever(service.fetchReviews(1)).thenReturn(getCall(mockResponse))

    val data = viewModel.reviewListLiveData
    val observer = mock<Observer<List<Review>>>()
    data.observeForever(observer)

    viewModel.postMovieId(1)
    viewModel.postMovieId(1)

    verify(movieDao, atLeastOnce()).getMovie(1)
    verify(service, atLeastOnce()).fetchReviews(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB.reviews)
    data.removeObserver(observer)
  }
}
