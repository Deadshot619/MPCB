package com.gov.mpcb.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.gov.mpcb.base.MPCBApp
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*


enum class LoadingStatus { LOADING, ERROR, DONE }
enum class ListData { LOADING, PRESENT, EMPTY }

//This enum class will be used to data for [ViewIndustryDirectoryDataRequest]
enum class IndustryDirectoryType(val value: String) {
    Consent("CONSENT"), Authorization("AUTH"), Submission("SUBM"),
    BankGuarantee("BG"), Visits("VISIT"), Legal("LEGAL")
}

object CommonUtils {
    //Get Device's Display Metrics
    private val displayMetrics = MPCBApp.instance.resources.displayMetrics

    //Get Device's height in dp
    val dpHeight: Float
        get() = displayMetrics.heightPixels / displayMetrics.density

    //Get Device's width in dp
    val dpWidth: Float
        get() = displayMetrics.widthPixels / displayMetrics.density

    /**
     * This method is used to show Date Picker Dialog. This will set the date in the
     * given view/[id].
     *
     * @param context Takes a context as parameter
     * @param id Takes an EditText view as input
     */
    fun showDateDialog(context: Context, id: EditText, hidePreviousDates: Boolean = false) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                id.setText("$year-${month + 1}-$dayOfMonth")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply { if (hidePreviousDates) datePicker.minDate = calendar.timeInMillis - 1000 }.show()
    }

    /**
     * This method will redirect the user to a browser(if it exists) with the url provided.
     *
     * @param context takes a context as input
     * @param url takes a string of url as input
     */
    fun redirectUserToBrowser(activity: Activity, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val title = "Please choose a app..."
        val chooser = Intent.createChooser(intent, title)

        //check if there are apps to open this url
        if (intent.resolveActivity(activity.packageManager!!) != null) {
            activity.startActivity(chooser)
        } else {
            activity.showMessage("You don't have any apps to open this link with.")
        }
    }

    /**
     * Method to download pdf from the provided url
     *
     * @param context Takes a context
     * @param url takes a url as String
     * @param fileName takes a name as String
     */
    fun checkPermissionAndDownloadPdf(context: Activity, view: View, url: String, fileName: String) {
        Dexter.withContext(context)
            .withPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val downloadFileRef = downloadPdf(context, url, fileName)
                    if (downloadFileRef != 0L)
                        context.showMessage("Starting download...")
//                    Toast.makeText(context, "Starting download...", Toast.LENGTH_SHORT).show()
                    else
                        context.showMessage("File is not available for download")
//                    Toast.makeText(context, "File is not available for download", Toast.LENGTH_SHORT).show()

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    if (p0!!.isPermanentlyDenied) {
                        Snackbar.make(
                            view,
                            "You need to provide Storage permission to download the file.",

                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Open") {
                                val intent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        data = Uri.fromParts("package", context.packageName, null)
                                    }
                                context.startActivity(intent)
                            }
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            "You need to accept this permission to download the file.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }).check()

    }

    /**
     * Method to download pdf using Download Manager
     */
    fun downloadPdf(context: Activity, url: String, fileName: String): Long {
        var downloadReference: Long = 0
        val downloadManager = (context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager)

        try {

            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setTitle(fileName)
                setDescription("Downloading...")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "${fileName}.pdf"
                )
            }
            downloadReference = downloadManager.enqueue(request)
        }catch (e: IllegalArgumentException){
            context.showMessage("Download link is broken or not available for download")
//            Toast.makeText(context, "Download link is broken or not available for download", Toast.LENGTH_SHORT).show()


        }
        return downloadReference
    }

    /**
     * Method to download pdf using PR Download Manager
     */
    /*fun downloadPdfPr(context: Context, url: String, fileName: String){
        var filePath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath

        Toast.makeText(context, "Starting download...", Toast.LENGTH_SHORT).show()

        var downloadId = PRDownloader.download(url, filePath, fileName)
            .build()
            .start(object : OnDownloadListener{
                override fun onDownloadComplete() {
                    Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: Error?) {
                    Toast.makeText(context, "Download Error + ${error?.toString()}", Toast.LENGTH_SHORT).show()
                    Log.d("PR", error?.connectionException?.message ?: "")
                }

            })
    }*/
}