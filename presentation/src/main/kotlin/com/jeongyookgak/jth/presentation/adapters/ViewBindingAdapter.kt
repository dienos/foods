package com.jeongyookgak.jth.presentation.adapters

import android.content.Intent
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeongyookgak.jth.data.model.ProductionItem
import com.jeongyookgak.jth.domain.model.remote.Category
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.presentation.JeongYookGakApplication
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.viewmodels.FavoriteViewModel
import com.jeongyookgak.jth.presentation.viewmodels.ProductionViewModel
import com.jeongyookgak.jth.presentation.views.*
import com.jeongyookgak.jth.presentation.views.ProductionsFragment.Companion.PUT_EXTRA_DETAIL
import java.text.DecimalFormat

@BindingAdapter(value = ["categories", "viewModel"])
fun setCategoryList(view: RecyclerView, list: List<Category>?, viewModel: ProductionViewModel) {
    list?.let {
        if (view.adapter == null) {
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

@BindingAdapter(value = ["productions", "viewModel"])
fun setProductionList(view: RecyclerView, list: List<Production>?, viewModel: ProductionViewModel) {
    list?.let {
        ProductionListAdapter(view.context, list!!, viewModel).apply {
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

private fun setFavoriteAdapter(view: RecyclerView, list : List<Production>, viewModel: FavoriteViewModel) {
    FavoriteListAdapter(view.context, viewModel, list).apply {
        view.adapter = this
        view.layoutManager = LinearLayoutManager(view.context)
        view.layoutManager = LinearLayoutManager(
            view.context,
            RecyclerView.VERTICAL,
            false
        )

        setOnClickListener(object : FavoriteListAdapter.OnClickListener {
            override fun onClick(item: Production) {
                item.isFavorite = true
                val intent = Intent(view.context , ProductionDetailActivity::class.java)
                intent.putExtra(PUT_EXTRA_DETAIL, item as ProductionItem)
                view.context.startActivity(intent)
            }
        })

        setOnCheckedChangeListener(object : FavoriteListAdapter.OnCheckedChangeListener {
            override fun onChange(isChecked: Boolean, item : Production) {
                JeongYookGakApplication.controlFavoriteList(
                    context = view.context,
                    isChecked = isChecked,
                    data = item
                )

                viewModel.getFavorite()
            }
        })
    }
}

@BindingAdapter(value = ["favorites", "viewModel"])
fun setFavoriteList(view: RecyclerView, list: List<Production>?, viewModel: FavoriteViewModel) {
    list?.let {
        view.adapter?.apply {
            val adapter = this as FavoriteListAdapter
            adapter.updateFavorite(list)

            adapter.setOnCheckedChangeListener(object : FavoriteListAdapter.OnCheckedChangeListener {
                override fun onChange(isChecked: Boolean, item : Production) {
                    JeongYookGakApplication.controlFavoriteList(
                        context = view.context,
                        isChecked = isChecked,
                        data = item
                    )

                    viewModel.getFavorite()
                }
            })

            adapter.setOnClickListener(object : FavoriteListAdapter.OnClickListener {
                override fun onClick(item: Production) {
                    item.isFavorite = true
                    val intent = Intent(view.context , ProductionDetailActivity::class.java)
                    intent.putExtra(PUT_EXTRA_DETAIL, item as ProductionItem)
                    view.context.startActivity(intent)
                }
            })
        } ?: run {
            setFavoriteAdapter(view, list, viewModel)
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



