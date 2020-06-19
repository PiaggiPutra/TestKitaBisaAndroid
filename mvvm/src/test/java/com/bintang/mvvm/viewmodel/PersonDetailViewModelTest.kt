package com.bintang.mvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.bintang.common.ApiUtil.getCall
import com.bintang.common.MockTestUtils.Companion.mockPerson
import com.bintang.common.MockTestUtils.Companion.mockPersonDetail
import com.bintang.entity.database.PeopleDao
import com.bintang.entity.response.PersonDetail
import com.bintang.mvvm.repository.PeopleRepository
import com.bintang.mvvm.ui.details.person.PersonDetailViewModel
import com.bintang.network.client.PeopleClient
import com.bintang.network.service.PeopleService
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PersonDetailViewModelTest {

  private lateinit var viewModel: PersonDetailViewModel
  private lateinit var repository: PeopleRepository
  private val service = mock<PeopleService>()
  private val client = PeopleClient(service)
  private val peopleDao = mock<PeopleDao>()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    this.repository = PeopleRepository(client, peopleDao)
    this.viewModel = PersonDetailViewModel(repository)
  }

  @Test
  fun loadPersonDetailFromLocalDatabase() {
    val mockResponse = mockPersonDetail()
    whenever(service.fetchPersonDetail(1)).thenReturn(getCall(mockResponse))
    whenever(peopleDao.getPerson(1)).thenReturn(mockPerson())

    val data = viewModel.personLiveData
    val observer = mock<Observer<PersonDetail>>()
    data.observeForever(observer)

    viewModel.postPersonId(1)
    viewModel.postPersonId(1)

    verify(peopleDao, atLeastOnce()).getPerson(1)
    verify(observer, atLeastOnce()).onChanged(mockResponse)
    data.removeObserver(observer)
  }
}
