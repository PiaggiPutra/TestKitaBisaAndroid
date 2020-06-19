

package com.bintang.mvvm_coroutines.di

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
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
  single {
    OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .build()
  }

  single {
    Retrofit.Builder()
      .client(get<OkHttpClient>())
      .baseUrl(EndPoint.TheMovieDB)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  single { get<Retrofit>().create(TheDiscoverService::class.java) }

  single { TheDiscoverClient(get()) }

  single { get<Retrofit>().create(PeopleService::class.java) }

  single { PeopleClient(get()) }

  single { get<Retrofit>().create(MovieService::class.java) }

  single { MovieClient(get()) }

  single { get<Retrofit>().create(TvService::class.java) }

  single { TvClient(get()) }
}
