package com.yaromchikv.thecatapi.overview

import android.content.res.Configuration
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

    private lateinit var binding: FragmentOverviewBinding

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
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val columnsCount = if (activity?.resources?.configuration?.orientation ==
            Configuration.ORIENTATION_PORTRAIT
        ) COLUMNS_COUNT_PORTRAIT else COLUMNS_COUNT_LANDSCAPE

        val manager = StaggeredGridLayoutManager(columnsCount, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        binding.recyclerView.apply {
            layoutManager = manager
            adapter = imageGridAdapter.withLoadStateFooter(ProgressStateAdapter())
            itemAnimator = null
        }

        imageGridAdapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh is LoadState.Loading
            binding.connectionError.isVisible = it.refresh is LoadState.Error
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

    companion object {
        private const val COLUMNS_COUNT_PORTRAIT = 2
        private const val COLUMNS_COUNT_LANDSCAPE = 4
    }
}
