package edu.cs371m.finalproject.ui.recipe


import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import edu.cs371m.finalproject.databinding.ActivityOneRecipeBinding
import androidx.appcompat.app.AppCompatActivity
import edu.cs371m.finalproject.glide.Glide
import edu.cs371m.finalproject.ui.MainViewModel


class Recipe : AppCompatActivity()
{
    private lateinit var binding : ActivityOneRecipeBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityOneRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mytoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val activityThatCalled = intent
        // Get the data that was sent
        val callingBundle = activityThatCalled.extras

        binding.title.text = callingBundle?.getCharSequence(MainViewModel.mealNameKey).toString()
        binding.titletv.text = callingBundle?.getCharSequence(MainViewModel.mealNameKey).toString()
        binding.onepostst.text = callingBundle?.getCharSequence(MainViewModel.mealInstructionsKey)
        binding.onepostst.movementMethod = ScrollingMovementMethod()
        val imageurl = callingBundle?.getString(MainViewModel.mealThumbLinkKey)
        val thumbnailurl = callingBundle?.getString(MainViewModel.mealThumbLinkKey)
        if (imageurl != null && thumbnailurl != null) {
            Glide.glideFetch(imageurl, thumbnailurl, binding.onepostimage)
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
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }
}