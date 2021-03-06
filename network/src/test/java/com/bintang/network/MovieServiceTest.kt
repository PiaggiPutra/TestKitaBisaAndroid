package com.bintang.network

import com.bintang.network.service.MovieService
import java.io.IOException
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class MovieServiceTest : ApiAbstract<MovieService>() {

  private lateinit var service: MovieService

  @Before
  fun initService() {
    this.service = createService(MovieService::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieKeywordsTest() {
    enqueueResponse("/tmdb_movie_keywords.json")
    this.service.fetchKeywords(1).transform {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.id, `is`(550))
          assertThat(it.data?.keywords?.get(0)?.id, `is`(825))
          assertThat(it.data?.keywords?.get(0)?.name, `is`("support group"))
        }
        else -> assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieVideosTest() {
    enqueueResponse("/tmdb_movie_videos.json")
    this.service.fetchVideos(1).transform {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.id, `is`(550))
          assertThat(it.data?.results?.get(0)?.id, `is`("533ec654c3a36854480003eb"))
          assertThat(it.data?.results?.get(0)?.key, `is`("SUXWAEX2jlg"))
        }
        else -> assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieReviewsTest() {
    enqueueResponse("/tmdb_movie_reviews.json")
    this.service.fetchReviews(1).transform {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.id, `is`(297761))
          assertThat(it.data?.results?.get(0)?.id, `is`("57a814dc9251415cfb00309a"))
          assertThat(it.data?.results?.get(0)?.author, `is`("Frank Ochieng"))
        }
        else -> assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}
