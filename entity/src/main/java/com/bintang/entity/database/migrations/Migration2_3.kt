package com.bintang.entity.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Suppress("ClassName")
object Migration2_3 : Migration(2, 3) {

  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("DROP TABLE Gallery")
    database.execSQL("ALTER TABLE Movie ADD COLUMN favourite INTEGER NOT NULL DEFAULT '0'")
    database.execSQL("ALTER TABLE Tv ADD COLUMN favourite INTEGER NOT NULL DEFAULT '0'")
  }
}
