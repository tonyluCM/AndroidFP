package edu.cs371m.finalproject.ui.categories

import android.text.method.ArrowKeyMovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.api.Category
import edu.cs371m.finalproject.databinding.RowCategoryBinding
import edu.cs371m.finalproject.glide.Glide
import edu.cs371m.finalproject.ui.MainViewModel
import edu.cs371m.finalproject.ui.meals.Meals
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible

// NB: Could probably unify with PostRowAdapter if we had two
// different VH and override getItemViewType
// https://medium.com/@droidbyme/android-recyclerview-with-multiple-view-type-multiple-view-holder-af798458763b
class CategoryListAdapter(private val viewModel: MainViewModel,
                          private val fragmentActivity: FragmentActivity )
    : ListAdapter<Category, CategoryListAdapter.VH>(CategoryDiff()) {

    // ViewHolder pattern
    inner class VH(val rowcategoryBinding: RowCategoryBinding)
        : RecyclerView.ViewHolder(rowcategoryBinding.root)
    {
        init {
            //XXX Write me.
            rowcategoryBinding.root.setOnClickListener{
                val clickedCategory = getItem(adapterPosition).strCategory.toString()

                //exit current fragment
                fragmentActivity.supportFragmentManager.popBackStack()
                //go to meals
                fragmentActivity.supportFragmentManager.commit{
                  add(R.id.main_frame, Meals.newInstance())
                   setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }

                    viewModel.setTitle(clickedCategory)
                    viewModel.setMealCategory(clickedCategory)
                    viewModel.setTitleToCategory()
                    viewModel.netMealsInCategory()
            }


        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val rowcategoryBinding = RowCategoryBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return VH(rowcategoryBinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        val rowbinding = holder.rowcategoryBinding

        Glide.glideFetch(item.strCategoryThumb,item.strCategoryThumb,rowbinding.categoryThumbIV)
        rowbinding.categoryNameTV.text=item.strCategory
        rowbinding.categoryDescTV.text=item.strCategoryDescription
        rowbinding.categoryDescTV.movementMethod = ScrollingMovementMethod()

    }


    class CategoryDiff : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return Category.spannableStringsEqual(oldItem.strCategory, newItem.strCategory)

        }
    }
}

