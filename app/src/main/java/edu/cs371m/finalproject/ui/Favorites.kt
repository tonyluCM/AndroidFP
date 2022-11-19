package edu.cs371m.finalproject.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import edu.cs371m.finalproject.MainActivity
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.databinding.FragmentRvBinding

class Favorites: Fragment() {
    // XXX initialize viewModel
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: PostRowAdapter
    private var _binding: FragmentRvBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance(): Favorites {
            return Favorites()
        }
    }
    //added adapter initialization
    private fun initAdapter(binding: FragmentRvBinding) : PostRowAdapter {
        val rv = binding.recyclerView
        rv.itemAnimator = null
        adapter = PostRowAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(rv.context)
        return adapter
    }
    private fun setDisplayHomeAsUpEnabled(value : Boolean) {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(value)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRvBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(javaClass.simpleName, "onViewCreated")
        setDisplayHomeAsUpEnabled(true)
        // XXX Write me
        // Setting itemAnimator = null on your recycler view might get rid of an annoying
        // flicker
        initAdapter(binding)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing=false
        }
        viewModel.setTitle("Favorites")
        viewModel.observeFavorites().observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
        /**
        viewModel.observeSearchedFavoriteList().observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()

        }
        **/


        // Add to menu
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Menu is already inflated by main activity
            }
            // XXX Write me, onMenuItemSelected
            override fun onMenuItemSelected(item: MenuItem): Boolean {
                //same as what is in hw3 media player
                val id = item.itemId
                if (id == android.R.id.home) {
                    viewModel.setTitleToSubreddit()
                    onDestroyView()
                    parentFragmentManager.commit {
                        replace(R.id.main_frame,HomeFragment.newInstance())
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    }
                    return true
                } else
                    return onMenuItemSelected(item)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        // XXX Write me
        // Don't let back to home button stay when we exit favorites
        setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}