package edu.cs371m.finalproject.ui.subreddits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.api.RedditPost
import edu.cs371m.finalproject.databinding.RowSubredditBinding
import edu.cs371m.finalproject.glide.Glide
import edu.cs371m.finalproject.ui.HomeFragment
import edu.cs371m.finalproject.ui.MainViewModel
import edu.cs371m.finalproject.ui.PostRowAdapter

// NB: Could probably unify with PostRowAdapter if we had two
// different VH and override getItemViewType
// https://medium.com/@droidbyme/android-recyclerview-with-multiple-view-type-multiple-view-holder-af798458763b
class SubredditListAdapter(private val viewModel: MainViewModel,
                           private val fragmentActivity: FragmentActivity )
    : ListAdapter<RedditPost, SubredditListAdapter.VH>(PostRowAdapter.RedditDiff()) {

    // ViewHolder pattern
    inner class VH(val rowSubredditBinding: RowSubredditBinding)
        : RecyclerView.ViewHolder(rowSubredditBinding.root)
    {
        init {
            //XXX Write me.
            rowSubredditBinding.root.setOnClickListener{
                val tempTitle = getItem(adapterPosition).displayName.toString()
                //exit current fragment
                fragmentActivity.supportFragmentManager.popBackStack()
                //go to home
                fragmentActivity.supportFragmentManager.commit{
                  add(R.id.main_frame,HomeFragment.newInstance())
                   setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }
                viewModel.setTitle("r/"+tempTitle)
                //repo-fetch similar
                viewModel.setSubredditToTitle()
                viewModel.setTitleToSubreddit()
                viewModel.netPosts()
            }

        }
        }
    // XXX Write me.
    // NB: This one-liner will exit the current fragment
    // fragmentActivity.supportFragmentManager.popBackStack()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val rowSubredditBinding = RowSubredditBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return VH(rowSubredditBinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        val rowbinding = holder.rowSubredditBinding
        rowbinding.subRowDetails.text=item.publicDescription
        Glide.glideFetch(item.iconURL,item.iconURL,rowbinding.subRowPic)
        rowbinding.subRowHeading.text=item.displayName

        // NB: This one-liner will exit the current fragment
        //fragmentActivity.supportFragmentManager.popBackStack()
    }

}

