package dev.carrion.data.local.transaction

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.carrion.data.local.BaseDao
import dev.carrion.data.local.DataEntity

@Dao
interface TransactionDao: BaseDao<DataEntity.Transaction> {

    @Query("SELECT * FROM transaction_table WHERE id = :id")
    override fun select(id: Long): LiveData<DataEntity.Transaction>

    @Query("SELECT * FROM transaction_table ORDER BY id")
    override fun selectAll(): LiveData<List<DataEntity.Transaction>>

    @Query("SELECT * FROM transaction_table WHERE sku = :item ORDER BY id")
    fun selectByItem(item: String): LiveData<List<DataEntity.Transaction>>

    @Query("SELECT DISTINCT sku FROM transaction_table")
    fun selectDistinctItemNames(): LiveData<List<String>>

    @Query("DELETE FROM transaction_table")
    override fun truncate()
}