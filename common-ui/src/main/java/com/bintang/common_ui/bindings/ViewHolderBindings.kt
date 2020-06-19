package com.bintang.common_ui.bindings

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.bintang.common_ui.GlideApp
import com.bintang.common_ui.PosterPath
import com.bintang.common_ui.R
import com.bintang.common_ui.extensions.gone
import com.bintang.common_ui.extensions.visible
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Tv
import com.skydoves.whatif.whatIfNotNull
import org.threeten.bp.LocalDate

@BindingAdapter("bindingPostUrl")
fun bindingPostUrl(imageView: ImageView, path: String?) {
  path?.let {
    GlideApp.with(imageView.context)
      .load(PosterPath.getPosterPath(it))
      .error(ContextCompat.getDrawable(imageView.context, R.drawable.not_found))
      .apply(RequestOptions().circleCrop())
      .into(imageView)
  }
}

@BindingAdapter("bindingPalettePostUrl", "palette")
fun bindingPalettePostUrl(imageView: ImageView, path: String?, palette: View) {
  path?.let {
    GlideApp.with(imageView.context)
      .load(PosterPath.getPosterPath(it))
      .error(ContextCompat.getDrawable(imageView.context, R.drawable.not_found))
      .listener(GlidePalette.with(PosterPath.getPosterPath(it))
        .use(BitmapPalette.Profile.VIBRANT)
        .intoBackground(palette)
        .crossfade(true))
      .into(imageView)
  }
}

@BindingAdapter("bindingPaletteYoutubeUrl", "palette")
fun bindingPaletteYoutubeUrl(imageView: ImageView, path: String?, palette: View) {
  path?.let {
    GlideApp.with(imageView.context)
      .load(PosterPath.getYoutubeThumbnailPath(it))
      .error(ContextCompat.getDrawable(imageView.context, R.drawable.not_found))
      .listener(GlidePalette.with(PosterPath.getYoutubeThumbnailPath(it))
        .use(BitmapPalette.Profile.VIBRANT)
        .intoBackground(palette)
        .crossfade(true))
      .into(imageView)
  }
}

@BindingAdapter("bindingRibbonOnMovie")
fun bindingRibbonOnMovie(view: View, movie: Movie) {
  movie.release_date.whatIfNotNull {
    val now = LocalDate.now()
    val releaseDate = LocalDate.parse(it).plusMonths(1)
    if (releaseDate.isAfter(now)) {
      view.visible()
    } else {
      view.gone()
    }
  }
}

@BindingAdapter("bindingRibbonOnTv")
fun bindingRibbonOnTv(view: View, tv: Tv) {
  if (tv.vote_average / 2 >= 3.5) {
    view.visible()
  } else {
    view.gone()
  }
}
