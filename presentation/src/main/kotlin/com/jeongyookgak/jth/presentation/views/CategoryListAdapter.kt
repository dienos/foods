package com.jeongyookgak.jth.presentation.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jeongyookgak.jth.domain.model.remote.Category
import com.jeongyookgak.jth.presentation.BR
import com.jeongyookgak.jth.presentation.databinding.CategoryItemBinding
import com.jeongyookgak.jth.presentation.viewmodels.ProductionViewModel

class CategoryListAdapter(
    private val context: Context,
    private val viewModel: ProductionViewModel,
    private val list: List<Category>
) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    private lateinit var binding: CategoryItemBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Category) {
            binding.setVariable(BR.categoryItem, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.setVariable(BR.viewModel, viewModel)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size


}