package com.bintang.mvvm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bintang.mvvm.di.annotations.ViewModelKey
import com.bintang.mvvm.ui.details.movie.MovieDetailViewModel
import com.bintang.mvvm.ui.details.person.PersonDetailViewModel
import com.bintang.mvvm.ui.details.tv.TvDetailViewModel
import com.bintang.mvvm.ui.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(MainActivityViewModel::class)
  internal abstract fun bindMainActivityViewModels(mainActivityViewModel: MainActivityViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(MovieDetailViewModel::class)
  internal abstract fun bindMovieDetailActivityViewModels(movieDetailViewModel: MovieDetailViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(TvDetailViewModel::class)
  internal abstract fun bindTvDetailActivityViewModels(tvDetailViewModel: TvDetailViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(PersonDetailViewModel::class)
  internal abstract fun bindPersonDetailActivityViewModels(personDetailViewModel: PersonDetailViewModel): ViewModel

  @Binds
  internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
