package com.yaromchikv.thecatapi.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val firstVisibleItemPositions = manager.findFirstVisibleItemPositions(null)
                val firstVisibleItemPosition = firstVisibleItemPositions[0]

                if (viewModel.isUploaded.value == false) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                        firstVisibleItemPosition >= 0) {
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.loadMoreCats()
                    }
                }
            }
        })

        viewModel.isUploaded.observe(viewLifecycleOwner, { uploaded ->
            if (uploaded == false) {
                binding.progressBar.visibility = View.INVISIBLE
                imageGridAdapter.submitList(viewModel.listOfCats)
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
