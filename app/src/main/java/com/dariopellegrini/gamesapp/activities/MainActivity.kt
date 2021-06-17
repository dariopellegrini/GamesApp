package com.dariopellegrini.gamesapp.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dariopellegrini.gamesapp.R
import com.dariopellegrini.gamesapp.repository.Repository
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login()
    }

    private fun login() = CoroutineScope(Dispatchers.Main).launch {
        try {
//            Repository.deleteUser()

            val alreadyLogged = Repository.loggedUserExists()
            if (!alreadyLogged) {
                val user = Repository.login("info3@dariopellegrini.com", "superpassword")
                val loggedAfterLogin = Repository.loggedUserExists()
                Log.i("MainActivityLogin", "$user")
                Log.i("MainActivityLogin", "$loggedAfterLogin")
            }
            Log.i("MainActivityLogin", "Go to games")
        } catch (e: Exception) {
            Log.e("MainActivityLogin", "$e")
        }
    }

    // Image
    private var selectedImageByteArray: ByteArray? = null
    private fun checkPermissions(completion: () -> Unit) {
        val permissionListener = object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                completion()
            }

            override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
            ) {
            }
        }

        val snackbarMultiplePermissionsListener: MultiplePermissionsListener =
                SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                        .with(constraintLayout, "Permission necessary")
                        .withOpenSettingsButton("Settings")
                        .withCallback(object : Snackbar.Callback() {
                            override fun onShown(snackbar: Snackbar) {
                            }

                            override fun onDismissed(snackbar: Snackbar, event: Int) {
                            }
                        })
                        .build()
        val compositePermissionsListener = CompositeMultiplePermissionsListener(permissionListener, snackbarMultiplePermissionsListener)
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(compositePermissionsListener).check()
    }

    private fun pickImage() {
        ImagePicker.with(this)
                .crop()
                .compress(750)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(700, 700)  //Final image resolution will be less than 1080 x 1080(Optional)
                .start(54321)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data
//            imageView.setImageURI(fileUri)
//
//            CoroutineScope(Dispatchers.Main).launch {
//                selectedImageByteArray = getByteArray(imageView.drawable)
//            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun getByteArray(drawable: Drawable?): ByteArray? = withContext(Dispatchers.Default) {
        val bitmap = ((drawable as? BitmapDrawable)?.bitmap) ?: return@withContext null
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.toByteArray()
    }
}