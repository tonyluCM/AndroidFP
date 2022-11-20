package edu.cs371m.finalproject.api
import android.text.SpannableString
import com.google.gson.GsonBuilder
import edu.cs371m.finalproject.MainActivity
import retrofit2.http.Path
import retrofit2.http.Query

class MealsRepository(private val mealApi: MealApi) {

    suspend fun getCategories() : List<Category>
    {
        return mealApi.getCategories().categories
    }
    suspend fun getMealsByCategory(mealCategory: String) : List<Meals>
    {
        return mealApi.getMealsByCategory(mealCategory).meals
    }
    suspend fun getMealByID(mealId: String): List<Meals>
    {
        return mealApi.getMealByID(mealId).meals
    }
}