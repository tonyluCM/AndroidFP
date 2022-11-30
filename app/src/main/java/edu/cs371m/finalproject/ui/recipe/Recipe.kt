package edu.cs371m.finalproject.ui.recipe

import android.util.Log
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.cs371m.finalproject.databinding.FragmentRecipeBinding
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import edu.cs371m.finalproject.MainActivity
import edu.cs371m.finalproject.databinding.ActionBarBinding
import edu.cs371m.finalproject.ui.MainViewModel
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.databinding.FragmentRvBinding
import edu.cs371m.finalproject.ui.meals.Meals


class Recipe : Fragment()
{
    private var _binding : FragmentRecipeBinding? = null
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
        (requireActivity() as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var temp = (requireActivity() as MainActivity).findViewById<EditText>(R.id.actionSearch)
        temp.visibility = View.INVISIBLE

        viewModel.observeMeal().observe(viewLifecycleOwner)
        {
            binding.title.text = it[0].strMeal
             temp2 = it[0].strCategory.toString()
            //video
            //there is a problem since the link is watch?=nvm... not embed/...
            val temp3 = it[0].strYoutube.toString()
            val temp4 = temp3.replace("watch?v=","embed/")
            val framevideo = "<html><body>Video From YouTube<br><iframe width=\"420\" height=\"315\" src=\"${temp4.toString()}\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
            //val framevideo = "<html><body>Video From YouTube<br><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/nMyBC9staMU\" frameborder=\"0\" allowfullscreen></iframe></body></html>"

            binding.recipeVideo.setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }
            })
            val webSettings: WebSettings = binding.recipeVideo.getSettings()
            webSettings.javaScriptEnabled = true
            binding.recipeVideo.loadData(framevideo, "text/html", "utf-8")

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
            val clickedCategory = temp2.toString()
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