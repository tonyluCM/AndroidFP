package edu.cs371m.finalproject.ui.recipe


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayout
import edu.cs371m.finalproject.MainActivity
import edu.cs371m.finalproject.ui.MainViewModel
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.databinding.FragmentRecipeBinding
import edu.cs371m.finalproject.ui.meals.Meals


class Recipe : Fragment(), TabLayout.OnTabSelectedListener
{


    private var _binding : FragmentRecipeBinding? = null
    private var fragments : Fragment? =null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var temp2: String
    companion object {
        fun newInstance(): Recipe {
            return Recipe()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // XXX Write me, onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        //(requireActivity() as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var temp = (requireActivity() as MainActivity).findViewById<EditText>(R.id.actionSearch)
        temp.visibility = View.INVISIBLE


        val tabLayout = binding.tabLayout
        fragments = IngredientsFragment()

        val manager: FragmentManager? = getActivity()?.getSupportFragmentManager()
        val transaction : FragmentTransaction? = manager?.beginTransaction()
        if (transaction != null) {
            fragments?.let { transaction.replace(R.id.framelayout, it) }
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.commit()
        }
        tabLayout.addOnTabSelectedListener(this)


        viewModel.observeMeal().observe(viewLifecycleOwner)
        {
            temp2 = it[0].strCategory.toString()

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

    override fun onTabSelected(tab: TabLayout.Tab?) {
       when(tab?.position){

           0->{
               fragments = IngredientsFragment()
           }
           1->{
               fragments = InstructionFragment()
           }
           2->{
               fragments = VideoFragment()
           }
       }

        val manager: FragmentManager? = getActivity()?.getSupportFragmentManager()
        val transaction : FragmentTransaction? = manager?.beginTransaction()
        if (transaction != null) {
            fragments?.let { transaction.replace(R.id.framelayout, it) }
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.commit()
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

}