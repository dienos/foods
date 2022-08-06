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
import com.jeongyookgak.jth.presentation.databinding.FavoriteItemBinding
import com.jeongyookgak.jth.presentation.viewmodels.FavoriteViewModel

class FavoriteListAdapter(
    private val context: Context,
    private val viewModel: FavoriteViewModel,
    private val list: List<Production>
) : RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {
    private lateinit var binding: FavoriteItemBinding
    private var onClickListener: OnClickListener? = null
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    interface OnClickListener {
        fun onClick(item: Production)
    }

    interface OnCheckedChangeListener {
        fun onChange(isChecked: Boolean, item : Production)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        onCheckedChangeListener = listener
    }

    fun updateFavorite(productions: List<Production>) {
        val diffCallback = DiffCallback(list, productions)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        (list as ArrayList).clear()
        list.addAll(productions)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Production) {
            binding.setVariable(BR.favorite_item, item)

            view.setOnClickListener {
                onClickListener?.onClick(item)
            }

            binding.favoriteCheck.setOnCheckedChangeListener { _, isChecked ->
                onCheckedChangeListener?.onChange(isChecked, item)
            }
        }
    }

    class DiffCallback(oldList: List<Production>, newList: List<Production>) :
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
            val oldItem: Production = oldList[oldItemPosition]
            val newItem: Production = newList[newItemPosition]
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem: Production = oldList[oldItemPosition]
            val newItem: Production = newList[newItemPosition]
            return oldItem.key == newItem.key
        }

        @Nullable
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FavoriteItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}