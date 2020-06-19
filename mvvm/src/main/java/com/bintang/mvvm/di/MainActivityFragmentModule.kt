package com.bintang.mvvm.di

import com.bintang.mvvm.di.annotations.FragmentScope
import com.bintang.mvvm.ui.main.MovieListFragment
import com.bintang.mvvm.ui.main.PersonListFragment
import com.bintang.mvvm.ui.main.TvListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityFragmentModule {

  @FragmentScope
  @ContributesAndroidInjector
  abstract fun contributeMovieListFragment(): MovieListFragment

  @FragmentScope
  @ContributesAndroidInjector
  abstract fun contributeTvListFragment(): TvListFragment

  @FragmentScope
  @ContributesAndroidInjector
  abstract fun contributePersonListFragment(): PersonListFragment
}
