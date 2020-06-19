package com.bintang.entity

import com.bintang.common.MockTestUtils.Companion.mockMovie
import com.bintang.common.MockTestUtils.Companion.mockMovieList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class MovieDaoTest : LocalDatabase() {

  @Test
  fun insertAndReadTest() {
    val mockData = mockMovieList()
    db.movieDao().insertMovieList(mockData)
    val loadFromDB = db.movieDao().getMovieList(1)[0]
    assertThat(loadFromDB.page, `is`(1))
    assertThat(loadFromDB.id, `is`(123))
  }

  @Test
  fun updateAndReadTest() {
    val mockData = mockMovieList()
    val movie = mockMovie()
    db.movieDao().insertMovieList(mockData)

    val loadFromDB = db.movieDao().getMovie(movie.id)
    assertThat(loadFromDB.page, `is`(1))

    movie.page = 10
    db.movieDao().updateMovie(movie)

    val updated = db.movieDao().getMovie(movie.id)
    assertThat(updated.page, `is`(10))
  }
}
