package edu.cs371m.finalproject.api

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.google.gson.annotations.SerializedName

data class Meal (
    @SerializedName("id")
    val idMeal: String,
    @SerializedName("name")
    val strMeal: SpannableString,
    @SerializedName("thumbnail")
    val strMealThumb: String,
    //add for recipe details
    @SerializedName("drinkalternate")
    val strDrinkAlternate: String?,
    @SerializedName("category")
    val strCategory: SpannableString?,
    @SerializedName("area")
    val strArea: String?,
    @SerializedName("instruction")
    val strInstructions: String?,
    @SerializedName("tag")
    val strTags: String?,
    @SerializedName("video")
    val strYoutube: String?,
    @SerializedName("ingredient1")
    val strIngredient1: String?,
    @SerializedName("ingredient2")
    val strIngredient2: String?,
    @SerializedName("ingredient3")
    val strIngredient3: String?,
    @SerializedName("ingredient4")
    val strIngredient4: String?,
    @SerializedName("ingredient5")
    val strIngredient5: String?,
    @SerializedName("ingredient6")
    val strIngredient6: String?,
    @SerializedName("ingredient7")
    val strIngredient7: String?,
    @SerializedName("ingredient8")
    val strIngredient8: String?,
    @SerializedName("ingredient9")
    val strIngredient9: String?,
    @SerializedName("ingredient10")
    val strIngredient10: String?,
    @SerializedName("ingredient11")
    val strIngredient11: String?,
    @SerializedName("ingredient12")
    val strIngredient12: String?,
    @SerializedName("ingredient13")
    val strIngredient13: String?,
    @SerializedName("ingredient14")
    val strIngredient14: String?,
    @SerializedName("ingredient15")
    val strIngredient15: String?,
    @SerializedName("ingredient16")
    val strIngredient16: String?,
    @SerializedName("ingredient17")
    val strIngredient17: String?,
    @SerializedName("ingredient18")
    val strIngredient18: String?,
    @SerializedName("ingredient19")
    val strIngredient19: String?,
    @SerializedName("ingredient20")
    val strIngredient20: String?,
    @SerializedName("measure1")
    val strMeasure1: String?,
    @SerializedName("measure2")
    val strMeasure2: String?,
    @SerializedName("measure3")
    val strMeasure3: String?,
    @SerializedName("measure4")
    val strMeasure4: String?,
    @SerializedName("measure5")
    val strMeasure5: String?,
    @SerializedName("measure6")
    val strMeasure6: String?,
    @SerializedName("measure7")
    val strMeasure7: String?,
    @SerializedName("measure8")
    val strMeasure8: String?,
    @SerializedName("measure9")
    val strMeasure9: String?,
    @SerializedName("measure10")
    val strMeasure10: String?,
    @SerializedName("measure11")
    val strMeasure11: String?,
    @SerializedName("measure12")
    val strMeasure12: String?,
    @SerializedName("measure13")
    val strMeasure13: String?,
    @SerializedName("measure14")
    val strMeasure14: String?,
    @SerializedName("measure15")
    val strMeasure15: String?,
    @SerializedName("measure16")
    val strMeasure16: String?,
    @SerializedName("measure17")
    val strMeasure17: String?,
    @SerializedName("measure18")
    val strMeasure18: String?,
    @SerializedName("measure19")
    val strMeasure19: String?,
    @SerializedName("measure20")
    val strMeasure20: String?,
    @SerializedName("source")
    val strSource: String?,
    @SerializedName("imagesource")
    val strImageSource: String?,
    @SerializedName("creativecommonsconfirmed")
    val strCreativeCommonsConfirmed: String?,
    @SerializedName("datemodified")
    val dateModified: String?

){
    companion object {
        // NB: This only highlights the first match in a string
        private fun findAndSetSpan(fulltext: SpannableString, subtext: String): Boolean {
            if (subtext.isEmpty()) return true
            val i = fulltext.indexOf(subtext, ignoreCase = true)
            if (i == -1) return false
            fulltext.setSpan(
                ForegroundColorSpan(Color.CYAN), i, i + subtext.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return true
        }

        fun spannableStringsEqual(a: SpannableString?, b: SpannableString?): Boolean {
            if(a == null && b == null) return true
            if(a == null && b != null) return false
            if(a != null && b == null) return false
            val spA = a!!.getSpans(0, a.length, Any::class.java)
            val spB = b!!.getSpans(0, b.length, Any::class.java)
            return a.toString() == b.toString()
                    &&
                    spA.size == spB.size && spA.equals(spB)

        }
    }

    override fun equals(other: Any?) : Boolean =
        if (other is Meal) {
            idMeal == other.idMeal
        } else {
            false
        }
}