package edu.cs371m.finalproject.api

import android.graphics.Color
 import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.text.clearSpans
import com.google.gson.annotations.SerializedName

data class RedditPost (
    @SerializedName("name")
    val key: String,
    @SerializedName("title")
    val title: SpannableString,
    @SerializedName("score")
    val score: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("num_comments")
    val commentCount: Int,
    @SerializedName("thumbnail")
    val thumbnailURL: String,
    @SerializedName("url")
    val imageURL: String,
    @SerializedName("selftext")
    val selfText : SpannableString?,
    @SerializedName("is_video")
    val isVideo : Boolean,
    // Useful for subreddits
    @SerializedName("display_name")
    val displayName: SpannableString?,
    @SerializedName("icon_img")
    val iconURL: String,
    @SerializedName("public_description")
    val publicDescription: SpannableString?
) {
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
        clearSpan(title)
        clearSpan(selfText)
        clearSpan(displayName)
        clearSpan(publicDescription)
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
        var result1 = findAndSetSpan(title,searchTerm)
        var result2 = false
        var result3 = false
        var result4 = false
        if (selfText != null) {
            result2 = findAndSetSpan(selfText,searchTerm)
        }
        if (displayName != null) {
            result3 = findAndSetSpan(displayName,searchTerm)
        }
        if (publicDescription != null) {
            result4 = findAndSetSpan(publicDescription,searchTerm)
        }
        return result1||result2||result3||result4

    }

    // NB: This changes the behavior of lists of RedditPosts.  I want posts fetched
    // at two different times to compare as equal.  By default, they will be different
    // objects with different hash codes.
    override fun equals(other: Any?) : Boolean =
        if (other is RedditPost) {
            key == other.key
        } else {
            false
        }
}
