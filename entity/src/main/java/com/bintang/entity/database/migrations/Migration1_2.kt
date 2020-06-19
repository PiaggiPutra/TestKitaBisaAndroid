
package com.bintang.entity.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Suppress("ClassName")
object Migration1_2 : Migration(1, 2) {

  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("CREATE TABLE IF NOT EXISTS `Gallery` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` INTEGER NOT NULL)")
  }
}
