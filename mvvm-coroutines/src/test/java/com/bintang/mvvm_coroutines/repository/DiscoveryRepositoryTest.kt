package com.bintang.mvvm_coroutines.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.bintang.common.ApiUtil.getCall
import com.bintang.common.MockTestUtils.Companion.mockMovieList
import com.bintang.common.MockTestUtils.Companion.mockTvList
import com.bintang.entity.database.MovieDao
import com.bintang.entity.database.TvDao
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Tv
import com.bintang.entity.response.DiscoverMovieResponse
import com.bintang.entity.response.DiscoverTvResponse
import com.bintang.network.ApiResponse
import com.bintang.network.client.TheDiscoverClient
import com.bintang.network.service.TheDiscoverService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DiscoveryRepositoryTest {

  private lateinit var repository: DiscoverRepository
  private lateinit var client: TheDiscoverClient
  private val service = mock<TheDiscoverService>()
  private val movieDao = mock<MovieDao>()
  private val tvDao = mock<TvDao>()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    client = TheDiscoverClient(service)
    repository = DiscoverRepository(client, movieDao, tvDao)
  }

  @Test
  fun loadMovieListFromNetworkTest() = runBlocking {
    val loadFromDB = movieDao.getMovieList(1)
    whenever(movieDao.getMovieList(1)).thenReturn(loadFromDB)

    val mockResponse = DiscoverMovieResponse(1, emptyList(), 100, 10)
    whenever(service.fetchDiscoverMovie(1)).thenReturn(getCall(mockResponse))

    val data = repository.loadMovies(1) { }
    verify(movieDao, times(2)).getMovieList(1)

    val observer = mock<Observer<List<Movie>>>()
    data.observeForever(observer)
    val updatedData = mockMovieList()
    whenever(movieDao.getMovieList(1)).thenReturn(updatedData)
    data.postValue(updatedData)
    verify(observer).onChanged(updatedData)

    client.fetchDiscoverMovie(1) {
      when (it) {
        is ApiResponse.Success -> {
          assertEquals(it.data, `is`(mockResponse))
          assertEquals(it.data?.results, `is`(updatedData))
        }
        else -> assertThat(it, instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Test
  fun loadTvListFromNetworkTest() = runBlocking {
    val loadFromDB = tvDao.getTvList(1)
    whenever(tvDao.getTvList(1)).thenReturn(loadFromDB)

    val mockResponse = DiscoverTvResponse(1, emptyList(), 100, 10)
    whenever(service.fetchDiscoverTv(1)).thenReturn(getCall(mockResponse))

    val data = repository.loadTvs(1) { }
    verify(tvDao, times(2)).getTvList(1)

    val observer = mock<Observer<List<Tv>>>()
    data.observeForever(observer)
    val updatedData = mockTvList()
    whenever(tvDao.getTvList(1)).thenReturn(updatedData)
    data.postValue(updatedData)
    verify(observer).onChanged(updatedData)

    client.fetchDiscoverTv(1) {
      when (it) {
        is ApiResponse.Success -> {
          assertEquals(it.data, `is`(mockResponse))
          assertEquals(it.data?.results, `is`(updatedData))
        }
        else -> assertThat(it, instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}
