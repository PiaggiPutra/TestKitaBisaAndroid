package com.bintang.mvvm_coroutines.ui.details.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.bintang.entity.entities.Person
import com.bintang.entity.response.PersonDetail
import com.bintang.mvvm_coroutines.base.LiveCoroutinesViewModel
import com.bintang.mvvm_coroutines.repository.PeopleRepository
import timber.log.Timber

class PersonDetailViewModel constructor(
  private val peopleRepository: PeopleRepository
) : LiveCoroutinesViewModel() {

  private val personIdLiveData: MutableLiveData<Int> = MutableLiveData()
  val personLiveData: LiveData<PersonDetail>
  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  private lateinit var person: Person

  init {
    Timber.d("Injection : PersonDetailViewModel")

    this.personLiveData = personIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        peopleRepository.loadPersonDetail(id) { toastLiveData.postValue(it) }
      }
    }
  }

  fun postPersonId(id: Int) {
    this.personIdLiveData.postValue(id)
    this.person = peopleRepository.getPerson(id)
  }

  fun getPerson() = this.person
}
