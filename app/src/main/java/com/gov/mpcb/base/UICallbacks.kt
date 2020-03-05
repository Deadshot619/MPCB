package com.gov.mpcb.base

import androidx.annotation.LayoutRes

interface UICallbacks<V> {

    @LayoutRes
    fun getLayoutId(): Int
    fun getViewModel(): Class<V>
    fun getNavigator(): BaseNavigator
    fun onBinding()
}