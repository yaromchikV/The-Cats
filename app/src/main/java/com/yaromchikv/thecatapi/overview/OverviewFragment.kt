package com.yaromchikv.thecatapi.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        val repository = Repository()
        val viewModelFactory = OverviewViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        val imageGridAdapter = ImageGridAdapter(ImageGridAdapter.OnClickListener {
            viewModel.displayCatDetails(it)
        })

        binding.recyclerView.apply {
            layoutManager = manager
            adapter = imageGridAdapter
            itemAnimator = null
        }

        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                imageGridAdapter.submitList(response.body())
                Log.d("LOG_TAG", response.headers().toString())
            } else {
                Toast.makeText(
                    requireContext(),
                    response.code(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.navigateToSelectedCat.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayCatDetailsComplete()
            }
        })
    }
}
