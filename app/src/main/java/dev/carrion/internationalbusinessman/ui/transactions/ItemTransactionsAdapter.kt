package dev.carrion.internationalbusinessman.ui.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.domain.entities.Transaction
import dev.carrion.internationalbusinessman.R

class ItemTransactionsAdapter : RecyclerView.Adapter<ItemTransactionsAdapter.ItemTransactionsViewHolder>() {

    var transactions: List<Transaction> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTransactionsViewHolder = ItemTransactionsViewHolder.create(parent)

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: ItemTransactionsViewHolder, position: Int) = holder.bind(transactions[position])

    fun setData(newList: List<Transaction>){
        transactions = newList
        notifyDataSetChanged()
    }


    class ItemTransactionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtSku: TextView = view.findViewById(R.id.txtSku)
        private val txtAmount: TextView = view.findViewById(R.id.txtAmount)

        fun bind(transaction: Transaction){
            txtSku.text = transaction.sku
            txtAmount.text = transaction.amount.toString()
        }

        companion object {
            fun create(parent: ViewGroup): ItemTransactionsViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_transaction, parent, false)
                return ItemTransactionsViewHolder(view)
            }
        }
    }

}