package com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentApplyForSurpriseInspectionBinding
import com.gov.mpcb.databinding.ItemApplySurpriseInspectionBinding
import com.gov.mpcb.network.response.ViewAvailableIndustriesData
import com.gov.mpcb.utils.CommonUtils
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class ApplyForSurpriseInspectionFragment :
    BaseFragment<FragmentApplyForSurpriseInspectionBinding, ApplyForSurpriseInspectionViewModel>(),
    ApplyForSurpriseInspectionNavigator {

    //Variable to store visibility status of application status
    private var LAYOUT_HIDDEN = true

    private lateinit var mAdapter: PreviouslyConductedInspectionAdapter

    private lateinit var viewAvailableIndustriesData: ViewAvailableIndustriesData

    override fun getLayoutId() = R.layout.fragment_apply_for_surprise_inspection
    override fun getViewModel() = ApplyForSurpriseInspectionViewModel::class.java
    override fun getNavigator() = this@ApplyForSurpriseInspectionFragment
    override fun onError(message: String) = showMessage(message)
    override fun showToast(msg: String) = showMessage(msg)
    override fun openActivity(activity: Activity) {
        startActivity(Intent(getBaseActivity(), activity::class.java).apply {
            putExtra(Constants.RELOAD_KEY, Constants.RELOAD_VALUE)

            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
        this.activity?.finish()
    }

    override fun onInternetError() {}
    override fun onBinding() {
        //Get industry data from bundle
        viewAvailableIndustriesData = arguments?.getParcelable(
            Constants.VIEW_AVAILABLE_INDUSTRY_DATA
        )!!

        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.apply_for_surprise_inspection),
            showSearchBar = false,
            showCalendar = false,
            showBackButton = true
        )

        //Set lifecycleOwner & ViewModel to this view
        mBinding.run{
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }

        setViews(
            data = viewAvailableIndustriesData,
            layout = mBinding.layoutApplySurpriseInspection
        )

        setListeners()

        setUpRecyclerView(mBinding.rvPreviouslyConductedInspection)

        setObservers()

        mViewModel.loadPreviouslyConductedInspections(viewAvailableIndustriesData.industry_iin)
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        mAdapter = PreviouslyConductedInspectionAdapter(PreviouslyConductedInspectionAdapter.OnClickListener{ url ->
            //Open Browser
            CommonUtils.redirectUserToBrowser(activity!!, url)
        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }

    /**
     * This method sets data to the provided [ItemApplySurpriseInspectionBinding] layout
     */
    private fun setViews(
        data: ViewAvailableIndustriesData,
        layout: ItemApplySurpriseInspectionBinding
    ) {
        layout.run {
            tvIndustryRefNoInfo.text = data.industry_iin
            tvIndustryNameInfo.text = data.industry_name
            tvIndustryAddressInfo.text = data.industry_address
        }
    }

    /**
     * Method to set all types of listeners for this fragment
     */
    private fun setListeners() {

        mBinding.run {
            //Destroy the activity on click of back button
            toolbarLayout.imgBack.setOnClickListener {
                activity?.onBackPressed()
            }

            //Show/Hide lower form layout
            layoutApplySurpriseInspection.run {
                upperLayout.setOnClickListener {
                    //If [LAYOUT_HIDDEN] is true, then show the application form & set its value to false,
                    //If [LAYOUT_HIDDEN] is false, then hide the application form & set its value to true,
                    if (LAYOUT_HIDDEN) {
                        lowerLayout.visibility = View.VISIBLE
                        LAYOUT_HIDDEN = false
                    } else {
                        lowerLayout.visibility = View.GONE
                        LAYOUT_HIDDEN = true
                    }
                }
            }

            //Show Date Picker Dialog on click of editText
            layoutApplySurpriseInspection.edtSurpriseInspectionConductedOnInfo.setOnClickListener {
                layoutApplySurpriseInspection.edtSurpriseInspectionConductedOnInfo.error = null
                CommonUtils.showDateDialog(
                    context = getBaseActivity(),
                    id = layoutApplySurpriseInspection.edtSurpriseInspectionConductedOnInfo,
                    hidePreviousDates = true
                )
            }

            //SAVE button : Validate whether the form is filled or not
            layoutApplySurpriseInspection.btnSave.setOnClickListener {
                if (validate()){
                    mViewModel.submitInspectionForm(
                        industryInn = viewAvailableIndustriesData.industry_iin,
                        date = mBinding.layoutApplySurpriseInspection.edtSurpriseInspectionConductedOnInfo.text.toString(),
                        reason = mBinding.layoutApplySurpriseInspection.edtReasonInfo.text.toString()
                    )
                }
            }

            //Clear text on click of 'cancel' button.
            layoutApplySurpriseInspection.btnCancel.setOnClickListener {
                layoutApplySurpriseInspection.edtSurpriseInspectionConductedOnInfo.setText("")
                layoutApplySurpriseInspection.edtReasonInfo.setText("")
            }
        }
    }

    private fun setObservers() {}

    /**
     * This method checks if the Application form is filled or not
     *
     * @return Returns true if all the fields are filled else false.
     */
    private fun validate(): Boolean {
        mBinding.layoutApplySurpriseInspection.run {
            //check if date is filled
            if (edtSurpriseInspectionConductedOnInfo.text.isNullOrEmpty()){
                showMessage("Please select a date")
                return false
            }

            //check if reason is filled
            if (edtReasonInfo.text.isNullOrEmpty()){
                showMessage("Please enter valid reason")
                return false
            }

            return true
        }
    }

}
