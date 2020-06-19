package com.bintang.mvvm_coroutines.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bintang.common_ui.adapters.MovieFavouriteListAdapter
import com.bintang.common_ui.adapters.TvFavouriteListAdapter
import com.bintang.common_ui.customs.FlourishFactory
import com.bintang.common_ui.extensions.applyExitMaterialTransform
import com.bintang.common_ui.viewholders.MovieFavouriteListViewHolder
import com.bintang.common_ui.viewholders.TvFavouriteListViewHolder
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Tv
import com.bintang.mvvm_coroutines.R
import com.bintang.mvvm_coroutines.ui.details.movie.MovieDetailActivity
import com.bintang.mvvm_coroutines.ui.details.tv.TvDetailActivity
import kotlinx.android.synthetic.main.activity_main.main_bottom_navigation
import kotlinx.android.synthetic.main.activity_main.main_toolbar
import kotlinx.android.synthetic.main.activity_main.main_viewpager
import kotlinx.android.synthetic.main.activity_main.parentView
import kotlinx.android.synthetic.main.layout_favourites.view.back
import kotlinx.android.synthetic.main.layout_favourites.view.recyclerViewMovies
import kotlinx.android.synthetic.main.layout_favourites.view.recyclerViewTvs
import kotlinx.android.synthetic.main.toolbar_home.view.toolbar_favourite
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),
  MovieFavouriteListViewHolder.Delegate, TvFavouriteListViewHolder.Delegate {

  private val viewModel: MainActivityViewModel by viewModel()

  private val adapterMovieList = MovieFavouriteListAdapter(this)
  private val adapterTvList = TvFavouriteListAdapter(this)

  private val flourish by lazy {
    FlourishFactory.create(parentView, R.layout.layout_favourites)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    applyExitMaterialTransform()
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initializeUI()
  }

  private fun initializeUI() {
    with(main_viewpager) {
      adapter = MainPagerAdapter(supportFragmentManager)
      offscreenPageLimit = 3
      addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageScrolled(
          position: Int,
          positionOffset: Float,
          positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) {
          main_bottom_navigation.menu.getItem(position).isChecked = true
        }
      })
      main_bottom_navigation.setOnNavigationItemSelectedListener {
        when (it.itemId) {
          R.id.action_one -> currentItem = 0
          R.id.action_two -> currentItem = 1
          R.id.action_three -> currentItem = 2
        }
        true
      }
    }

    with(flourish.flourishView) {
      recyclerViewMovies.adapter = adapterMovieList
      recyclerViewTvs.adapter = adapterTvList
      back.setOnClickListener { flourish.dismiss() }
      main_toolbar.toolbar_favourite.setOnClickListener {
        refreshFavourites()
        flourish.show()
      }
    }
  }

  override fun onResume() {
    super.onResume()
    refreshFavourites()
  }

  private fun refreshFavourites() {
    this.adapterMovieList.addMovieList(viewModel.getFavouriteMovieList())
    this.adapterTvList.addTvList(viewModel.getFavouriteTvList())
  }

  override fun onItemClick(view: View, movie: Movie) =
    MovieDetailActivity.startActivityModel(this, view, movie)

  override fun onItemClick(tv: Tv) =
    TvDetailActivity.startActivityModel(this, tv.id)

  override fun onBackPressed() {
    if (this.flourish.isShowing()) {
      this.flourish.dismiss()
    } else {
      super.onBackPressed()
    }
  }
}
