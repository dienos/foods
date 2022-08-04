package com.jeongyookgak.jth.presentation.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.jeongyookgak.jth.data.model.Tab
import com.jeongyookgak.jth.domain.model.remote.Category
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.presentation.views.CategoryListAdapter
import com.jeongyookgak.jth.presentation.views.ProductionListAdapter


@BindingAdapter(value = ["categories"])
fun setCategoryList(view: RecyclerView, list: List<Category>?) {
    list?.let {
        CategoryListAdapter(view.context, list).apply {
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

@BindingAdapter(value = ["tabs"])
fun setTabs(view: TabLayout, list: List<Tab>?) {
    list?.let {

    }
}


@BindingAdapter(value = ["productions"])
fun setProductionList(view: RecyclerView, list: List<Production>?) {
    list?.let {
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
