package com.gov.mpcb.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gov.mpcb.R
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    UICallbacks<V> {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: V
    private lateinit var mContext: Context
    internal lateinit var mPref: PreferencesHelper
    private lateinit var mManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@BaseActivity, getLayoutId())
        mViewModel = ViewModelProvider(this@BaseActivity).get(getViewModel())
        mViewModel.setNavigator(getNavigator())
        mManager = supportFragmentManager
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mContext = this@BaseActivity
        mPref = PreferencesHelper
        createDialog()
        onBinding()
    }

    private fun createDialog() {
        val dialog = LoadingDialog(this@BaseActivity)
        mViewModel.getVisibility().observe(this@BaseActivity, Observer { show ->
            dialog.run {
                if (show) {
                    if (!isShowing)
                        show()
                } else {
                    if (isShowing)
                        hide()
                }
            }
        })
        mViewModel.getMessage().observe(this@BaseActivity, Observer {
            dialog.setMessage(it)
        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun addFragment(fragment: Fragment, addToBackstack: Boolean, bundle: Bundle? = null) {
        bundle?.let {
            fragment.arguments = bundle
        }
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, fragment)
            if (addToBackstack) {
                addToBackStack(fragment::class.java.simpleName)
            }
            commit()
        }
    }

    /**
     * Method to remove fragment from backStack
     */
    fun removeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            remove(fragment)
            commit()
        }
        supportFragmentManager.popBackStack()
    }

    fun addReportFragment(fragment: Fragment, addToBackstack: Boolean, bundle: Bundle? = null) {
        bundle?.let {
            fragment.arguments = bundle
        }
        mManager.beginTransaction().apply {
            replace(R.id.report_container, fragment)
            if (addToBackstack) {
                addToBackStack(fragment::class.java.simpleName)
            }
            commit()
        }
    }


}
