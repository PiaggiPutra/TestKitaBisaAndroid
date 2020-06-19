package com.bintang.mvvm.repository

import androidx.lifecycle.MutableLiveData
import com.bintang.entity.database.MovieDao
import com.bintang.entity.database.TvDao
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Tv
import com.bintang.network.ApiResponse
import com.bintang.network.client.TheDiscoverClient
import com.bintang.network.message
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class DiscoverRepository @Inject constructor(
  private val discoverClient: TheDiscoverClient,
  private val movieDao: MovieDao,
  private val tvDao: TvDao
) : Repository {

  override var isLoading: Boolean = false

  init {
    Timber.d("Injection DiscoverRepository")
  }

  fun loadMovies(page: Int, error: (String) -> Unit): MutableLiveData<List<Movie>> {
    val liveData = MutableLiveData<List<Movie>>()
    var movies = movieDao.getMovieList(page)
    if (movies.isEmpty()) {
      this.isLoading = true
      discoverClient.fetchDiscoverMovie(page) { response ->
        this.isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              movies = data.results
              movies.forEach { it.page = page }
              liveData.postValue(movies)
              movieDao.insertMovieList(movies)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveData.postValue(movies)
    return liveData
  }

  fun loadTvs(page: Int, error: (String) -> Unit): MutableLiveData<List<Tv>> {
    val liveData = MutableLiveData<List<Tv>>()
    var tvs = tvDao.getTvList(page)
    if (tvs.isEmpty()) {
      this.isLoading = true
      discoverClient.fetchDiscoverTv(page) { response ->
        this.isLoading = false
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              tvs = data.results
              tvs.forEach { it.page = page }
              liveData.postValue(tvs)
              tvDao.insertTv(tvs)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveData.postValue(tvs)
    return liveData
  }

  fun getFavouriteMovieList() = movieDao.getFavouriteMovieList()

  fun getFavouriteTvList() = tvDao.getFavouriteTvList()
}
