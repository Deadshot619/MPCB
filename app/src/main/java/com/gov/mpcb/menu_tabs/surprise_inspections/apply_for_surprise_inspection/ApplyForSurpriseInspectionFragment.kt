package com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection

import android.view.View
import androidx.fragment.app.Fragment
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

    private lateinit var viewAvailableIndustriesData: ViewAvailableIndustriesData

    override fun getLayoutId() = R.layout.fragment_apply_for_surprise_inspection
    override fun getViewModel() = ApplyForSurpriseInspectionViewModel::class.java
    override fun getNavigator() = this@ApplyForSurpriseInspectionFragment
    override fun onError(message: String) = showMessage(message)
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

//        setUpRecyclerView(mBinding.rvIndustryList)

        setObservers()
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
                CommonUtils.showDateDialog(
                    context = getBaseActivity(),
                    id = layoutApplySurpriseInspection.edtSurpriseInspectionConductedOnInfo
                )
            }

            //SAVE button : Validate whether the form is filled or not
            layoutApplySurpriseInspection.btnSave.setOnClickListener {
                showMessage("" + validate())
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
        return !mBinding.layoutApplySurpriseInspection.run {
            edtSurpriseInspectionConductedOnInfo.text.isNullOrEmpty() || edtReasonInfo.text.isNullOrEmpty()
        }
    }

}
