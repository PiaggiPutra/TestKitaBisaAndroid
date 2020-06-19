package com.bintang.mvvm.di

import com.bintang.mvvm.di.annotations.ActivityScope
import com.bintang.mvvm.ui.details.movie.MovieDetailActivity
import com.bintang.mvvm.ui.details.person.PersonDetailActivity
import com.bintang.mvvm.ui.details.tv.TvDetailActivity
import com.bintang.mvvm.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
  internal abstract fun contributeMainActivity(): MainActivity

  @ActivityScope
  @ContributesAndroidInjector
  internal abstract fun contributeMovieDetailActivity(): MovieDetailActivity

  @ActivityScope
  @ContributesAndroidInjector
  internal abstract fun contributeTvDetailActivity(): TvDetailActivity

  @ActivityScope
  @ContributesAndroidInjector
  internal abstract fun contributePersonDetailActivity(): PersonDetailActivity
}
