package com.example.mpcb.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), UICallbacks<V> {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: V
    private lateinit var mContext: Context
    internal lateinit var mPref:PreferencesHelper;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@BaseActivity
        mPref = PreferencesHelper

        mBinding = DataBindingUtil.setContentView(this@BaseActivity, getLayoutId())
//        mViewModel = ViewModelProviders.of(this@BaseActivity).get(getViewModel())
        mViewModel = ViewModelProvider(this@BaseActivity).get(getViewModel())
        mViewModel.setNavigator(getNavigator())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        createDialog()
        onBinding()
    }

    private fun createDialog() {
        val progressBar = ProgressBar(mContext)
        progressBar.apply {
            mViewModel.getVisibility().observe(this@BaseActivity, Observer {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
