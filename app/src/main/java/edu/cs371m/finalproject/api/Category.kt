package edu.cs371m.finalproject.api

import android.text.SpannableString
import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("id")
    val idCategory: String,
    @SerializedName("name")
    val strCategory: String,
    @SerializedName("thumbnail")
    val strCategoryThumb: String,
    @SerializedName("public_description")
    val strCategoryDescription: SpannableString?
)