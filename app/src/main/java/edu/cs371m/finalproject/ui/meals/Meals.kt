package edu.cs371m.finalproject.ui.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import edu.cs371m.finalproject.databinding.FragmentRvBinding
import edu.cs371m.finalproject.ui.MainViewModel

class Meals : Fragment() {
    // XXX initialize viewModel
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentRvBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: MealListAdapter

    companion object {
        fun newInstance(): Meals {
            return Meals()
        }
    }

    private fun initAdapter(binding: FragmentRvBinding) : MealListAdapter {
        val recyclerView = binding.recyclerView
        adapter = MealListAdapter(viewModel,this.requireActivity())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        return adapter
    }
    private fun notifyWhenFragmentForegrounded(meallistadapter: MealListAdapter) {
        // When we return to our fragment, notifyDataSetChanged
        // to pick up modifications to the favorites list.  Maybe do more.
        meallistadapter.notifyDataSetChanged()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRvBinding.inflate(inflater, container, false)
        return binding.root
    }

    // XXX Write me, onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.netMealsInCategory()
        initAdapter(binding)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing=false
        }
       // viewModel.setTitle("Pick")

        viewModel.observeMealsInCategory().observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
        notifyWhenFragmentForegrounded(adapter)
        /**
        viewModel.observeSearchedSubRedditList().observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
        **/

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}