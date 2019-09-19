package com.example.mpcb.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, V : ViewDataBinding>(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listItems: List<T> = emptyList()
    private lateinit var itemBinding: V
    private var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBinding = DataBindingUtil.inflate(mInflater, getLayoutId(), parent, false)
        return BaseViewHolder<V>(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount() = listItems.size

    abstract fun getLayoutId(): Int

    abstract fun onBindView(itemBinding: V, item: T, position: Int)
}