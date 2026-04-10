package com.statushub.india.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ShareUtils {
    fun shareImage(context: Context, bitmap: Bitmap, title: String) {
        try {
            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val stream = FileOutputStream("$cachePath/image.png")
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

            val newFile = File(cachePath, "image.png")
            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                newFile
            )

            if (contentUri != null) {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    setDataAndType(contentUri, context.contentResolver.getType(contentUri))
                    putExtra(Intent.EXTRA_STREAM, contentUri)
                    type = "image/png"
                }
                context.startActivity(Intent.createChooser(shareIntent, title))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun shareImageUri(context: Context, uri: Uri, title: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/jpeg"
        }
        context.startActivity(Intent.createChooser(shareIntent, title))
    }
}
