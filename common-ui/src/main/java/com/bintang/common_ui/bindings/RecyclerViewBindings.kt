package com.bintang.common_ui.bindings

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.bintang.common_ui.PosterPath
import com.bintang.common_ui.adapters.MovieListAdapter
import com.bintang.common_ui.adapters.PeopleAdapter
import com.bintang.common_ui.adapters.ReviewListAdapter
import com.bintang.common_ui.adapters.TvListAdapter
import com.bintang.common_ui.adapters.VideoListAdapter
import com.bintang.common_ui.extensions.visible
import com.bintang.entity.Review
import com.bintang.entity.Video
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Person
import com.bintang.entity.entities.Tv
import com.skydoves.whatif.whatIfNotNullOrEmpty

@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, baseAdapter: BaseAdapter) {
  view.adapter = baseAdapter
}

@BindingAdapter("adapterMovieList")
fun bindAdapterMovieList(view: RecyclerView, movies: List<Movie>?) {
  movies.whatIfNotNullOrEmpty {
    val adapter = view.adapter as? MovieListAdapter
    adapter?.addMovieList(it)
  }
}

@BindingAdapter("adapterTvList")
fun bindAdapterTvList(view: RecyclerView, tvs: List<Tv>?) {
  tvs.whatIfNotNullOrEmpty {
    val adapter = view.adapter as? TvListAdapter
    adapter?.addTvList(it)
  }
}

@BindingAdapter("adapterPersonList")
fun bindAdapterPersonList(view: RecyclerView, people: List<Person>?) {
  people.whatIfNotNullOrEmpty {
    val adapter = view.adapter as? PeopleAdapter
    adapter?.addPeople(it)
  }
}

@BindingAdapter("adapterVideoList")
fun bindAdapterVideoList(recyclerView: RecyclerView, videos: List<Video>?) {
  videos.whatIfNotNullOrEmpty {
    val adapter = VideoListAdapter()
    adapter.addVideoList(it)
    recyclerView.adapter = adapter
    recyclerView.visible()
  }
}

@BindingAdapter("adapterReviewList")
fun bindAdapterReviewList(recyclerView: RecyclerView, reviews: List<Review>?) {
  reviews.whatIfNotNullOrEmpty {
    val adapter = ReviewListAdapter()
    adapter.addReviewList(it)
    recyclerView.adapter = adapter
    recyclerView.isNestedScrollingEnabled = false
    recyclerView.setHasFixedSize(true)
    recyclerView.visible()
  }
}

@BindingAdapter("onVideoItemClick")
fun onVideoItemClick(view: View, video: Video) {
  view.setOnClickListener {
    val playVideoIntent = Intent(
      Intent.ACTION_VIEW, Uri.parse(PosterPath.getYoutubeVideoPath(video.key)))
    view.context.startActivity(playVideoIntent)
  }
}
