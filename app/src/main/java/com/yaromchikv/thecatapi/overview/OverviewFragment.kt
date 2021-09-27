package com.yaromchikv.thecatapi.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yaromchikv.thecatapi.databinding.FragmentOverviewBinding
import com.yaromchikv.thecatapi.repository.Repository


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val imageGridAdapter by lazy { ImageGridAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        val repository = Repository()
        val viewModelFactory = OverviewViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        viewModel.getCat()
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                imageGridAdapter.submitList(response.body())
            } else {
                Toast.makeText(
                    requireContext(),
                    response.code(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setUpRecyclerView() {
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        binding.recyclerView.apply {
            adapter = imageGridAdapter
            layoutManager = manager
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                (recyclerView.layoutManager as StaggeredGridLayoutManager?)?.invalidateSpanAssignments()
            }
        })
    }
}
