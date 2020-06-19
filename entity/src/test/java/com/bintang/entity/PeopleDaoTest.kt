package com.bintang.entity

import com.bintang.common.MockTestUtils.Companion.mockPerson
import com.bintang.common.MockTestUtils.Companion.mockPersonList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class PeopleDaoTest : LocalDatabase() {

  @Test
  fun insertAndReadTest() {
    val people = mockPersonList()
    db.peopleDao().insertPeople(people)
    val loadFromDB = db.peopleDao().getPeople(1)[0]
    assertThat(loadFromDB.page, `is`(1))
    assertThat(loadFromDB.id, `is`(123))
  }

  @Test
  fun updateAndReadTest() {
    val people = mockPersonList()
    val mockPerson = mockPerson()
    db.peopleDao().insertPeople(people)

    val loadFromDB = db.peopleDao().getPerson(mockPerson.id)
    assertThat(loadFromDB.page, `is`(1))

    mockPerson.page = 10
    db.peopleDao().updatePerson(mockPerson)

    val updated = db.peopleDao().getPerson(mockPerson.id)
    assertThat(updated.page, `is`(10))
  }
}
