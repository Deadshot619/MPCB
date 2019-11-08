package com.example.mpcb.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, V : ViewDataBinding>() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listItems = mutableListOf<T>()
    private lateinit var itemBinding: V

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBinding = DataBindingUtil.inflate(
            LayoutInflater
                .from(parent.context), getLayoutId(), parent, false
        )
        return BaseViewHolder<V>(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    protected fun setItems(models: List<T>) {
        listItems.clear()
        models.forEach { listItems.add(it) }
        notifyDataSetChanged()
    }

    override fun getItemCount() = listItems.size

    abstract fun getLayoutId(): Int

    abstract fun onBindView(itemBinding: V, item: T, position: Int)
}