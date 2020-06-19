package com.bintang.mvvm_coroutines.ui.details.tv

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.entities.Tv
import com.bintang.mvvm_coroutines.base.LiveCoroutinesViewModel
import com.bintang.mvvm_coroutines.repository.TvRepository
import timber.log.Timber

class TvDetailViewModel constructor(
  private val tvRepository: TvRepository
) : LiveCoroutinesViewModel() {

  private val tvIdLiveData: MutableLiveData<Int> = MutableLiveData()
  val keywordListLiveData: LiveData<List<Keyword>>
  val videoListLiveData: LiveData<List<Video>>
  val reviewListLiveData: LiveData<List<Review>>
  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  private lateinit var tv: Tv
  val favourite = ObservableBoolean()

  init {
    Timber.d("Injection TvDetailViewModel")

    this.keywordListLiveData = tvIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        tvRepository.loadKeywordList(id) { toastLiveData.postValue(it) }
      }
    }

    this.videoListLiveData = tvIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        tvRepository.loadVideoList(id) { toastLiveData.postValue(it) }
      }
    }

    this.reviewListLiveData = tvIdLiveData.switchMap { id ->
      launchOnViewModelScope { tvRepository.loadReviewsList(id) { toastLiveData.postValue(it) } }
    }
  }

  fun postTvId(id: Int) {
    this.tvIdLiveData.postValue(id)
    this.tv = tvRepository.getTv(id)
    this.favourite.set(this.tv.favourite)
  }

  fun getTv() = this.tv

  fun onClickedFavourite(tv: Tv) =
    favourite.set(tvRepository.onClickFavourite(tv))
}
