package edu.cs371m.finalproject.api

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.text.clearSpans
import com.google.gson.annotations.SerializedName

data class Categories (
    @SerializedName("id")
    val idCategory: String,
    @SerializedName("name")
    val strCategory: String,
    @SerializedName("thumbnail")
    val strCategoryThumb: String,
    @SerializedName("public_description")
    val strCategoryDescription: SpannableString?
)