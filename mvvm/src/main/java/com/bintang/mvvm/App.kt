package com.bintang.mvvm

import com.jakewharton.threetenabp.AndroidThreeTen
import com.bintang.mvvm.di.DaggerAppComponent
import dagger.android.DaggerApplication
import timber.log.Timber

@Suppress("unused")
class App : DaggerApplication() {

  private val appComponent = DaggerAppComponent.factory().create(this)

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  override fun applicationInjector() = appComponent
}
