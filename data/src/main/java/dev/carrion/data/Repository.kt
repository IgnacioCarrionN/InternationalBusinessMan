package dev.carrion.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dev.carrion.data.local.LocalDataSource
import dev.carrion.data.mappers.map
import dev.carrion.data.network.NetworkDataSource

class Repository(val localDataSource: LocalDataSource, val networkDataSource: NetworkDataSource) {

    val networkErrors = MutableLiveData<String>()

    fun fetchApiRates() {
        Log.d("Repository", "fecthApiRates()")
        networkDataSource.getRateList(
            { data ->
                val domainData = data.map {
                    it.map()
                }
                localDataSource.saveRates(domainData){}
            },
            {
                networkErrors.postValue(it)
            }
        )
    }

    fun fetchApiTransactions() {
        Log.d("Repository", "fecthApiTransactions()")
        networkDataSource.getTransactionList(
            { data ->
                val domainData = data.map {
                    it.map()
                }
                localDataSource.saveTransactions(domainData){}
            },
            {
                networkErrors.postValue(it)
            }
        )
    }

    fun clearTables() {
        localDataSource.deleteRates()
        localDataSource.deleteTransactions()
    }

    fun getLocalRates() = localDataSource.getRates()

    fun getLocalTransactions() = localDataSource.getTransactions()

    fun getLocalTransactionsForItem(item: String) = localDataSource.getTransactionsForItem(item)

    fun getDistinctItemsList() = localDataSource.getDistinctItemsList()
}