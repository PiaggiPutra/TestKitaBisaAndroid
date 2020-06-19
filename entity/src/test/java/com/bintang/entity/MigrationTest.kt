package com.bintang.entity

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import com.bintang.entity.database.AppDatabase
import com.bintang.entity.database.migrations.Migration1_2
import com.bintang.entity.database.migrations.Migration2_3
import java.io.IOException
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class MigrationTest {

  private val TEST_DB = "migration-test"

  @Rule
  @JvmField
  val helper: MigrationTestHelper = MigrationTestHelper(
    InstrumentationRegistry.getInstrumentation(),
    AppDatabase::class.java.canonicalName,
    FrameworkSQLiteOpenHelperFactory()
  )

  @Test
  @Throws(IOException::class)
  fun migrate1To2() {
    helper.createDatabase(TEST_DB, 1).apply {
      execSQL(
        "CREATE TABLE IF NOT EXISTS `Gallery` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` INTEGER NOT NULL)")
      close()
    }

    helper.runMigrationsAndValidate(TEST_DB, 1, true, Migration1_2)
  }

  @Test
  @Throws(IOException::class)
  fun migrate2To3() {
    helper.createDatabase(TEST_DB, 2).apply {
      execSQL("DROP TABLE IF EXISTS `Gallery`")
      execSQL("ALTER TABLE `Movie` ADD COLUMN `favourite` INTEGER NOT NULL DEFAULT '0'")
      execSQL("ALTER TABLE `Tv` ADD COLUMN `favourite` INTEGER NOT NULL DEFAULT '0'")
      close()
    }

    helper.runMigrationsAndValidate(TEST_DB, 2, true, Migration2_3)
  }
}
