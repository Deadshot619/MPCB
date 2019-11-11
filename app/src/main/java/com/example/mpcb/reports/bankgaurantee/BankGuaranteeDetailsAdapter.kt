package com.example.mpcb.reports.bankgaurantee

import com.example.mpcb.R
import com.example.mpcb.base.BaseAdapter
import com.example.mpcb.databinding.ItemBankGauranteeDetailsBinding

class BankGuaranteeDetailsAdapter :
    BaseAdapter<BankGauranteeViewModel, ItemBankGauranteeDetailsBinding>() {
    override fun getLayoutId(): Int = R.layout.item_bank_gaurantee_details

    override fun onBindView(
        itemBinding: ItemBankGauranteeDetailsBinding,
        item: BankGauranteeViewModel,
        position: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setList(list: List<BankGauranteeViewModel>) {
        setItems(list)
    }

    override fun getItemCount(): Int {
        return 2
    }

}
