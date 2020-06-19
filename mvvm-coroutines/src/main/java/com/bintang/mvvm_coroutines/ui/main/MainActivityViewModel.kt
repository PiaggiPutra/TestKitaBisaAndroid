package com.bintang.mvvm_coroutines.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Person
import com.bintang.entity.entities.Tv
import com.bintang.mvvm_coroutines.base.LiveCoroutinesViewModel
import com.bintang.mvvm_coroutines.repository.DiscoverRepository
import com.bintang.mvvm_coroutines.repository.PeopleRepository
import timber.log.Timber

class MainActivityViewModel constructor(
  private val discoverRepository: DiscoverRepository,
  private val peopleRepository: PeopleRepository
) : LiveCoroutinesViewModel() {

  private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
  val movieListLiveData: LiveData<List<Movie>>

  private var tvPageLiveData: MutableLiveData<Int> = MutableLiveData()
  val tvListLiveData: LiveData<List<Tv>>

  private var peoplePageLiveData: MutableLiveData<Int> = MutableLiveData()
  val peopleLiveData: LiveData<List<Person>>

  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  init {
    Timber.d("injection MainActivityViewModel")

    this.movieListLiveData = this.moviePageLiveData.switchMap { page ->
      launchOnViewModelScope {
        this.discoverRepository.loadMovies(page) { this.toastLiveData.postValue(it) }
      }
    }

    this.tvListLiveData = this.tvPageLiveData.switchMap { page ->
      launchOnViewModelScope {
        this.discoverRepository.loadTvs(page) { this.toastLiveData.postValue(it) }
      }
    }

    this.peopleLiveData = this.peoplePageLiveData.switchMap { page ->
      launchOnViewModelScope {
        this.peopleRepository.loadPeople(page) { this.toastLiveData.postValue(it) }
      }
    }
  }

  fun postMoviePage(page: Int) = this.moviePageLiveData.postValue(page)

  fun postTvPage(page: Int) = this.tvPageLiveData.postValue(page)

  fun postPeoplePage(page: Int) = this.peoplePageLiveData.postValue(page)

  fun getFavouriteMovieList() = this.discoverRepository.getFavouriteMovieList()

  fun getFavouriteTvList() = this.discoverRepository.getFavouriteTvList()

  fun isLoading() = this.discoverRepository.isLoading || this.peopleRepository.isLoading
}
