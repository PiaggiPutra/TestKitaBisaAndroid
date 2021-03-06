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
data class Tv(
  var page: Int,
  var keywords: List<Keyword>? = ArrayList(),
  var videos: List<Video>? = ArrayList(),
  var reviews: List<Review>? = ArrayList(),
  val poster_path: String?,
  val popularity: Float,
  @PrimaryKey val id: Int,
  val backdrop_path: String?,
  val vote_average: Float,
  val overview: String,
  val first_air_date: String?,
  val origin_country: List<String>,
  val genre_ids: List<Int>,
  val original_language: String,
  val vote_count: Int,
  val name: String,
  val original_name: String,
  var favourite: Boolean = false
) : Parcelable
