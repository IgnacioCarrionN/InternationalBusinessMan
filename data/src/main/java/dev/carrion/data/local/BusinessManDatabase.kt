package dev.carrion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.carrion.data.local.rate.RateDao
import dev.carrion.data.local.transaction.TransactionDao
import dev.carrion.domain.entities.Rate
import dev.carrion.domain.entities.Transaction

@Database(entities = [DataEntity.Rate::class, DataEntity.Transaction::class], version = 2, exportSchema = false)
abstract class BusinessManDatabase : RoomDatabase() {

    abstract fun rateDao(): RateDao

    abstract fun transactionDao(): TransactionDao
}