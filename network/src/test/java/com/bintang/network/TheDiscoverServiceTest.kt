package com.bintang.network

import com.bintang.network.service.TheDiscoverService
import java.io.IOException
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class TheDiscoverServiceTest : ApiAbstract<TheDiscoverService>() {

  private lateinit var service: TheDiscoverService

  @Before
  fun initService() {
    this.service = createService(TheDiscoverService::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieListTest() {
    enqueueResponse("/tmdb_movie.json")
    this.service.fetchDiscoverMovie(1).transform {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.results?.get(0)?.id, `is`(164558))
          assertThat(it.data?.total_results, `is`(61))
          assertThat(it.data?.total_pages, `is`(4))
        }
        else -> assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Throws(IOException::class)
  @Test
  fun fetchTvListTest() {
    enqueueResponse("/tmdb_tv.json")
    this.service.fetchDiscoverTv(1).transform {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.results?.get(0)?.id, `is`(61889))
          assertThat(it.data?.total_results, `is`(61470))
          assertThat(it.data?.total_pages, `is`(3074))
        }
        else -> assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}
