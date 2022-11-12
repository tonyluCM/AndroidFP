package edu.cs371m.reddit.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.reddit.MainActivity
import edu.cs371m.reddit.R
import edu.cs371m.reddit.api.RedditPost
import edu.cs371m.reddit.databinding.RowPostBinding
import edu.cs371m.reddit.glide.Glide

/**
 * Created by witchel on 8/25/2019
 */

// https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter
// Slick adapter that provides submitList, so you don't worry about how to update
// the list, you just submit a new one when you want to change the list and the
// Diff class computes the smallest set of changes that need to happen.
// NB: Both the old and new lists must both be in memory at the same time.
// So you can copy the old list, change it into a new list, then submit the new list.
//
// You can call adapterPosition to get the index of the selected item
class PostRowAdapter(private val viewModel: MainViewModel)
    : ListAdapter<RedditPost, PostRowAdapter.VH>(RedditDiff()) {

    inner class VH(val rowpostbinding: RowPostBinding)
        : RecyclerView.ViewHolder(rowpostbinding.root)
    {
        init {
            //XXX Write me.
            rowpostbinding.root.setOnClickListener{
                MainViewModel.doOnePost(it.context,getItem(adapterPosition))
            }
            rowpostbinding.rowFav.setOnClickListener {
                // Toggle Favorite
                val loc = getItem(adapterPosition)
                 loc.let {
                    if (viewModel.isFavorite(it)) {
                        viewModel.removeFavorite(it)
                    } else {
                        viewModel.addFavorite(it)
                   }
                    notifyItemChanged(adapterPosition)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        //XXX Write me.
        val rowpostbinding = RowPostBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return VH(rowpostbinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        //XXX Write me.
        val item = getItem(position)
        val rowpostbinding = holder.rowpostbinding
        rowpostbinding.title.text = item.title
        Glide.glideFetch(item.imageURL,item.thumbnailURL,rowpostbinding.image)
        rowpostbinding.selfText.text = item.selfText
        rowpostbinding.score.text = item.score.toString()
        rowpostbinding.comments.text = item.commentCount.toString()
        if (viewModel.isFavorite(item)) {
            rowpostbinding.rowFav.setImageResource(R.drawable.ic_favorite_black_24dp)
        }
        else {
            rowpostbinding.rowFav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        }


    }
    class RedditDiff : DiffUtil.ItemCallback<RedditPost>() {
        override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
            return oldItem.key == newItem.key
        }
        override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
            return RedditPost.spannableStringsEqual(oldItem.title, newItem.title) &&
                    RedditPost.spannableStringsEqual(oldItem.selfText, newItem.selfText) &&
                    RedditPost.spannableStringsEqual(oldItem.publicDescription, newItem.publicDescription) &&
                    RedditPost.spannableStringsEqual(oldItem.displayName, newItem.displayName)

        }
    }
}

