package com.ewayapp.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.devkanhaiya.bookreader.BuildConfig
import java.io.File
import java.io.IOException


object AppUtil {
    fun createImageFile(activity: Activity, prefix: String): FileUri? {
        val fileUri = FileUri()

        var image: File? = null
        try {
            image = File.createTempFile(
                prefix + System.currentTimeMillis().toString(),
                ".jpg",
                getWorkingDirectory()
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (image != null) {
            fileUri.file = image
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileUri.imageUrl =
                    (FileProvider.getUriForFile(
                        activity,
                        BuildConfig.APPLICATION_ID + ".fileprovider",
                        image
                    ))
            } else {
                fileUri.imageUrl = (Uri.parse("file:" + image.absolutePath))
            }
        }
        return fileUri
    }

    fun getOSVersion(): String {
        val fields = Build.VERSION_CODES::class.java.fields
        var codeName = "UNKNOWN"
        fields.filter { it.getInt(Build.VERSION_CODES::class) == Build.VERSION.SDK_INT }
            .forEach { codeName = it.name }
        return codeName
    }

    fun getWorkingDirectory(): File {
        val directory = File(Environment.getExternalStorageDirectory(), BuildConfig.APPLICATION_ID)
        if (!directory.exists()) {
            directory.mkdir()
        }
        return directory
    }

    fun loadImages(context: Context, imageURL: String, appCompatImageView: AppCompatImageView) {
        Glide.with(context).load(imageURL).apply(RequestOptions.centerCropTransform())
            .into(appCompatImageView)
    }

    fun loadImageswithImageLoading(
        context: Context,
        imageURL: Any,
        appCompatImageView: AppCompatImageView,
        imageProgress: ProgressBar
    ) {
        Glide.with(context)
            .load(imageURL)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageProgress.setVisibility(View.GONE);
                    return false;

                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageProgress.setVisibility(View.GONE);
                    return false;
                }

            })
            .into(appCompatImageView)
    }
}