package com.bintang.mvvm_coroutines.di

import com.bintang.mvvm_coroutines.repository.DiscoverRepository
import com.bintang.mvvm_coroutines.repository.MovieRepository
import com.bintang.mvvm_coroutines.repository.PeopleRepository
import com.bintang.mvvm_coroutines.repository.TvRepository
import org.koin.dsl.module

val repositoryModule = module {
  single { DiscoverRepository(get(), get(), get()) }
  single { MovieRepository(get(), get()) }
  single { TvRepository(get(), get()) }
  single { PeopleRepository(get(), get()) }
}
