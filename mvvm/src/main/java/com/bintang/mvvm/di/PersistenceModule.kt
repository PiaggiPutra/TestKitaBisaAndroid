package com.bintang.mvvm.di

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.bintang.entity.database.AppDatabase
import com.bintang.entity.database.MovieDao
import com.bintang.entity.database.PeopleDao
import com.bintang.entity.database.TvDao
import com.bintang.entity.database.migrations.Migration1_2
import com.bintang.entity.database.migrations.Migration2_3
import com.bintang.mvvm.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

  @Provides
  @Singleton
  fun provideDatabase(@NonNull application: Application): AppDatabase {
    return Room
      .databaseBuilder(application, AppDatabase::class.java,
        application.getString(R.string.database))
      .allowMainThreadQueries()
      .fallbackToDestructiveMigration()
      .addMigrations(Migration1_2, Migration2_3)
      .build()
  }

  @Provides
  @Singleton
  fun provideMovieDao(@NonNull database: AppDatabase): MovieDao {
    return database.movieDao()
  }

  @Provides
  @Singleton
  fun provideTvDao(@NonNull database: AppDatabase): TvDao {
    return database.tvDao()
  }

  @Provides
  @Singleton
  fun providePeopleDao(@NonNull database: AppDatabase): PeopleDao {
    return database.peopleDao()
  }
}
