package com.bintang.mvvm_coroutines.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.bintang.common.ApiUtil.getCall
import com.bintang.common.MockTestUtils.Companion.mockPerson
import com.bintang.common.MockTestUtils.Companion.mockPersonDetail
import com.bintang.common.MockTestUtils.Companion.mockPersonList
import com.bintang.entity.database.PeopleDao
import com.bintang.entity.entities.Person
import com.bintang.entity.response.PeopleResponse
import com.bintang.entity.response.PersonDetail
import com.bintang.network.ApiResponse
import com.bintang.network.client.PeopleClient
import com.bintang.network.service.PeopleService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalStdlibApi
class PeopleRepositoryTest {

  private lateinit var repository: PeopleRepository
  private lateinit var client: PeopleClient
  private val service = mock<PeopleService>()
  private val peopleDao = mock<PeopleDao>()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    client = PeopleClient(service)
    repository = PeopleRepository(client, peopleDao)
  }

  @Test
  fun loadPeopleListFromNetworkTest() = runBlocking {
    val mockResponse = PeopleResponse(1, emptyList(), 0, 0)
    whenever(service.fetchPopularPeople(1)).thenReturn(getCall(mockResponse))
    whenever(peopleDao.getPeople(1)).thenReturn(emptyList())

    val data = repository.loadPeople(1) { }
    val observer = mock<Observer<List<Person>>>()
    data.observeForever(observer)
    verify(peopleDao).getPeople(1)

    val loadFromDB = peopleDao.getPeople(1)
    data.postValue(loadFromDB)
    verify(observer, times(2)).onChanged(loadFromDB)

    val updatedData = mockPersonList()
    whenever(peopleDao.getPeople(1)).thenReturn(updatedData)
    data.postValue(updatedData)
    verify(observer).onChanged(updatedData)

    client.fetchPopularPeople(1) {
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
  fun loadPersonDetailFromNetworkTest() = runBlocking {
    val mockResponse = mockPersonDetail()
    whenever(service.fetchPersonDetail(1)).thenReturn(getCall(mockResponse))
    whenever(peopleDao.getPerson(1)).thenReturn(mockPerson())

    val data = repository.loadPersonDetail(1) { }
    val observer = mock<Observer<PersonDetail>>()
    data.observeForever(observer)
    verify(peopleDao).getPerson(1)

    val loadFromDB = peopleDao.getPerson(1)
    data.postValue(loadFromDB.personDetail)
    verify(observer, times(2)).onChanged(loadFromDB.personDetail)

    val updatedData = mockPerson()
    whenever(peopleDao.getPerson(1)).thenReturn(updatedData)
    data.postValue(updatedData.personDetail)
    verify(observer, times(3)).onChanged(updatedData.personDetail)

    client.fetchPersonDetail(1) {
      when (it) {
        is ApiResponse.Success -> {
          assertEquals(it, `is`(mockResponse))
          assertEquals(it.data, `is`(updatedData.personDetail))
        }
        else -> assertThat(it, instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}
