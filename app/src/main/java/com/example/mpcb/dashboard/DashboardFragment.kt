package com.example.mpcb.dashboard


import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentDashboardBinding
import com.example.mpcb.utils.showMessage
import com.example.mpcb.base.BaseViewModel
import java.util.*


class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(), DashboardNavigator {

    override fun getLayoutId() = R.layout.fragment_dashboard
    override fun getViewModel() = DashboardViewModel::class.java
    override fun getNavigator() = this@DashboardFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        mBinding.dashboardModel = mViewModel.getDashboardModel()
        setToolbar(mBinding.toolbarLayout, "MPCB")
        mViewModel.getDashboardData()


        mBinding.monthLayOne.setOnClickListener {
            setAllView(R.drawable.cal_back_select, R.drawable.cal_back_unselect, mBinding.monthLayOne)
            setAllText(R.color.white,R.color.black,mBinding.tvMonthOne,mBinding.tvYearOne)
        }

        mBinding.monthLayTwo.setOnClickListener {
            setAllView(R.drawable.cal_back_select, R.drawable.cal_back_unselect, mBinding.monthLayTwo)
            setAllText(R.color.white,R.color.black,mBinding.tvMonthTwo,mBinding.tvYearTwo)
        }


        mBinding.monthLayThree.setOnClickListener {
            setAllView(R.drawable.cal_back_select, R.drawable.cal_back_unselect, mBinding.monthLayThree)
            setAllText(R.color.white,R.color.black,mBinding.tvMonthThree,mBinding.tvYearThree)
        }


        mBinding.monthLayFour.setOnClickListener {
            setAllView(R.drawable.cal_back_select, R.drawable.cal_back_unselect, mBinding.monthLayFour)
            setAllText(R.color.white,R.color.black,mBinding.tvMonthFour,mBinding.tvYearFour)
        }


    }


    private fun setAllView(selecteColor:Int, unselectCol:Int,vararg views: View){

        setCalendarView(unselectCol, mBinding.monthLayOne,mBinding.monthLayTwo,mBinding.monthLayThree, mBinding.monthLayFour)
        setCalendarView(selecteColor, *views)
    }

    private fun setAllText(selecteColor:Int, unselectCol:Int,vararg txtvws: TextView){
        setCalText(unselectCol,mBinding.tvMonthOne,mBinding.tvYearOne,
                               mBinding.tvMonthTwo,mBinding.tvYearTwo,
                               mBinding.tvMonthThree,mBinding.tvYearThree,
                               mBinding.tvMonthFour,mBinding.tvYearFour)

        setCalText(selecteColor, *txtvws)
    }

    private fun setCalendarView(res:Int,vararg views: View){
        for(view in views){
          view.setBackgroundResource(res)
        }
    }

    private fun setCalText(colorCode: Int, vararg txtvws: TextView){
        for (tv in txtvws){
            tv.setTextColor(ContextCompat.getColor(getBaseActivity(), colorCode))
        }

    }

}
