package com.bintang.mvvm_coroutines.di

import com.bintang.mvvm_coroutines.ui.details.movie.MovieDetailViewModel
import com.bintang.mvvm_coroutines.ui.details.person.PersonDetailViewModel
import com.bintang.mvvm_coroutines.ui.details.tv.TvDetailViewModel
import com.bintang.mvvm_coroutines.ui.main.MainActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { MainActivityViewModel(get(), get()) }
  viewModel { MovieDetailViewModel(get()) }
  viewModel { TvDetailViewModel(get()) }
  viewModel { PersonDetailViewModel(get()) }
}
