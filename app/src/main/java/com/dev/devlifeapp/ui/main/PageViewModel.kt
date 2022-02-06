package com.dev.devlifeapp.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dev.devlifeapp.common.Common
import com.dev.devlifeapp.interfaces.RetrofitServices
import com.dev.devlifeapp.model.Life
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PageViewModel : ViewModel() {

    lateinit var mService: RetrofitServices

    val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    val index: LiveData<Int> = Transformations.map(_index) {
        it
    }

    var context: Context? = null

    var imageView: Bitmap? = null

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getIndex(): Int? {
        return _index.value
    }
}