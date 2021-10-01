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
import com.yaromchikv.thecatapi.model.Cat
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application

    fun saveTheCat(cat: Cat) = viewModelScope.launch {
        val filename = "${cat.id}.jpeg"
        val request = ImageRequest.Builder(app).data(cat.imageUrl).build()
        try {
            val imageLoader = Coil.imageLoader(app)
            val bitmap = (imageLoader.execute(request).drawable as BitmapDrawable).bitmap
            saveMediaToStorage(bitmap, filename)
        } catch (e: IllegalAccessException) {
            Toast.makeText(app, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun saveMediaToStorage(bitmap: Bitmap, filename: String) {
        var outputStream: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            app.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, DIRECTORY)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(DIRECTORY)
            val image = File(imagesDir, filename)
            outputStream = FileOutputStream(image)
        }
        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUALITY, it)
            Toast.makeText(
                app,
                app.getString(R.string.saved_in_pictures, filename, DIRECTORY),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val DIRECTORY = "Pictures/TheCatApi"
        private const val BITMAP_QUALITY = 100
    }
}
