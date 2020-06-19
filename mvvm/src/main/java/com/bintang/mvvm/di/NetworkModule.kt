package com.bintang.mvvm.di

import com.bintang.network.EndPoint
import com.bintang.network.RequestInterceptor
import com.bintang.network.client.MovieClient
import com.bintang.network.client.PeopleClient
import com.bintang.network.client.TheDiscoverClient
import com.bintang.network.client.TvClient
import com.bintang.network.service.MovieService
import com.bintang.network.service.PeopleService
import com.bintang.network.service.TheDiscoverService
import com.bintang.network.service.TvService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(EndPoint.TheMovieDB)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideDiscoverService(retrofit: Retrofit): TheDiscoverService {
    return retrofit.create(TheDiscoverService::class.java)
  }

  @Provides
  @Singleton
  fun provideDiscoverClient(theDiscoverService: TheDiscoverService): TheDiscoverClient {
    return TheDiscoverClient(theDiscoverService)
  }

  @Provides
  @Singleton
  fun providePeopleService(retrofit: Retrofit): PeopleService {
    return retrofit.create(PeopleService::class.java)
  }

  @Provides
  @Singleton
  fun providePeopleClient(peopleService: PeopleService): PeopleClient {
    return PeopleClient(peopleService)
  }

  @Provides
  @Singleton
  fun provideMovieService(retrofit: Retrofit): MovieService {
    return retrofit.create(MovieService::class.java)
  }

  @Provides
  @Singleton
  fun provideMovieClient(movieService: MovieService): MovieClient {
    return MovieClient(movieService)
  }

  @Provides
  @Singleton
  fun provideTvService(retrofit: Retrofit): TvService {
    return retrofit.create(TvService::class.java)
  }

  @Provides
  @Singleton
  fun provideTvClient(tvService: TvService): TvClient {
    return TvClient(tvService)
  }
}
