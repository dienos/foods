package com.jeongyookgak.jth.presentation.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.presentation.BR
import com.jeongyookgak.jth.presentation.databinding.ProductionItemBinding

class ProductionListAdapter(private val context : Context, private val list: List<Production>) : RecyclerView.Adapter<ProductionListAdapter.ViewHolder>() {
    private lateinit var  binding : ProductionItemBinding

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item : Production) {
            binding.setVariable(BR.production_item, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding  = ProductionItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size

}