package edu.cs371m.finalproject

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import edu.cs371m.finalproject.databinding.ActionBarBinding
import edu.cs371m.finalproject.databinding.ActivityMainBinding
import edu.cs371m.finalproject.ui.*
import edu.cs371m.finalproject.ui.categories.Categories
import edu.cs371m.finalproject.ui.meals.favorites


class MainActivity : AppCompatActivity() {
    // This allows us to do better testing
    companion object {
        var globalDebug = false
        lateinit var jsonAww100: String
       // lateinit var subreddit1: String
       // private const val mainFragTag = "mainFragTag"
        private const val initialPageTag = "initialPageTag"
        private const val favoritesFragTag = "favoritesFragTag"
       // private const val subredditsFragTag = "subredditsFragTag"

        private const val categoriesFragTag = "categoriesFragTag"

    }
    private var actionBarBinding: ActionBarBinding? = null
    private val viewModel: MainViewModel by viewModels()
    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            viewModel.updateUser()
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

    private fun actionBarTitleLaunchCategories()  {
        // XXX Write me actionBarBinding

        actionBarBinding?.actionTitle?.setOnClickListener {
            if(viewModel.observeTitle().value !="Categories" &&viewModel.observeTitle().value !="Yummy Dallas"&&viewModel.observeTitle().value != "Favorites") {
                supportFragmentManager.commit {
                    replace(R.id.main_frame, Categories.newInstance(), categoriesFragTag)
                    // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }
            }

        }

    }
    fun actionBarLaunchFavorites() {
        // XXX Write me actionBarBinding
                actionBarBinding!!.actionFavorite.setOnClickListener {
                    if(viewModel.observeTitle().value != "Favorites") {
                        supportFragmentManager.commit {
                            replace(R.id.main_frame, favorites.newInstance(), favoritesFragTag)
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        }
                    }
                }


    }

    /**
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
**/
    private fun addInitialPage(){
        supportFragmentManager.commit {
            add(R.id.main_frame, InitialPage.newInstance(), initialPageTag)
            // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
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
                    R.id.action_signin ->{
                        AuthInit(viewModel, signInLauncher)
                        return true
                    }
                    R.id.action_logout ->{
                        viewModel.signOut()
                        return true
                    }
                    android.R.id.home -> false // Handle in fragment
                    else -> true
                }
            }

        })


        addInitialPage()
       // initDebug()
        initTitleObservers()
       // actionBarTitleLaunchSubreddit()
        actionBarTitleLaunchCategories()
        actionBarLaunchFavorites()
        //actionBarSearch()
        //viewModel.setTitleToSubreddit()
        //AuthInit(viewModel, signInLauncher)

    }
}
