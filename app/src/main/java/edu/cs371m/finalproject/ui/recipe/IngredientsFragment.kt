package edu.cs371m.finalproject.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import edu.cs371m.finalproject.MainActivity
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.databinding.FragmentRecipeIngredientsBinding
import edu.cs371m.finalproject.ui.MainViewModel
import edu.cs371m.finalproject.ui.meals.Meals


class IngredientsFragment : Fragment() {

    private var _binding : FragmentRecipeIngredientsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var temp2: String
    companion object {
        fun newInstance(): IngredientsFragment {
            return IngredientsFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setHasOptionsMenu(true)
        /*  (requireActivity() as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
          var temp = (requireActivity() as MainActivity).findViewById<EditText>(R.id.actionSearch)
          temp.visibility = View.INVISIBLE*/

        viewModel.observeMeal().observe(viewLifecycleOwner)
        {
            //  binding.title.text = it[0].strMeal
            temp2 = it[0].strCategory.toString()
            val myArrayList = ArrayList<String>()

            if(it[0].strIngredient1!=null && it[0].strIngredient1?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient1 +" -------------- "+it[0].strMeasure1)
            }
            if(it[0].strIngredient2!=null && it[0].strIngredient2?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient2 +" -------------- "+it[0].strMeasure2)
            }
            if(it[0].strIngredient3!=null && it[0].strIngredient3?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient3 +" -------------- "+it[0].strMeasure3)
            }
            if(it[0].strIngredient4!=null && it[0].strIngredient4?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient4 +" -------------- "+it[0].strMeasure4)
            }
            if(it[0].strIngredient5!=null && it[0].strIngredient5?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient5 +" -------------- "+it[0].strMeasure5)
            }
            if(it[0].strIngredient6!=null && it[0].strIngredient6?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient6 +" -------------- "+it[0].strMeasure6)
            }
            if(it[0].strIngredient7!=null && it[0].strIngredient7?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient7 +" -------------- "+it[0].strMeasure7)
            }
            if(it[0].strIngredient8!=null && it[0].strIngredient8?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient8 +" -------------- "+it[0].strMeasure8)
            }
            if(it[0].strIngredient9!=null && it[0].strIngredient9?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient9 +" -------------- "+it[0].strMeasure9)
            }
            if(it[0].strIngredient10!=null && it[0].strIngredient10?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient10 +" -------------- "+it[0].strMeasure10)
            }

            if(it[0].strIngredient11!=null && it[0].strIngredient11?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient11 +" -------------- "+it[0].strMeasure11)
            }
            if(it[0].strIngredient12!=null && it[0].strIngredient12?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient12 +" -------------- "+it[0].strMeasure12)
            }
            if(it[0].strIngredient13!=null && it[0].strIngredient13?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient13 +" -------------- "+it[0].strMeasure13)
            }
            if(it[0].strIngredient14!=null && it[0].strIngredient14?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient14 +" -------------- "+it[0].strMeasure14)
            }
            if(it[0].strIngredient15!=null && it[0].strIngredient15?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient15 +" -------------- "+it[0].strMeasure15)
            }
            if(it[0].strIngredient16!=null && it[0].strIngredient16?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient16 +" -------------- "+it[0].strMeasure16)
            }
            if(it[0].strIngredient17!=null && it[0].strIngredient17?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient17 +" -------------- "+it[0].strMeasure17)
            }
            if(it[0].strIngredient18!=null && it[0].strIngredient18?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient18 +" -------------- "+it[0].strMeasure18)
            }
            if(it[0].strIngredient19!=null && it[0].strIngredient19?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient19 +" -------------- "+it[0].strMeasure19)
            }
            if(it[0].strIngredient20!=null && it[0].strIngredient20?.isNotBlank() == true){
                myArrayList.add(it[0].strIngredient20 +" -------------- "+it[0].strMeasure20)
            }
            binding.ingredientsList.setAdapter(
                getActivity()?.let { it1 ->
                    ArrayAdapter<String>(
                        it1,
                        R.layout.row_ingredient,
                        R.id.one_ingredient_tv,
                        myArrayList
                    )
                }
            )



        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return if (id == android.R.id.home) {
            // If user clicks "up", then it it as if they clicked OK.  We commit
            // changes and return to parent
            (requireActivity() as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            onDestroyView()
            val clickedCategory = temp2
            //exit current fragment

            //go to meals

            this.requireActivity().supportFragmentManager.commit{
                replace(R.id.main_frame, Meals.newInstance())
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }

            viewModel.setTitle(clickedCategory)
            viewModel.setMealCategory(clickedCategory)
            viewModel.setTitleToCategory()
            viewModel.netMealsInCategory()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}