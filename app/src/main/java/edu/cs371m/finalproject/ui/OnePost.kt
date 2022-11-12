package edu.cs371m.finalproject.ui


import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import edu.cs371m.finalproject.databinding.ActivityOnePostBinding
import androidx.appcompat.app.AppCompatActivity
import edu.cs371m.finalproject.glide.Glide





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