package com.bintang.mvvm.di

import com.bintang.mvvm.base.ViewModelActivity
import com.bintang.mvvm.base.ViewModelFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class BaseModule {

  @ContributesAndroidInjector
  internal abstract fun contributeViewModelActivity(): ViewModelActivity

  @ContributesAndroidInjector
  internal abstract fun contributeViewModelFragment(): ViewModelFragment
}
