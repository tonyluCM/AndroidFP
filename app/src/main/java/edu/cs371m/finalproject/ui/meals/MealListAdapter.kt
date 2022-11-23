package edu.cs371m.finalproject.ui.meals

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.finalproject.api.Meal
import edu.cs371m.finalproject.databinding.RowMealsBinding
import edu.cs371m.finalproject.glide.Glide
import edu.cs371m.finalproject.ui.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import edu.cs371m.finalproject.R
import kotlin.coroutines.coroutineContext

// NB: Could probably unify with PostRowAdapter if we had two
// different VH and override getItemViewType
// https://medium.com/@droidbyme/android-recyclerview-with-multiple-view-type-multiple-view-holder-af798458763b
class MealListAdapter(private val viewModel: MainViewModel,
                      private val fragmentActivity: FragmentActivity )
    : ListAdapter<Meal, MealListAdapter.VH>(MealDiff()) {

    // ViewHolder pattern
    inner class VH(val rowMealBinding: RowMealsBinding)
        : RecyclerView.ViewHolder(rowMealBinding.root)
    {
        init {
            //XXX Write me.
            rowMealBinding.root.setOnClickListener{
                if(FirebaseAuth.getInstance().currentUser!=null) {
                    MainViewModel.doOneMealRecipe(it.context, getItem(adapterPosition))
                    val clickedMealName = getItem(adapterPosition).strMeal.toString()
                    val clickedMealId = getItem(adapterPosition).idMeal.toString()
                    viewModel.setTitle(clickedMealName)
                    viewModel.setMealId(clickedMealId)
                    viewModel.netMeal()
                }
                else
                {
                    Toast.makeText(it.context,"Please sign in to view the recipe~",Toast.LENGTH_SHORT).show()
                }
                //repo-fetch similar
              /*  viewModel.setSubredditToTitle()
                viewModel.setTitleToSubreddit()
                viewModel.netPosts()*/
            }
            rowMealBinding.rowFav.setOnClickListener {
                // Toggle Favorite
                if(FirebaseAuth.getInstance().currentUser!=null) {
                    val loc = getItem(adapterPosition)
                    loc.let {
                        if (viewModel.isFavorite(it)) {
                            viewModel.removeFavorite(it)
                        } else {
                            viewModel.addFavorite(it)
                        }
                        notifyItemChanged(adapterPosition)
                    }
                }
                else
                {
                    Toast.makeText(it.context,"Please sign in to access favorite feature~",Toast.LENGTH_SHORT).show()
                }
            }
        }
        }
    // XXX Write me.
    // NB: This one-liner will exit the current fragment
    // fragmentActivity.supportFragmentManager.popBackStack()
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
      //mealThumbIV subRowPic
        //mealNameTV subRowHeading

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

