package com.bintang.mvvm_coroutines.repository

import androidx.lifecycle.MutableLiveData
import com.bintang.entity.database.PeopleDao
import com.bintang.entity.entities.Person
import com.bintang.entity.response.PersonDetail
import com.bintang.network.ApiResponse
import com.bintang.network.client.PeopleClient
import com.bintang.network.message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PeopleRepository constructor(
  private val peopleClient: PeopleClient,
  private val peopleDao: PeopleDao
) : Repository {

  override var isLoading: Boolean = false

  init {
    Timber.d("Injection PeopleRepository")
  }

  suspend fun loadPeople(page: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Person>>()
    var people = peopleDao.getPeople(page)
    if (people.isEmpty()) {
      isLoading = true
      peopleClient.fetchPopularPeople(page) { response ->
        isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              people = data.results
              people.forEach { it.page = page }
              liveData.postValue(people)
              peopleDao.insertPeople(people)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveData.apply { postValue(people) }
  }

  suspend fun loadPersonDetail(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<PersonDetail>()
    val person = peopleDao.getPerson(id)
    var personDetail = person.personDetail
    if (personDetail == null) {
      isLoading = true
      peopleClient.fetchPersonDetail(id) { response ->
        isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              personDetail = data
              person.personDetail = personDetail
              liveData.postValue(personDetail)
              peopleDao.updatePerson(person)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveData.apply { postValue(person.personDetail) }
  }

  fun getPerson(id: Int) = peopleDao.getPerson(id)
}
