package dev.carrion.data.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NetworkDataSource(private val api: BusinessManApi) {

    fun getRateList(onSuccess: (data: List<ApiEntity.Rate>) -> Unit, onError: (error: String) -> Unit) {
        api.getRateList().enqueue(
            object : Callback<List<ApiEntity.Rate>> {
                override fun onFailure(call: Call<List<ApiEntity.Rate>>, t: Throwable) {
                    onError(t.message ?: "Unknown error")
                }

                override fun onResponse(call: Call<List<ApiEntity.Rate>>, response: Response<List<ApiEntity.Rate>>) {
                    if(response.isSuccessful){
                        val rates = response.body()
                        rates?.let(onSuccess)
                    }else{
                        onError(response.errorBody()?.toString() ?: "Unknown Error")
                    }
                }
            }
        )
    }

    fun getTransactionList(onSuccess: (data: List<ApiEntity.Transaction>) -> Unit, onError: (error: String) -> Unit) {
        api.getTransactions().enqueue(
            object : Callback<List<ApiEntity.Transaction>> {
                override fun onFailure(call: Call<List<ApiEntity.Transaction>>, t: Throwable) {
                    onError(t.message ?: "Unknown error")
                }

                override fun onResponse(call: Call<List<ApiEntity.Transaction>>, response: Response<List<ApiEntity.Transaction>>) {
                    if(response.isSuccessful){
                        val transactions = response.body()
                        transactions?.let(onSuccess)
                    }else{
                        onError(response.errorBody()?.toString() ?: "Unknown Error")
                    }
                }

            }
        )
    }
}