package com.bintang.entity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bintang.entity.converters.IntegerListConverter
import com.bintang.entity.converters.KeywordListConverter
import com.bintang.entity.converters.ReviewListConverter
import com.bintang.entity.converters.StringListConverter
import com.bintang.entity.converters.VideoListConverter
import com.bintang.entity.entities.Movie
import com.bintang.entity.entities.Person
import com.bintang.entity.entities.Tv

@Database(entities = [Movie::class, Tv::class, Person::class],
  version = 3, exportSchema = true)
@TypeConverters(value = [StringListConverter::class, IntegerListConverter::class,
  KeywordListConverter::class, VideoListConverter::class, ReviewListConverter::class])
abstract class AppDatabase : RoomDatabase() {

  abstract fun movieDao(): MovieDao
  abstract fun tvDao(): TvDao
  abstract fun peopleDao(): PeopleDao
}
