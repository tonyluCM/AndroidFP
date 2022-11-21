package edu.cs371m.finalproject.ui


import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import edu.cs371m.finalproject.api.*
import edu.cs371m.finalproject.ui.recipe.Recipe

// XXX Much to write
class MainViewModel : ViewModel() {

    private var displayName = MutableLiveData("")
    private var email = MutableLiveData("")
    private var uid = MutableLiveData("")
    private var title = MutableLiveData<String>()

    private var mealName = MutableLiveData<String>()
    //private var searchTerm = MutableLiveData<String>()

    private var mealCategory = MutableLiveData<String>().apply{
        value = "Beef"
    }
    private var mealId = MutableLiveData<String>().apply{
        value = "52959"
    }
    // XXX Write netPosts/searchPosts
    private val mealapi = MealApi.create()
    private val mealrepository = MealsRepository(mealapi)

    private val categoryList = MediatorLiveData<List<Category>>()
    private val mealsInCategoryList = MediatorLiveData<List<Meal>>()
    private val mealList = MediatorLiveData<List<Meal>>()


    //private val favoritelist = MediatorLiveData<List<RedditPost>>()
    init {
        //netPosts()
        netCategories()
    }

    fun netCategories() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO) {
        // Update LiveData from IO dispatcher, use postValue
        categoryList.postValue(mealrepository.getCategories())
    }
    fun netMealsInCategory() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO) {
        // Update LiveData from IO dispatcher, use postValue
        mealsInCategoryList.postValue(mealrepository.getMealsByCategory(mealCategory.value.toString()))
    }
    fun netMeal() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO) {
        // Update LiveData from IO dispatcher, use postValue
        mealList.postValue(mealrepository.getMealByID(mealId.value.toString()))
    }

    private fun userLogout() {
        displayName.postValue("No user")
        email.postValue("No email, no active user")
        uid.postValue("No uid, no active user")
    }

    fun updateUser() {
        // XXX Write me. Update user data in view model
        val user = FirebaseAuth.getInstance().currentUser
        displayName.postValue(user?.displayName)
        email.postValue(user?.email)
        uid.postValue(user?.uid)

    }

    fun observeDisplayName() : LiveData<String> {
        return displayName
    }
    fun observeEmail() : LiveData<String> {
        return email
    }
    fun observeUid() : LiveData<String> {
        return uid
    }
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        userLogout()
    }




/**
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
**/
    // Looks pointless, but if LiveData is set up properly, it will fetch posts
    // from the network
   //fun repoFetch() {
     //   val fetch = subreddit.value
     //   subreddit.value = fetch
    //}

    //Tao
    fun setMealCategory(tempcategory:String)
    {
        mealCategory.value = tempcategory
    }
    fun setMealId(tempid:String)
    {
        mealId.value = tempid
    }
    fun observeCategory(): LiveData<List<Category>>
    {
        return categoryList
    }
    //set meal category before netting it
    fun observeMealsInCategory(): LiveData<List<Meal>>
    {
        return mealsInCategoryList
    }
    //set meal id before netting it
    fun observeMeal(): LiveData<List<Meal>>
    {
        return mealList
    }

    fun observeMealName(): LiveData<String> {
        return mealName
    }
    fun setMealName(newMealName: String) {
        mealName.value = newMealName
    }


    fun observeTitle(): LiveData<String> {
        return title
    }
    fun setTitle(newTitle: String) {
        title.value = newTitle
    }
    // The parsimonious among you will find that you can call this in exactly two places

    fun setTitleToCategory(){
        title.value =  mealCategory.value
    }

  /*  fun setTitleToMeal(){
        title.value =  mealId.value
    }*/


    // XXX Write me, set, observe, deal with favorites



    /*fun observeFavorites(): LiveData<List<RedditPost>>
    {
        return favoritelist
    }*/

    //favorite ones, Thanks to the filter list example
   /* fun isFavorite(redditpost: RedditPost): Boolean {
        return favoritelist.value?.contains(redditpost) ?: false
    }
    fun addFavorite(redditpost: RedditPost) {
        favoritelist.value = favoritelist.value?.plus(redditpost)?: listOf(redditpost)
    }
    fun removeFavorite(redditpost: RedditPost) {
        favoritelist.value = favoritelist.value?.minus(redditpost)?: listOf(redditpost)
    }*/
    /**
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
**/
    // Convenient place to put it as it is shared
    companion object {
        const val mealIdKey = "mealIdKey"
        const val mealNameKey = "mealNameKey"
        const val mealImgKey = "mealImgKey"
        const val mealCategoryKey = "mealCategoryKey"
        const val mealInstructionsKey = "mealInstructionsKey"
        const val mealYoutubeLinkKey = "mealYoutubeLinkKey"
        const val mealThumbLinkKey = "mealThumbLinkKey"

        fun doOneMealRecipe(context: Context, meal: Meal) {
            val oneMealRecipeIntent = Intent(context, Recipe::class.java)
            oneMealRecipeIntent.putExtra(mealIdKey,meal.idMeal)
            oneMealRecipeIntent.putExtra(mealNameKey,meal.strMeal)
            oneMealRecipeIntent.putExtra(mealImgKey,meal.strMealThumb)
            oneMealRecipeIntent.putExtra(mealCategoryKey,meal.strCategory)
            oneMealRecipeIntent.putExtra(mealInstructionsKey,meal.strInstructions)
            oneMealRecipeIntent.putExtra(mealYoutubeLinkKey,meal.strYoutube)
            oneMealRecipeIntent.putExtra(mealYoutubeLinkKey,meal.strMealThumb)

            context.startActivity(oneMealRecipeIntent)
        }
    }
}