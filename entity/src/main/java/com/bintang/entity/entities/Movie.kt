package com.bintang.entity.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bintang.entity.Keyword
import com.bintang.entity.Review
import com.bintang.entity.Video
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class Movie(
  var page: Int,
  var keywords: List<Keyword>? = ArrayList(),
  var videos: List<Video>? = ArrayList(),
  var reviews: List<Review>? = ArrayList(),
  val poster_path: String?,
  val adult: Boolean,
  val overview: String,
  val release_date: String?,
  val genre_ids: List<Int>,
  @PrimaryKey val id: Int,
  val original_title: String,
  val original_language: String,
  val title: String,
  val backdrop_path: String?,
  val popularity: Float,
  val vote_count: Int,
  val video: Boolean,
  val vote_average: Float,
  var favourite: Boolean = false
) : Parcelable
