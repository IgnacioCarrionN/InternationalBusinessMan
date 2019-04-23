package dev.carrion.internationalbusinessman.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.carrion.internationalbusinessman.R

class SelectItemAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<SelectItemAdapter.SelectItemViewHolder>() {

    var listSku: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectItemViewHolder.create(parent)

    override fun getItemCount(): Int = listSku.size

    override fun onBindViewHolder(holder: SelectItemViewHolder, position: Int) = holder.bind(listSku[position], listener)

    fun setListData(listSku: List<String>) {
        this.listSku = listSku
        notifyDataSetChanged()
    }

    class SelectItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val itemSku: TextView = view.findViewById(R.id.txtSku)
        private val itemImg: ImageView = view.findViewById(R.id.imgItem)

        fun bind(sku: String, listener: OnItemClickListener){
            itemSku.text = sku
            view.setOnClickListener {
                listener.onItemClick(sku)
            }
        }

        companion object {
            fun create(parent: ViewGroup): SelectItemViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_select_item, parent, false)
                return SelectItemViewHolder(view)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(sku: String)
    }
}

