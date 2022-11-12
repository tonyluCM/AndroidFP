package edu.cs371m.reddit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import edu.cs371m.reddit.api.RedditPost
import edu.cs371m.reddit.databinding.ActionBarBinding
import edu.cs371m.reddit.databinding.ActivityMainBinding
import edu.cs371m.reddit.ui.Favorites
import edu.cs371m.reddit.ui.HomeFragment
import edu.cs371m.reddit.ui.MainViewModel
import edu.cs371m.reddit.ui.subreddits.Subreddits
import edu.cs371m.reddit.ui.OnePost

class MainActivity : AppCompatActivity() {
    // This allows us to do better testing
    companion object {
        var globalDebug = false
        lateinit var jsonAww100: String
        lateinit var subreddit1: String
        private const val mainFragTag = "mainFragTag"
        private const val favoritesFragTag = "favoritesFragTag"
        private const val subredditsFragTag = "subredditsFragTag"
    }
    private var actionBarBinding: ActionBarBinding? = null
    private val viewModel: MainViewModel by viewModels()

    // An Android nightmare
    // https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
    // https://stackoverflow.com/questions/7789514/how-to-get-activitys-windowtoken-without-view
    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    }

    // https://stackoverflow.com/questions/24838155/set-onclick-listener-on-action-bar-title-in-android/29823008#29823008
    private fun initActionBar(actionBar: ActionBar) {
        // Disable the default and enable the custom
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayShowCustomEnabled(true)
        actionBarBinding = ActionBarBinding.inflate(layoutInflater)
        // Apply the custom view
        actionBar.customView = actionBarBinding?.root
    }

    private fun actionBarTitleLaunchSubreddit()  {
        // XXX Write me actionBarBinding

            actionBarBinding!!.actionTitle.setOnClickListener {
                if(viewModel.observeTitle().value != "Pick"&&viewModel.observeTitle().value != "Favorites") {
                    supportFragmentManager.commit {
                        replace(R.id.main_frame, Subreddits.newInstance(), subredditsFragTag)
                        // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    }
                }
            }

    }
    fun actionBarLaunchFavorites() {
        // XXX Write me actionBarBinding
                actionBarBinding!!.actionFavorite.setOnClickListener {
                    if(viewModel.observeTitle().value != "Pick"&&viewModel.observeTitle().value != "Favorites") {
                        supportFragmentManager.commit {
                            replace(R.id.main_frame, Favorites.newInstance(), favoritesFragTag)
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        }
                    }
                }


    }

    // XXX check out addTextChangedListener
    private fun actionBarSearch() {
        // XXX Write me
        actionBarBinding?.actionSearch?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true){
                    hideKeyboard()
                }
                viewModel.setSearchTerm(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun addHomeFragment() {
        // No back stack for home
        supportFragmentManager.commit {
            add(R.id.main_frame, HomeFragment.newInstance(), mainFragTag)
            // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    }

    private fun initDebug() {
        if(globalDebug) {
            assets.list("")?.forEach {
                Log.d(javaClass.simpleName, "Asset file: $it" )
            }
            jsonAww100 = assets.open("aww.hot.1.100.json.transformed.txt").bufferedReader().use {
                it.readText()
            }
            subreddit1 = assets.open("subreddits.1.json.txt").bufferedReader().use {
                it.readText()
            }
        }
    }
    private fun initTitleObservers() {
        // Observe title changes
        viewModel.observeTitle().observe(ViewTreeLifecycleOwner.get(findViewById(R.id.actionTitle))!!)
        {
            actionBarBinding!!.actionTitle.text = it
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        supportActionBar?.let{
            initActionBar(it)
        }
        // Add menu items without overriding methods in the Activity
        // https://developer.android.com/jetpack/androidx/releases/activity#1.4.0-alpha01
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Inflate the menu; this adds items to the action bar if it is present.
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle action bar item clicks here.
                return when (menuItem.itemId) {
                    android.R.id.home -> false // Handle in fragment
                    else -> true
                }
            }
        })

        addHomeFragment()
        initDebug()
        initTitleObservers()
        actionBarTitleLaunchSubreddit()
        actionBarLaunchFavorites()
        actionBarSearch()
        viewModel.setTitleToSubreddit()
    }
}
