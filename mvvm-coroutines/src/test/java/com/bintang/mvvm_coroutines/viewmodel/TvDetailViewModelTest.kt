package com.bintang.mvvm_coroutines.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.bintang.common.ApiUtil.getCall
import com.bintang.common.MockTestUtils.Companion.mockKeywordList
import com.bintang.common.MockTestUtils.Companion.mockReviewList
import com.bintang.common.MockTestUtils.Companion.mockTv
import com.bintang.common.MockTestUtils.Companion.mockVideoList
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.database.TvDao
import com.bintang.entity.response.KeywordListResponse
import com.bintang.entity.response.ReviewListResponse
import com.bintang.entity.response.VideoListResponse
import com.bintang.mvvm_coroutines.repository.TvRepository
import com.bintang.mvvm_coroutines.ui.details.tv.TvDetailViewModel
import com.bintang.network.client.TvClient
import com.bintang.network.service.TvService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvDetailViewModelTest {

  private lateinit var viewModel: TvDetailViewModel
  private lateinit var repository: TvRepository
  private val service = mock<TvService>()
  private val client = TvClient(service)
  private val tvDao = mock<TvDao>()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    this.repository = TvRepository(client, tvDao)
    this.viewModel = TvDetailViewModel(repository)
  }

  @Test
  fun loadKeywordListFromNetwork() = runBlocking {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(1)).thenReturn(loadFromDB)

    val mockResponse = KeywordListResponse(1, mockKeywordList())
    whenever(service.fetchKeywords(1)).thenReturn(getCall(mockResponse))

    val data = repository.loadKeywordList(1) { }
    val observer = mock<Observer<List<Keyword>>>()
    data.observeForever(observer)

    viewModel.postTvId(1)
    viewModel.postTvId(1)

    verify(tvDao, atLeastOnce()).getTv(1)
    verify(service, atLeastOnce()).fetchKeywords(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB.keywords)
    data.removeObserver(observer)
  }

  @Test
  fun loadVideoListFromNetwork() = runBlocking {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(1)).thenReturn(loadFromDB)

    val mockResponse = VideoListResponse(1, mockVideoList())
    whenever(service.fetchVideos(1)).thenReturn(getCall(mockResponse))

    val data = repository.loadVideoList(1) { }
    val observer = mock<Observer<List<Video>>>()
    data.observeForever(observer)

    viewModel.postTvId(1)
    viewModel.postTvId(1)

    verify(tvDao, atLeastOnce()).getTv(1)
    verify(service, atLeastOnce()).fetchVideos(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB.videos)
    data.removeObserver(observer)
  }

  @Test
  fun loadReviewsListFromNetwork() = runBlocking {
    val loadFromDB = mockTv()
    whenever(tvDao.getTv(1)).thenReturn(loadFromDB)

    val mockResponse = ReviewListResponse(1, 0, mockReviewList(), 0, 0)
    whenever(service.fetchReviews(1)).thenReturn(getCall(mockResponse))

    val data = repository.loadReviewsList(1) { }
    val observer = mock<Observer<List<Review>>>()
    data.observeForever(observer)

    viewModel.postTvId(1)
    viewModel.postTvId(1)

    verify(tvDao, atLeastOnce()).getTv(1)
    verify(service, atLeastOnce()).fetchReviews(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB.reviews)
    data.removeObserver(observer)
  }
}
