package com.bintang.entity.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bintang.entity.response.PersonDetail
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "People")
data class Person(
  var page: Int,
  @Embedded var personDetail: PersonDetail? = null,
  val profile_path: String?,
  val adult: Boolean,
  @PrimaryKey val id: Int,
  val name: String,
  val popularity: Float
) : Parcelable
