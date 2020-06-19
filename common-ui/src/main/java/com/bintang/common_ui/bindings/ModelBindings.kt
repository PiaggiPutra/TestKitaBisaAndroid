package com.bintang.common_ui.bindings

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.bintang.common_ui.GlideApp
import com.bintang.common_ui.PosterPath.getBackdropPath
import com.bintang.common_ui.R
import com.bintang.common_ui.extensions.requestGlideListener
import com.bintang.common_ui.extensions.visible
import com.bintang.entity.Keyword
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Person
import com.bintang.entity.entities.Tv
import com.bintang.entity.response.PersonDetail
import com.skydoves.whatif.whatIfNotNull
import com.skydoves.whatif.whatIfNotNullOrEmpty

@BindingAdapter("visibilityByModel")
fun visibilityByModel(view: View, anyList: List<Any>?) {
  anyList.whatIfNotNullOrEmpty {
    view.visible()
  }
}

@BindingAdapter("mapKeywordList")
fun bindMapKeywordList(chipGroup: ChipGroup, keywords: List<Keyword>?) {
  keywords.whatIfNotNullOrEmpty {
    chipGroup.visible()
    for (keyword in it) {
      val chip = Chip(chipGroup.context)
      chip.text = keyword.name
      chip.isCheckable = false
      chip.setTextAppearanceResource(R.style.ChipTextStyle)
      chip.setChipBackgroundColorResource(R.color.colorPrimary)
      chipGroup.addView(chip)
    }
  }
}

@BindingAdapter("mapNameTagList")
fun bindTags(chipGroup: ChipGroup, personDetail: PersonDetail?) {
  personDetail?.also_known_as?.whatIfNotNull {
    chipGroup.visible()
    for (nameTag in it) {
      val chip = Chip(chipGroup.context)
      chip.text = nameTag
      chip.isCheckable = false
      chip.setTextAppearanceResource(R.style.ChipTextStyle)
      chip.setChipBackgroundColorResource(R.color.colorPrimary)
      chipGroup.addView(chip)
    }
  }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindReleaseDate")
fun bindReleaseDate(view: TextView, movie: Movie) {
  view.text = "Release Date : ${movie.release_date}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindAirDate")
fun bindAirDate(view: TextView, tv: Tv) {
  view.text = "First Air Date : ${tv.first_air_date}"
}

@BindingAdapter("biography")
fun bindBiography(view: TextView, personDetail: PersonDetail?) {
  view.text = personDetail?.biography
}

@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, movie: Movie) {
  bindBackDrop(view, movie.backdrop_path, movie.poster_path)
}

@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, tv: Tv) {
  bindBackDrop(view, tv.backdrop_path, tv.poster_path)
}

@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, person: Person) {
  person.profile_path.whatIfNotNull {
    GlideApp.with(view.context)
      .load(getBackdropPath(it))
      .apply(RequestOptions().circleCrop())
      .into(view)
  }
}

private fun bindBackDrop(view: ImageView, path: String?, posterPath: String?) {
  path.whatIfNotNull(
    whatIf = {
      GlideApp.with(view.context)
        .load(getBackdropPath(it))
        .error(ContextCompat.getDrawable(view.context, R.drawable.not_found))
        .listener(view.requestGlideListener())
        .into(view)
    },
    whatIfNot = {
      GlideApp.with(view.context)
        .load(getBackdropPath(posterPath))
        .error(ContextCompat.getDrawable(view.context, R.drawable.not_found))
        .listener(view.requestGlideListener())
        .into(view)
    }
  )
}
