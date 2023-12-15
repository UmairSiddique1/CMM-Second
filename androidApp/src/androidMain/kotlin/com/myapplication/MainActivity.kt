package com.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import com.myapplication.screens.HomeScreen
import com.myapplication.screens.ViewAllScreen


class MainActivity : AppCompatActivity() {
    private val STORAGE_PERMISSION_CODE = 23
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
//                HomeScreen.SearchBarSample()
//                HomeScreen.Tools()
//                HomeScreen.RecentFiles()
//                HomeScreen.BottomNav()
//                SearchScreen.SearchScreenLayout()

               ViewAllScreen.TopBar(applicationContext                       )
            }
//requestForStoragePermissions()
        }
    }
    private val storageActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                //Android is 11 (R) or above
                if (Environment.isExternalStorageManager()) {
                    //Manage External Storage Permissions Granted
                    Toast.makeText(
                        this@MainActivity,
                        "Storage Permissions Granted",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Storage Permissions Denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                //Below android 11
            }
        }

    @Composable
    private fun RequestForStoragePermissions() {
        //Android is 11 (R) or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

             if(!Environment.isExternalStorageManager()){
                 launchStoragePermissionSettings()
             }
                else{
                    Toast.makeText(applicationContext,"Storage Permission granted",Toast.LENGTH_SHORT).show()
                 ViewAllScreen.FetchFilesData(applicationContext)
             }

        } else {
            //Below android 11
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun launchStoragePermissionSettings() {
        try {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            storageActivityResultLauncher.launch(intent)
        } catch (e: Exception) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            storageActivityResultLauncher.launch(intent)
        }
    }
}