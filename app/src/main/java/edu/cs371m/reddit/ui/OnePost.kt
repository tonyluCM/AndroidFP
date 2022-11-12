package edu.cs371m.reddit.ui


import android.app.Activity
import android.content.Intent
import androidx.core.view.MenuProvider
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.cs371m.reddit.MainActivity
import edu.cs371m.reddit.databinding.ActivityOnePostBinding
import androidx.appcompat.app.AppCompatActivity
import edu.cs371m.reddit.glide.Glide





class OnePost : AppCompatActivity()
{
    private lateinit var binding : ActivityOnePostBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityOnePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mytoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val activityThatCalled = intent
        // Get the data that was sent
        val callingBundle = activityThatCalled.extras

        if(callingBundle?.getCharSequence(MainViewModel.titleKey)!!.length >30)
        {
            binding.title.text = callingBundle?.getCharSequence(MainViewModel.titleKey)?.subSequence(0,30).toString()+"..."
        }
        else {
            binding.title.text = callingBundle?.getCharSequence(MainViewModel.titleKey).toString()
        }
        binding.titletv.text = callingBundle?.getCharSequence(MainViewModel.titleKey).toString()
        binding.onepostst.text = callingBundle?.getCharSequence(MainViewModel.selftextKey)
        binding.onepostst.movementMethod = ScrollingMovementMethod()
        val imageurl = callingBundle?.getString(MainViewModel.imageKey)
        val thumbnailurl = callingBundle?.getString(MainViewModel.thumbnailKey)
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