package dev.carrion.data.local.rate

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.carrion.data.local.BaseDao
import dev.carrion.data.local.DataEntity

@Dao
interface RateDao : BaseDao<DataEntity.Rate> {

    @Query("SELECT * FROM rate_table WHERE id = :id")
    override fun select(id: Long): LiveData<DataEntity.Rate>

    @Query("SELECT * FROM rate_table ORDER BY id")
    override fun selectAll(): LiveData<List<DataEntity.Rate>>

    @Query("DELETE FROM rate_table")
    override fun truncate()

}