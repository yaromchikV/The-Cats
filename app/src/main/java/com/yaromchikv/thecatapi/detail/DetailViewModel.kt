package com.yaromchikv.thecatapi.detail

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.request.ImageRequest
import com.yaromchikv.thecatapi.R
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application

    fun saveTheCatImage(imageUrl: String) = viewModelScope.launch {
        val request = ImageRequest.Builder(app.applicationContext).data(imageUrl).build()
        try {
            val imageLoader = Coil.imageLoader(app.applicationContext)
            val bitmap = (imageLoader.execute(request).drawable as BitmapDrawable).bitmap
            saveMediaToStorage(bitmap)
        } catch (e: IllegalAccessException) {
            Toast.makeText(app.applicationContext, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var outputStream: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            app.applicationContext.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            outputStream = FileOutputStream(image)
        }
        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUALITY, it)
            Toast.makeText(
                app.applicationContext,
                app.getString(R.string.saved_in_pictures, Environment.DIRECTORY_PICTURES),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val BITMAP_QUALITY = 100
    }
}
