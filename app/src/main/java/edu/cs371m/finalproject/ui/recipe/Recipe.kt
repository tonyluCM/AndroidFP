package edu.cs371m.finalproject.ui.recipe


import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import edu.cs371m.finalproject.databinding.ActivityOneRecipeBinding
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

        val frameVideo =
            "<html><body>Video From YouTube<br><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/nMyBC9staMU\" frameborder=\"0\" allowfullscreen></iframe></body></html>"

        binding.recipeVideo.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        })
        val webSettings: WebSettings = binding.recipeVideo.getSettings()
        webSettings.javaScriptEnabled = true
        binding.recipeVideo.loadData(frameVideo, "text/html", "utf-8")
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