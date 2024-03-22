package com.saqeeb.foodapp

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import retrofit2.Response

fun <T> Response<T>.isValid():Boolean{
    return isSuccessful && code() == 200 && body()!=null
}

@BindingAdapter("networkSrc")
fun ImageView.networkSrc(url:String){
    Glide.with(this.context).load(url).into(this).onLoadFailed(ContextCompat.getDrawable(this.context,R.drawable.avatars))
}