package com.gov.mpcb.base

interface UICallbacks<V> {

    fun getLayoutId(): Int
    fun getViewModel(): Class<V>
    fun getNavigator(): BaseNavigator
    fun onBinding()
}