package com.bintang.mvvm_coroutines.di

import androidx.room.Room
import com.bintang.entity.database.AppDatabase
import com.bintang.entity.database.migrations.Migration1_2
import com.bintang.entity.database.migrations.Migration2_3
import com.bintang.mvvm_coroutines.R
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {
  single {
    Room
      .databaseBuilder(androidApplication(), AppDatabase::class.java,
        androidApplication().getString(R.string.database))
      .allowMainThreadQueries()
      .fallbackToDestructiveMigration()
      .addMigrations(Migration1_2, Migration2_3)
      .build()
  }

  single { get<AppDatabase>().movieDao() }
  single { get<AppDatabase>().tvDao() }
  single { get<AppDatabase>().peopleDao() }
}
