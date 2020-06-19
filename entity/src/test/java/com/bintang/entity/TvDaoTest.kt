package com.bintang.entity

import com.bintang.common.MockTestUtils.Companion.mockTv
import com.bintang.common.MockTestUtils.Companion.mockTvList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class TvDaoTest : LocalDatabase() {

  @Test
  fun insertAndReadTest() {
    val tvList = mockTvList()
    db.tvDao().insertTv(tvList)
    val loadFromDB = db.tvDao().getTvList(1)[0]
    assertThat(loadFromDB.page, `is`(1))
    assertThat(loadFromDB.id, `is`(123))
  }

  @Test
  fun updateAndReadTest() {
    val tvList = mockTvList()
    val tv = mockTv()
    db.tvDao().insertTv(tvList)

    val loadFromDB = db.tvDao().getTv(tv.id)
    assertThat(loadFromDB.page, `is`(1))

    tv.page = 10
    db.tvDao().updateTv(tv)

    val updated = db.tvDao().getTv(tv.id)
    assertThat(updated.page, `is`(10))
  }
}
