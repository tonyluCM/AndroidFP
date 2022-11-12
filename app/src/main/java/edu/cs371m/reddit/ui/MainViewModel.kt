package edu.cs371m.reddit.ui


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import edu.cs371m.reddit.api.RedditApi
import edu.cs371m.reddit.api.RedditPost
import edu.cs371m.reddit.api.RedditPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// XXX Much to write
class MainViewModel : ViewModel() {
    private var title = MutableLiveData<String>()
    private var searchTerm = MutableLiveData<String>()
    private var subreddit = MutableLiveData<String>().apply {
        value = "aww"
    }

    // XXX Write netPosts/searchPosts
    private val redditpostapi = RedditApi.create()
    private val redditrepository = RedditPostRepository(redditpostapi)

    private val redditposts =MediatorLiveData<List<RedditPost>>()
    private val subredditlist =MediatorLiveData<List<RedditPost>>()
    private val favoritelist = MediatorLiveData<List<RedditPost>>()
    init {
        netPosts()
    }
    fun netPosts() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO) {
        // Update LiveData from IO dispatcher, use postValue
        redditposts.postValue(redditrepository.getPosts(subreddit.value.toString()))

    }
    fun netSubreddits() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO) {
        // Update LiveData from IO dispatcher, use postValue
        subredditlist.postValue(redditrepository.getSubreddits())

    }
    //Thanks to the filter list example
    private val searchedredditposts =MediatorLiveData<List<RedditPost>>().apply {
        addSource(searchTerm)    { value = filterPosts()}
        addSource(redditposts)    { value = filterPosts()}
        value=redditposts.value
    }
    private val searchedsubredditlist =MediatorLiveData<List<RedditPost>>().apply {
        addSource(searchTerm)    { value = filterSubRedditList()}
        addSource(subredditlist)    { value = filterSubRedditList()}
        value = subredditlist.value
    }
    private val searchedfavoritelist = MediatorLiveData<List<RedditPost>>().apply {
        addSource(searchTerm)    { value = filterFavoriteList()}
        addSource(favoritelist)    { value = filterFavoriteList()}
        value=favoritelist.value
    }

    private fun filterPosts(): List<RedditPost>? {
        var value = redditposts.value
        var searchValue = searchTerm.value
        if(searchValue.isNullOrEmpty()) return value?.filter{
            it.searchFor("")
        }
        return value?.filter{
            it.searchFor(searchValue)
        }
    }

    private fun filterSubRedditList(): List<RedditPost>? {
        var value = subredditlist.value
        var searchValue = searchTerm.value
        if(searchValue.isNullOrEmpty()) return value?.filter{
            it.searchFor("")
        }
        return value?.filter{
            it.searchFor(searchValue)
        }
    }

    private fun filterFavoriteList(): List<RedditPost>? {
        var value = favoritelist.value
        var searchValue = searchTerm.value
        if(searchValue.isNullOrEmpty()) return value?.filter{
            it.searchFor("")
        }
        return value?.filter{
            it.searchFor(searchValue)
        }
    }

    // Looks pointless, but if LiveData is set up properly, it will fetch posts
    // from the network
    fun repoFetch() {
        val fetch = subreddit.value
        subreddit.value = fetch
    }

    fun observeTitle(): LiveData<String> {
        return title
    }
    fun setTitle(newTitle: String) {
        title.value = newTitle
    }
    // The parsimonious among you will find that you can call this in exactly two places
    fun setTitleToSubreddit() {
        title.value = "r/${subreddit.value}"
    }

    // XXX Write me, set, observe, deal with favorites
    fun setSubredditToTitle()
    {
        //remove the "r/"
        subreddit.value = title.value!!.removeRange(0,2)
    }
    fun observePosts(): LiveData<List<RedditPost>>
    {
        return redditposts
    }
    fun observeSubReddits(): LiveData<List<RedditPost>>
    {
        return subredditlist
    }
    fun observeFavorites(): LiveData<List<RedditPost>>
    {
        return favoritelist
    }

    //favorite ones, Thanks to the filter list example
    fun isFavorite(redditpost: RedditPost): Boolean {
        return favoritelist.value?.contains(redditpost) ?: false
    }
    fun addFavorite(redditpost: RedditPost) {
        favoritelist.value = favoritelist.value?.plus(redditpost)?: listOf(redditpost)
    }
    fun removeFavorite(redditpost: RedditPost) {
        favoritelist.value = favoritelist.value?.minus(redditpost)?: listOf(redditpost)
    }
    //searching ones
    fun observeSearchedRedditPosts(): LiveData<List<RedditPost>>
    {
        return searchedredditposts
    }
    fun observeSearchedSubRedditList(): LiveData<List<RedditPost>> {
        return searchedsubredditlist
    }
    fun observeSearchedFavoriteList(): LiveData<List<RedditPost>>
    {
        return searchedfavoritelist
    }
    fun setSearchTerm(s: String) {
        searchTerm.value = s
    }

    // Convenient place to put it as it is shared
    companion object {
        const val titleKey = "titleKey"
        const val selftextKey = "selftextKey"
        const val imageKey = "imageKey"
        const val thumbnailKey = "thumbnailKey"

        fun doOnePost(context: Context, redditPost: RedditPost) {
            val onePostIntent = Intent(context,OnePost::class.java)
            onePostIntent.putExtra(titleKey,redditPost.title)
            onePostIntent.putExtra(selftextKey,redditPost.selfText)
            onePostIntent.putExtra(imageKey,redditPost.imageURL)
            onePostIntent.putExtra(thumbnailKey,redditPost.thumbnailURL)
            context.startActivity(onePostIntent)
        }
    }
}