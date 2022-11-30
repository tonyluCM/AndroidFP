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
    private var searchTerm = MutableLiveData<String>()
    private var mealName = MutableLiveData<String>()


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


    private val favoritelist = MediatorLiveData<List<Meal>>()
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





    //Thanks to the filter list example
    private val searchedmealincategorylist =MediatorLiveData<List<Meal>>().apply {
        addSource(searchTerm)    { value = filterMealInCategoryList()}
        addSource(mealsInCategoryList)    { value = filterMealInCategoryList()}
        value=mealsInCategoryList.value
    }

    private val searchedfavoritelist = MediatorLiveData<List<Meal>>().apply {
        addSource(searchTerm)    { value = filterFavoriteList()}
        addSource(favoritelist)    { value = filterFavoriteList()}
        value=favoritelist.value
    }

    private fun filterMealInCategoryList(): List<Meal>? {
        var value = mealsInCategoryList.value
        var searchValue = searchTerm.value
        if(searchValue.isNullOrEmpty()) return value?.filter{
            it.searchFor("")
        }
        return value?.filter{
            it.searchFor(searchValue)
        }
    }


    private fun filterFavoriteList(): List<Meal>? {
        var value = favoritelist.value
        var searchValue = searchTerm.value
        if(searchValue.isNullOrEmpty()) return value?.filter{
            it.searchFor("")
        }
        return value?.filter{
            it.searchFor(searchValue)
        }
    }

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



    fun observeFavorites(): LiveData<List<Meal>>
    {
        return favoritelist
    }

    //favorite ones, Thanks to the filter list example
    fun isFavorite(meal: Meal): Boolean {
        return favoritelist.value?.contains(meal) ?: false
    }
    fun addFavorite(meal: Meal) {
        favoritelist.value = favoritelist.value?.plus(meal)?: listOf(meal)
    }
    fun removeFavorite(meal: Meal) {
        favoritelist.value = favoritelist.value?.minus(meal)?: listOf(meal)
    }

    //searching ones
    fun observeSearchedMealInCategoryList(): LiveData<List<Meal>>
    {
        return searchedmealincategorylist
    }

    fun observeSearchedFavoriteList(): LiveData<List<Meal>>
    {
        return searchedfavoritelist
    }
    fun setSearchTerm(s: String) {
        searchTerm.value = s
    }



}