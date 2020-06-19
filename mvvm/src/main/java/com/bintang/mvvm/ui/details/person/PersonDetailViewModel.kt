package com.bintang.mvvm.ui.details.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.bintang.entity.entities.Person
import com.bintang.entity.response.PersonDetail
import com.bintang.mvvm.repository.PeopleRepository
import javax.inject.Inject
import timber.log.Timber

class PersonDetailViewModel @Inject constructor(
  private val peopleRepository: PeopleRepository
) : ViewModel() {

  private val personIdLiveData: MutableLiveData<Int> = MutableLiveData()
  val personLiveData: LiveData<PersonDetail>
  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  private lateinit var person: Person

  init {
    Timber.d("Injection : PersonDetailViewModel")

    this.personLiveData = personIdLiveData.switchMap { id ->
      peopleRepository.loadPersonDetail(id) { toastLiveData.postValue(it) }
    }
  }

  fun postPersonId(id: Int) {
    this.personIdLiveData.postValue(id)
    this.person = peopleRepository.getPerson(id)
  }

  fun getPerson() = this.person
}
