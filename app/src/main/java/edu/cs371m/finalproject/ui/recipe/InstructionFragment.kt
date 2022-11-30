package edu.cs371m.finalproject.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import edu.cs371m.finalproject.MainActivity
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.databinding.FragmentRecipeIngredientsBinding
import edu.cs371m.finalproject.databinding.FragmentRecipeInstructionsBinding
import edu.cs371m.finalproject.ui.MainViewModel
import edu.cs371m.finalproject.ui.meals.Meals


class InstructionFragment : Fragment() {


    private var _binding : FragmentRecipeInstructionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var temp2: String
    companion object {
        fun newInstance(): InstructionFragment {
            return InstructionFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeInstructionsBinding.inflate(inflater, container, false)
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

            println("=========instructions:"+it[0].strInstructions)
            binding.instructions.text=it[0].strInstructions


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

            //repo-fetch similar
            // viewModel.setSubredditToTitle()
            // viewModel.setTitleToSubreddit()
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