package edu.cs371m.finalproject.api

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.text.clearSpans
import com.google.gson.annotations.SerializedName

data class Meal (
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: SpannableString,
    @SerializedName("strMealThumb")
    val strMealThumb: String,
    //add for recipe details
    @SerializedName("strDrinkAlternate")
    val strDrinkAlternate: String?,
    @SerializedName("strCategory")
    val strCategory: SpannableString?,
    @SerializedName("strArea")
    val strArea: String?,
    @SerializedName("strInstructions")
    val strInstructions: String?,
    @SerializedName("strTags")
    val strTags: String?,
    @SerializedName("strYoutube")
    val strYoutube: String?,
    @SerializedName("strIngredient1")
    val strIngredient1: String?,
    @SerializedName("strIngredient2")
    val strIngredient2: String?,
    @SerializedName("strIngredient3")
    val strIngredient3: String?,
    @SerializedName("strIngredient4")
    val strIngredient4: String?,
    @SerializedName("strIngredient5")
    val strIngredient5: String?,
    @SerializedName("strIngredient6")
    val strIngredient6: String?,
    @SerializedName("strIngredient7")
    val strIngredient7: String?,
    @SerializedName("strIngredient8")
    val strIngredient8: String?,
    @SerializedName("strIngredient9")
    val strIngredient9: String?,
    @SerializedName("strIngredient10")
    val strIngredient10: String?,
    @SerializedName("strIngredient11")
    val strIngredient11: String?,
    @SerializedName("strIngredient12")
    val strIngredient12: String?,
    @SerializedName("strIngredient13")
    val strIngredient13: String?,
    @SerializedName("strIngredient14")
    val strIngredient14: String?,
    @SerializedName("strIngredient15")
    val strIngredient15: String?,
    @SerializedName("strIngredient16")
    val strIngredient16: String?,
    @SerializedName("strIngredient17")
    val strIngredient17: String?,
    @SerializedName("strIngredient18")
    val strIngredient18: String?,
    @SerializedName("strIngredient19")
    val strIngredient19: String?,
    @SerializedName("strIngredient20")
    val strIngredient20: String?,
    @SerializedName("strMeasure1")
    val strMeasure1: String?,
    @SerializedName("strMeasure2")
    val strMeasure2: String?,
    @SerializedName("strMeasure3")
    val strMeasure3: String?,
    @SerializedName("strMeasure4")
    val strMeasure4: String?,
    @SerializedName("strMeasure5")
    val strMeasure5: String?,
    @SerializedName("strMeasure6")
    val strMeasure6: String?,
    @SerializedName("strMeasure7")
    val strMeasure7: String?,
    @SerializedName("strMeasure8")
    val strMeasure8: String?,
    @SerializedName("strMeasure9")
    val strMeasure9: String?,
    @SerializedName("strMeasure10")
    val strMeasure10: String?,
    @SerializedName("strMeasure11")
    val strMeasure11: String?,
    @SerializedName("strMeasure12")
    val strMeasure12: String?,
    @SerializedName("strMeasure13")
    val strMeasure13: String?,
    @SerializedName("strMeasure14")
    val strMeasure14: String?,
    @SerializedName("strMeasure15")
    val strMeasure15: String?,
    @SerializedName("strMeasure16")
    val strMeasure16: String?,
    @SerializedName("strMeasure17")
    val strMeasure17: String?,
    @SerializedName("strMeasure18")
    val strMeasure18: String?,
    @SerializedName("strMeasure19")
    val strMeasure19: String?,
    @SerializedName("strMeasure20")
    val strMeasure20: String?,
    @SerializedName("strSource")
    val strSource: String?,
    @SerializedName("strImageSource")
    val strImageSource: String?,
    @SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String?,
    @SerializedName("dateModified")
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
    private fun clearSpan(str: SpannableString?) {
        str?.clearSpans()
        str?.setSpan(
            ForegroundColorSpan(Color.GRAY), 0, 0,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    // clearSpans does not invalidate the textview
    // We have to assign a span to make sure text gets redrawn, so assign
    // a span that does nothing
    private fun removeAllCurrentSpans(){
        // Erase all spans
        clearSpan(strMeal)
    }

    // Given a search string, look for it in the RedditPost.  If found,
    // highlight it and return true, otherwise return false.
    fun searchFor(searchTerm: String): Boolean {
        // XXX Write me, search both regular posts and subreddit listings
        removeAllCurrentSpans()
        if(searchTerm=="")
        {
            removeAllCurrentSpans()
            return true
        }
        var result = findAndSetSpan(strMeal,searchTerm)
        return result

    }
    override fun equals(other: Any?) : Boolean =
        if (other is Meal) {
            idMeal == other.idMeal
        } else {
            false
        }
}