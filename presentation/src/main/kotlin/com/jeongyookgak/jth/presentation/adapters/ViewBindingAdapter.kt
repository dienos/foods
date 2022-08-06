package com.jeongyookgak.jth.presentation.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeongyookgak.jth.domain.model.remote.Category
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.viewmodels.FavoriteViewModel
import com.jeongyookgak.jth.presentation.viewmodels.ProductionViewModel
import com.jeongyookgak.jth.presentation.views.CategoryListAdapter
import com.jeongyookgak.jth.presentation.views.FavoriteListAdapter
import com.jeongyookgak.jth.presentation.views.ProductionListAdapter
import java.text.DecimalFormat

@BindingAdapter(value = ["categories", "viewModel"])
fun setCategoryList(view: RecyclerView, list: List<Category>?, viewModel: ProductionViewModel) {
    list?.let {
        if(view.adapter == null) {
            CategoryListAdapter(view.context, viewModel, list).apply {
                view.adapter = this
                view.layoutManager = LinearLayoutManager(view.context)
                view.layoutManager = LinearLayoutManager(
                    view.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
            }
        }
    }
}

@BindingAdapter(value = ["productions"])
fun setProductionList(view: RecyclerView, list: List<Production>?) {
    list?.let {
        view.adapter?.apply {
            (this as ProductionListAdapter).updateProductions(list)
        }?: run {
            ProductionListAdapter(view.context, list).apply {
                view.adapter = this
                view.layoutManager = LinearLayoutManager(view.context)
                view.layoutManager = LinearLayoutManager(
                    view.context,
                    RecyclerView.VERTICAL,
                    false
                )
            }
        }
    }
}

@BindingAdapter(value = ["favorites", "viewModel"])
fun setFavoriteList(view: RecyclerView, list: List<Production>?, viewModel: FavoriteViewModel) {
    list?.let {
        view.adapter?.apply {
            (this as FavoriteListAdapter).updateProductions(list)
        }?: run {
            FavoriteListAdapter(view.context, viewModel, list).apply {
                view.adapter = this
                view.layoutManager = LinearLayoutManager(view.context)
                view.layoutManager = LinearLayoutManager(
                    view.context,
                    RecyclerView.VERTICAL,
                    false
                )
            }
        }
    }
}

@BindingAdapter(value = ["price"])
fun setPrice(view: TextView, price: String) {
    val result = if (price.contains(".") || price.length < 4) {
        price
    } else {
        DecimalFormat("###,###").format(price.toLong())
    }

    view.text = view.context.getString(R.string.price, result)
}



