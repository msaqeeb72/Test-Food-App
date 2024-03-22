package com.saqeeb.foodapp

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Response

fun <T> Response<T>.isValid():Boolean{
    return isSuccessful && code() == 200 && body()!=null
}

@BindingAdapter("networkSrc")
fun ImageView.networkSrc(url:String){
    Glide.with(this.context).load(url).into(this).onLoadFailed(ContextCompat.getDrawable(this.context,R.drawable.avatars))
}

private const val CLICK_THROTTLE_TIME = 2000L // 2 seconds
private val clickHandler = Handler()

fun View.throttleClicks() {
    if(isEnabled) {
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