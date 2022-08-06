package com.jeongyookgak.jth.presentation.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.presentation.BR
import com.jeongyookgak.jth.presentation.JeongYookGakApplication.Companion.favoriteList
import com.jeongyookgak.jth.presentation.databinding.ProductionItemBinding
import com.jeongyookgak.jth.presentation.di.PreferencesUtil.setStringArrayPref

class ProductionListAdapter(
    private val context: Context,
    private val list: List<Production>
) :
    RecyclerView.Adapter<ProductionListAdapter.ViewHolder>() {
    private lateinit var binding: ProductionItemBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Production) {
            binding.setVariable(BR.production_item, item)
        }
    }

    fun updateProductions(productions: List<Production>) {
        val diffCallback = DiffCallback(list, productions)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        (list as ArrayList).clear()
        list.addAll(productions)
        diffResult.dispatchUpdatesTo(this)
    }

    private class DiffCallback(oldList: List<Production>, newList: List<Production>) :
        DiffUtil.Callback() {
        private val oldList: List<Production>
        private val newList: List<Production>

        init {
            this.oldList = oldList
            this.newList = newList
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].key == newList[newItemPosition].key
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem: Production = oldList[oldItemPosition]
            val newItem: Production = newList[newItemPosition]
            println("oldKey"+oldItem.key)
            println("newKey"+newItem.key )
            return oldItem.key == newItem.key
        }

        @Nullable
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ProductionItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.favoriteCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if(favoriteList.isNotEmpty()) {
                    favoriteList.remove(list[position].key)
                }

                favoriteList.add(list[position].key)
            } else {
                if(favoriteList.isNotEmpty()) {
                    favoriteList.remove(list[position].key)
                }
            }

            setStringArrayPref(context, favoriteList)
        }

        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}