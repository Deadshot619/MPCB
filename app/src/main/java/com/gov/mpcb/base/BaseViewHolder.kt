package com.gov.mpcb.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<V : ViewDataBinding>(var viewDataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    init {
        val mBinding = viewDataBinding as V
    }
}