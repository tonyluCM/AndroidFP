package edu.cs371m.finalproject.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import edu.cs371m.finalproject.R
import edu.cs371m.finalproject.databinding.FragmentRvBinding
import edu.cs371m.finalproject.ui.AuthInit
import edu.cs371m.finalproject.ui.MainViewModel

class Categories : Fragment() {
    // XXX initialize viewModel
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentRvBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryListAdapter
    companion object {
        fun newInstance(): Categories {
            return Categories()
        }
    }

    private fun initAdapter(binding: FragmentRvBinding) : CategoryListAdapter {
        val recyclerView = binding.recyclerView
        adapter = CategoryListAdapter(viewModel,this.requireActivity())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        return adapter
    }

    private fun initSwipeLayout(swipe : SwipeRefreshLayout) {
        swipe.setOnRefreshListener {
            viewModel.netCategories()
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

    // XXX Write me, onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(binding)
        initSwipeLayout(binding.swipeRefreshLayout)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing=false
        }
        viewModel.setTitle("Categories")
        viewModel.observeCategory().observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}