package com.example.mpcb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mpcb.databinding.ToolbarBinding

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(), UICallbacks<V> {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewModel = ViewModelProvider(getBaseActivity()).get(getViewModel())
        mViewModel.setNavigator(getNavigator())
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createDialog()
        onBinding()
    }

    private fun createDialog() {
        val dialog = LoadingDialog(getBaseActivity())
        mViewModel.getVisibility().observe(viewLifecycleOwner, Observer { show ->
            dialog.run {
                if (show) show() else hide()
            }
        })
        mViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            dialog.setMessage(it)
        })

    }

    protected fun getBaseActivity() = activity as BaseActivity<*, *>

    protected fun setToolbar(toolbarBinding: ToolbarBinding, title: String) {
        toolbarBinding.txtToolbarTitle.text = title
    }
}

