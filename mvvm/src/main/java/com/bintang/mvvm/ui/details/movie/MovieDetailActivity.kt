package com.bintang.mvvm.ui.details.movie

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.observe
import com.bintang.common_ui.extensions.applyMaterialTransform
import com.bintang.common_ui.extensions.toast
import com.bintang.entity.entities.Movie
import com.bintang.mvvm.R
import com.bintang.mvvm.base.ViewModelActivity
import com.bintang.mvvm.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : ViewModelActivity() {

  private val vm: MovieDetailViewModel by injectViewModels()
  private val binding: ActivityMovieDetailBinding by binding(R.layout.activity_movie_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // post the movie id from intent
    vm.postMovieId(intent.getIntExtra(movieKey, 0))

    // apply material container transform
    applyMaterialTransform(vm.getMovie().title)

    // binding data into layout view
    with(binding) {
      lifecycleOwner = this@MovieDetailActivity
      activity = this@MovieDetailActivity
      viewModel = vm
      movie = vm.getMovie()
    }

    // observe error messages
    observeMessages()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  private fun observeMessages() =
    this.vm.toastLiveData.observe(this) { toast(it) }

  companion object {
    private const val movieKey = "movie"
    fun startActivityModel(context: Context?, startView: View, movie: Movie) {
      if (context is Activity) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(movieKey, movie.id)
        val options = ActivityOptions.makeSceneTransitionAnimation(context,
          startView, movie.title)
        context.startActivity(intent, options.toBundle())
      }
    }
  }
}
