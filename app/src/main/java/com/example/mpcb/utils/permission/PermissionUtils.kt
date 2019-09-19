package com.example.mpcb.utils.permission

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtils {

    val READ_PHONE_STATE:String = Manifest.permission.READ_PHONE_STATE;
    val READ_EXTERNAL_STORAGE:String = Manifest.permission.READ_PHONE_STATE;
    val WRITE_EXTERNAL_STORAGE:String = Manifest.permission.WRITE_EXTERNAL_STORAGE;


    companion object {

        val STORAGE_PERMISSIONS = arrayOf(
            READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
        )

        fun checkPermissions(ctx: Context, vararg permissions: String): Boolean{
            for (per in permissions){
                if(!checkPermision(ctx,per))
                return false;
            }
            return false
        }

        fun checkPermision(ctx:Context, permission:String):Boolean{
            return ContextCompat.checkSelfPermission(ctx, permission) == PackageManager.PERMISSION_GRANTED
        }


        fun isPermissionsGranted(ctx:Context, vararg permissions: String):Boolean{
            return checkPermissions(ctx, *permissions)
        }

        fun requestPermissions(obj: Any, requestCode:Int, vararg permissions: String){
            if(obj is Activity){
                ActivityCompat.requestPermissions(obj, permissions,requestCode)
            }
        }

        fun isAllPermissionsGranted(grantResults: IntArray, lenght:Int):Boolean {
            for(res in grantResults){
                if(res != PackageManager.PERMISSION_GRANTED)
                    return false
            }
            return true;
        }


        //Write functions for particular permission call
        fun isStoragePermissionsGranted(ctx:Context):Boolean{
            return checkPermissions(ctx, *STORAGE_PERMISSIONS)
        }


    }
}