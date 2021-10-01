package com.yaromchikv.thecatapi.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yaromchikv.thecatapi.databinding.FragmentOverviewBinding
import kotlinx.coroutines.launch

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    private val imageGridAdapter: ImageGridAdapter by lazy {
        ImageGridAdapter(ImageGridAdapter.OnClickListener {
            viewModel.displayCatDetails(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }

        binding.recyclerView.apply {
            layoutManager = manager
            adapter = imageGridAdapter.withLoadStateFooter(TheCatsLoadStateAdapter())
            itemAnimator = null
        }

        imageGridAdapter.addLoadStateListener { state ->
            binding.recyclerView.isVisible = state.refresh != LoadState.Loading
            binding.progressBar.isVisible = state.refresh == LoadState.Loading
        }

        viewModel.cats.observe(viewLifecycleOwner, { newCats ->
            lifecycleScope.launch {
                imageGridAdapter.submitData(newCats)
            }
        })

        viewModel.navigateToSelectedCat.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayCatDetailsComplete()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
