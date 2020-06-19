package com.bintang.mvvm_coroutines

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.bintang.common_ui.BuildConfig
import com.bintang.mvvm_coroutines.di.networkModule
import com.bintang.mvvm_coroutines.di.persistenceModule
import com.bintang.mvvm_coroutines.di.repositoryModule
import com.bintang.mvvm_coroutines.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("unused")
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)

    startKoin {
      androidContext(this@App)
      modules(networkModule)
      modules(persistenceModule)
      modules(repositoryModule)
      modules(viewModelModule)
    }

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}
