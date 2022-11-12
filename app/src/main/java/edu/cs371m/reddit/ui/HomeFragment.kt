package edu.cs371m.reddit.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import edu.cs371m.reddit.MainActivity
import edu.cs371m.reddit.databinding.FragmentRvBinding


// XXX Write most of this file
class HomeFragment: Fragment() {
    // XXX initialize viewModel
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: PostRowAdapter
    private var _binding: FragmentRvBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    // Set up the adapter
    private fun initAdapter(binding: FragmentRvBinding) : PostRowAdapter {
        val rv = binding.recyclerView
        adapter = PostRowAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(rv.context)
        return adapter
    }

    private fun notifyWhenFragmentForegrounded(postRowAdapter: PostRowAdapter) {
        // When we return to our fragment, notifyDataSetChanged
        // to pick up modifications to the favorites list.  Maybe do more.
        postRowAdapter.notifyDataSetChanged()
    }

    private fun initSwipeLayout(swipe : SwipeRefreshLayout) {
        swipe.setOnRefreshListener {
            viewModel.netPosts()
            swipe.isRefreshing=false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(javaClass.simpleName, "onViewCreated")
        // XXX Write me
        initAdapter(binding)
        initSwipeLayout(binding.swipeRefreshLayout)
        viewModel.observePosts().observe(viewLifecycleOwner){
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.observeSearchedRedditPosts().observe(viewLifecycleOwner){
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
        notifyWhenFragmentForegrounded(adapter)


    }
}