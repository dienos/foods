package com.jeongyookgak.jth.presentation.views

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jeongyookgak.jth.data.model.ProductionItem
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.presentation.BR
import com.jeongyookgak.jth.presentation.JeongYookGakApplication.Companion.controlFavoriteList
import com.jeongyookgak.jth.presentation.JeongYookGakApplication.Companion.favoriteList
import com.jeongyookgak.jth.presentation.databinding.ProductionItemBinding
import com.jeongyookgak.jth.presentation.di.PreferencesUtil.setStringArrayPref
import com.jeongyookgak.jth.presentation.views.ListFragment.Companion.PUT_EXTRA_DETAIL

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ProductionItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.favoriteCheck.setOnCheckedChangeListener { _, isChecked ->
            controlFavoriteList(
                context = context,
                isChecked = isChecked,
                data = list[position]
            )
        }

        binding.itemRoot.setOnClickListener {
            list[position].isFavorite = binding.favoriteCheck.isChecked
            val intent = Intent(context, ProductionDetailActivity::class.java)
            intent.putExtra(PUT_EXTRA_DETAIL, list[position] as ProductionItem)
            context.startActivity(intent)
        }

        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}