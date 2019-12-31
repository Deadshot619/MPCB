package com.example.mpcb.reports.additional_info


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentAdditionalInfoBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.constants.Constants.Companion.ADDITIONAL_INFO_TEXT
import com.example.mpcb.utils.permission.PermissionUtils
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.getReportFlagStatus
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.getStringPreference
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.setStringPreference
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.FilePickUtils
import java.io.File


class AdditionalInfoFragment :
    BaseFragment<FragmentAdditionalInfoBinding, AdditionalInfoViewModel>(),
    AdditionalInfoNavigator {

    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

     private lateinit var fileP: String

    //File to be uploaded
//    private val _file: File
//        get() = File(fileP)

    //File Uri
    private lateinit var fileUri: Uri
    //File Path
    private lateinit var filePath: String

    val PICKFILE_RESULT_CODE = 1000

    override fun getLayoutId() = R.layout.fragment_additional_info
    override fun getViewModel() = AdditionalInfoViewModel::class.java
    override fun getNavigator() = this@AdditionalInfoFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_18)
        setListener()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)


        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
            btnNext.apply {
                text = "Done"
                setOnClickListener { activity?.finish() }
            }
        }

        mBinding.uploadVisitEditTextLayout.setOnClickListener {
           // showMessage("Clicked!")


            if (!PermissionUtils.isStoragePermissionsGranted(ctx = activity!!)){


                if (activity!!.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        PermissionUtils.STORAGE_PERMISSIONS,
                        100
                    )
                } else {
                    pickFile()
                }



        }else{
                pickFile()
            }
        }
    }

    /**
     * This method creates an intent to choose a file
     */
    private fun pickFile(){

        val mimeTypes = arrayOf("image/*", "application/pdf")

        var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.type = "image/*|application/pdf"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            chooseFile.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        chooseFile = Intent.createChooser(chooseFile, "Choose a file")
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE)

    }

    private fun setListener() {
        mBinding.rgUnitComplied.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.legalActionUnitComplied =
                if (checkedId == R.id.rbUnitYes) 1 else 0
        }
//
        mBinding.edtAddInfo.addTextChangedListener{
            //Save data of additional info text in shared pref
            saveAdditionalInfoTextTemporary(mBinding.edtAddInfo.text.toString())
        }
    }

    private fun onSubmit() {
        report.data.routineReport.additionalInfo = mBinding.edtAddInfo.text.toString()

        //override additional info data in shared pref
        saveAdditionalInfoTextTemporary()

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_18,
                reportStatus = true
            )

            //submit report only if all the reports are filled.
            if (checkIfReportsFilled()) {
                mViewModel.submitReport(
                    reportRequest = getReportData(visitReportId),
                    file = File(fileP)
                )
//                mViewModel.uploadVisitFile(File(fileP))
            } else {
                showMessage("Please fill all the reports!")
            }

        }
    }

    /**
     * This method is used to check if all the reports are filled.
     *  @return returns false if even one of the report is not filled. otherwise true
     */
    private fun checkIfReportsFilled(): Boolean {
        //"i" represents report key
        for (i in 1..18) {
            if (!getReportFlagStatus(visitReportId, i)) {
                return false
            }
        }
        return true
    }

    private fun validate(): Boolean {
        if (report.data.routineReport.additionalInfo.isNullOrEmpty()) {
            showMessage("Enter Additional Information")
            return false
        }
        if (report.data.routineReport.legalActionUnitComplied == null) {
            showMessage("Select Unit Compiled")
            return false
        }
        if (mBinding.uploadVisitEditTextLayout.text.isNullOrEmpty()) {
            showMessage("Select a File")
            return false
        }
        return true
    }

    override fun onSubmitReportSuccess(msg: String) {
        showMessage(msg)
        activity!!.finish()
    }

    /**
     * Methods to save & retrieve data in shared pref temporarily
     */
    private fun saveAdditionalInfoTextTemporary(value: String = ""){
        setStringPreference(visitReportId + ADDITIONAL_INFO_TEXT, value)
    }

    private fun getAdditionalInfoTextTemporary(): String? =
        getStringPreference(visitReportId + ADDITIONAL_INFO_TEXT)


    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        reports = if (visitStatus) {
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }

        if (reports != null) {
            mBinding.run {
                reports?.data?.routineReport?.run {
                    //Additional Info
                    if (!getAdditionalInfoTextTemporary().isNullOrEmpty()){
                        mBinding.edtAddInfo.setText(getAdditionalInfoTextTemporary())
                    }else {
                        edtAddInfo.setText(additionalInfo)
                    }

                    //Whether Unit Complied
                    if (legalActionUnitComplied == 1)
                        rgUnitComplied.check(R.id.rbUnitYes)
                    else
                        rgUnitComplied.check(R.id.rbUnitNo)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        setDataToViews()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        when (requestCode) {
            PICKFILE_RESULT_CODE -> if (resultCode == -1) {
                //File Uri
                fileUri = data?.data!!
                //File Path
             //   getRealPathFromURI(activity)

                fileP =
                    if (FilePickUtils.getSmartFilePath(activity!!,fileUri) == null)
                        ""
                    else
                        FilePickUtils.getSmartFilePath(activity!!,fileUri).toString()

                filePath = fileUri.path!!
                mBinding.uploadVisitEditTextLayout.setText(File(fileP).name)

              //  val visitReportBodyLocal = RequestBody.create(MediaType.parse("image/*"), _file.absoluteFile)
//                val visitReportBodyLocal = RequestBody.create(MediaType.parse("image/*"),fileP)
//                val visitReportPartLocal =
//                    MultipartBody.Part.createFormData("file", _file.name, visitReportBodyLocal)
            }
        }
    }










}