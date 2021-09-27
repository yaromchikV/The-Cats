package com.yaromchikv.thecatapi.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.BlurTransformation
import com.yaromchikv.thecatapi.R
import com.yaromchikv.thecatapi.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cat = DetailFragmentArgs.fromBundle(requireArguments()).selectedCat
        val viewModelFactory = DetailViewModelFactory(cat)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.catId.text = "id: ${cat.id}"
        binding.backgroundImage.load(cat.imageUrl) {
            transformations(BlurTransformation(requireContext(), 15f))
        }
        binding.detailImageView.load(cat.imageUrl) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
            crossfade(true)
            crossfade(100)
        }
    }
}