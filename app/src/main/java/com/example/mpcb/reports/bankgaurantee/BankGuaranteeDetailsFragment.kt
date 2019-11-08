package com.example.mpcb.reports.bankgaurantee

import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentBankGauranteeBinding
import com.example.mpcb.utils.showMessage

class BankGuaranteeDetailsFragment : BaseFragment<FragmentBankGauranteeBinding, BankGauranteeViewModel>(),
    BankGauranteeNavigator {

    internal lateinit var adapter: BankGuaranteeDetailsAdapter

    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun getLayoutId(): Int = R.layout.fragment_bank_gaurantee
    override fun getViewModel() = BankGauranteeViewModel::class.java
    override fun getNavigator() = this@BankGuaranteeDetailsFragment

    override fun onBinding() {
        setToolbar(mBinding.toolbarLayout, "Bank Guarantee Details")

        initRecyclerView()

    }

    private fun initRecyclerView() {

        val list = arrayListOf<BankGauranteeViewModel>()
        list.add(BankGauranteeViewModel())
        list.add(BankGauranteeViewModel())
        list.add(BankGauranteeViewModel())
        adapter = BankGuaranteeDetailsAdapter()
        mBinding.rvBGDetails.adapter = adapter
        adapter.setList(list)

    }
}