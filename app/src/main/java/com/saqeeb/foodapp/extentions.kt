package com.saqeeb.foodapp

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import java.io.InputStream


@BindingAdapter("networkSrc")
fun ImageView.networkSrc(url: String) {
    if (url.startsWith("http")) {
        Glide.with(this.context).load(url).into(this)
    } else {
        val file = Uri.parse(url)
        Glide.with(this.context).load(file.getImageFromUri(this.context)).into(this)


    }
}

private const val CLICK_THROTTLE_TIME = 2000L // 2 seconds
private val clickHandler = Handler()

fun View.throttleClicks() {
    if (isEnabled) {
        isEnabled = false
        clickHandler.postDelayed({
            isEnabled = true
        }, CLICK_THROTTLE_TIME)
    }

}

fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun Uri.getImageFromUri(context: Context): Bitmap? {
    val contentResolver: ContentResolver = context.contentResolver
    var inputStream: InputStream? = null
    var bitmap: Bitmap? = null
    try {
        inputStream = contentResolver.openInputStream(this)
        bitmap = BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return bitmap
}
fun Double.roundToTwoDecimalPoints(): Double {
    return String.format("%.2f", this).toDouble()
}