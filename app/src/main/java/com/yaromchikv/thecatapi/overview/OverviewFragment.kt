package com.yaromchikv.thecatapi.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        val repository = Repository()
        val viewModelFactory = OverviewViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        viewModel.getCat()
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.forEach {
                    Log.d("!!!", it.id)
                    Log.d("!!!", it.url)
                    Log.d("!!!", it.width.toString())
                    Log.d("!!!", it.height.toString())
                }
            } else {
                Log.d("!!!", response.errorBody().toString())
            }
        })
    }
}
