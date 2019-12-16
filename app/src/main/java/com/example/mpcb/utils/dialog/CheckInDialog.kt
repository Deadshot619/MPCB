package com.example.mpcb.utils.dialog

import android.Manifest
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.CameraCharacteristics
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mpcb.BuildConfig
import com.example.mpcb.R
import com.example.mpcb.base.CheckInCallBack
import com.example.mpcb.databinding.CheckInPopupBinding
import com.example.mpcb.my_visits.MyVisitsViewModel
import com.example.mpcb.network.response.CheckInfoModel
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.permission.PermissionUtils
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CheckInDialog(context: Context, val model: MyVisitModel, val mViewModel: MyVisitsViewModel) :
    DialogFragment(), CheckInCallBack {


    private val REQUEST_CAMERA_CODE: Int = 100
    private var printImageFilePath: String = ""
    lateinit var photoURI: Uri
    private var dialogBinding = CheckInPopupBinding.inflate(LayoutInflater.from(context))

    companion object {
        fun newInstance(
            activity: Context,
            model: MyVisitModel,
            mViewModel: MyVisitsViewModel
        ): CheckInDialog {
            val args = Bundle()
            val fragment = CheckInDialog(activity, model, mViewModel)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null && dialog.window != null) {
            dialog.window!!.run{
                setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (dialog != null && dialog!!.window != null)
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)

        return dialogBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialogBinding.model = model
        dialogBinding.viewModel = mViewModel

        //if not checked in
        if (model.checkInStatus != 1) {
            dialogBinding.model!!.apply {
                latitude = PreferencesHelper.getCurrentLatitude()
                longitude = PreferencesHelper.getCurrentLongitude()
                dialogBinding.latitudeEd.setText(PreferencesHelper.getCurrentLatitude())
                dialogBinding.longitudeEd.setText(PreferencesHelper.getCurrentLongitude())
                //Show Check in Button
                dialogBinding.checkInBtn.visibility = View.VISIBLE
            }

            //Set listener to Check-In button
            dialogBinding.checkInBtn.setOnClickListener {
                mViewModel.onSubmitClicked(
                    PreferencesHelper.getStringPreference(Constants.IMAGE_PATH)!!,
                    model.visitSchedulerId
                )
            }

            //Set listener to Camera button
            dialogBinding.appCompatImageView.setOnClickListener {
                val cameraPermission =
                    PermissionUtils.checkPermision(activity!!, Manifest.permission.CAMERA)
                if (cameraPermission)
                    startCamera()
                else
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_CODE)
            }
        } else {//if already checked in
            mViewModel.onCheckInfoClicked(model) { data ->
                data.apply {
                    dialogBinding.latitudeEd.setText(data.latitude)
                    dialogBinding.longitudeEd.setText(data.longitude)

                    data.selfieImage?.let {


                        val imgUri = it.toUri().buildUpon().scheme("http").build()
                        Glide.with(this@CheckInDialog)
                            .load(imgUri)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.ic_checkin_profile)
                                    .error(R.drawable.ic_broken_image_black_24dp))
                            .into(dialogBinding.appCompatImageView)

                        dialogBinding.appCompatImageView.run {
                            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            layoutParams.width= ViewGroup.LayoutParams.WRAP_CONTENT
                            setPadding(0,0,0,24)
                            isClickable = false
                        }
                    }
                }

            }


        }

        //Set listener
        dialogBinding.btnCancel.setOnClickListener { dismiss() }
    }

    override fun getCheckInfo(): CheckInfoModel {

        val models = CheckInfoModel()
        Log.d("CheclInfo MOdel", models.toString())

        return models
    }


    private fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.apply {
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.O -> {
                        takePictureIntent.putExtra(
                            "android.intent.extras.CAMERA_FACING",
                            CameraCharacteristics.LENS_FACING_FRONT
                        )  // Tested on API 24 Android version 7.0(Samsung S6)
                    }
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                        takePictureIntent.putExtra(
                            "android.intent.extras.CAMERA_FACING",
                            CameraCharacteristics.LENS_FACING_FRONT
                        ) // Tested on API 27 Android version 8.0(Nexus 6P)
                        takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true)
                    }
                    else -> takePictureIntent.putExtra(
                        "android.intent.extras.CAMERA_FACING",
                        1
                    )  // Tested API 21 Android version 5.0.1(Samsung S4)
                }
            }.resolveActivity(activity!!.packageManager)?.also {
                val photoFile = try {
                    createImageFile()
                } catch (e: Exception) {
                    null
                }
                photoFile?.also {
                    val photoUri = FileProvider.getUriForFile(
                        activity!!,
                        BuildConfig.APPLICATION_ID + ".provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        takePictureIntent.clipData = ClipData.newRawUri("", photoUri)
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    }
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_CODE) {
            startCamera()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir = activity!!.filesDir
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            printImageFilePath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA_CODE) {
            try {
                val printedImageFile = File(printImageFilePath)
                Log.e("file path", printedImageFile.absolutePath)
                PreferencesHelper.setStringPreference(Constants.IMAGE_PATH, printImageFilePath)
            } catch (e: Throwable) {
                e.stackTrace
            }
        }
    }
}