package edu.cs371m.finalproject.ui.meals

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.finalproject.api.Meal
import edu.cs371m.finalproject.databinding.RowMealsBinding
import edu.cs371m.finalproject.glide.Glide
import edu.cs371m.finalproject.ui.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.ui.recipe.Recipe
import kotlin.coroutines.coroutineContext

// NB: Could probably unify with PostRowAdapter if we had two
// different VH and override getItemViewType
// https://medium.com/@droidbyme/android-recyclerview-with-multiple-view-type-multiple-view-holder-af798458763b
class MealListAdapter(private val viewModel: MainViewModel,
                      private val fragmentActivity: FragmentActivity )
    : ListAdapter<Meal, MealListAdapter.VH>(MealDiff()) {

    companion object {
        var itemCount = 0
    }
    // ViewHolder pattern
    inner class VH(val rowMealBinding: RowMealsBinding)
        : RecyclerView.ViewHolder(rowMealBinding.root)
    {

        init {
            //XXX Write me.
            rowMealBinding.root.setOnClickListener{
                if(FirebaseAuth.getInstance().currentUser!=null) {
                    val clickedMealName = getItem(adapterPosition).strMeal.toString()
                    val clickedMealId = getItem(adapterPosition).idMeal.toString()

                    //fragmentActivity.supportFragmentManager.popBackStack()
                    //go to meals
                    fragmentActivity.supportFragmentManager.commit{
                        replace(R.id.main_frame,Recipe.newInstance() )
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    }

                    viewModel.setTitle(clickedMealName)
                    viewModel.setMealId(clickedMealId)
                    viewModel.netMeal()

                }
                else
                {
                    ++MealListAdapter.itemCount
                    if(MealListAdapter.itemCount<=2)
                    {
                        val clickedMealName = getItem(adapterPosition).strMeal.toString()
                        val clickedMealId = getItem(adapterPosition).idMeal.toString()
                        //fragmentActivity.supportFragmentManager.popBackStack()
                        //go to meals
                        fragmentActivity.supportFragmentManager.commit{
                            replace(R.id.main_frame,Recipe.newInstance() )
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        }
                        viewModel.setTitle(clickedMealName)
                        viewModel.setMealId(clickedMealId)
                        viewModel.netMeal()
                        //MainViewModel.doOneMealRecipe(it.context, getItem(adapterPosition))
                    }
                    else {
                        Toast.makeText(
                            it.context,
                            "Please sign in to view more recipes~",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
            rowMealBinding.rowFav.setOnClickListener {
                // Toggle Favorite

                    val loc = getItem(adapterPosition)
                    loc.let {
                        if (viewModel.isFavorite(it)) {
                            viewModel.removeFavorite(it)
                        } else {
                            if(FirebaseAuth.getInstance().currentUser!=null) {
                                viewModel.addFavorite(it)
                            }
                            else
                            {
                                Toast.makeText(rowMealBinding.rowFav.context,"Please sign in to access Favorite feature~",Toast.LENGTH_SHORT).show()
                            }
                        }
                        notifyItemChanged(adapterPosition)
                    }


            }
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val rowMealBinding = RowMealsBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return VH(rowMealBinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        val rowbinding = holder.rowMealBinding
        Glide.glideFetch(item.strMealThumb,item.strMealThumb,rowbinding.mealThumbIV)
        rowbinding.mealNameTV.text=item.strMeal
        if (viewModel.isFavorite(item)) {
            rowbinding.rowFav.setImageResource(R.drawable.ic_favorite_black_24dp)
        }
        else {
            rowbinding.rowFav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        }


    }
    class MealDiff : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }
        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return Meal.spannableStringsEqual(oldItem.strMeal, newItem.strMeal) &&
                    Meal.spannableStringsEqual(oldItem.strCategory, newItem.strCategory)
        }
    }
}

