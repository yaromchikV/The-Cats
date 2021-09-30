package com.yaromchikv.thecatapi.detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.BlurTransformation
import com.yaromchikv.thecatapi.R
import com.yaromchikv.thecatapi.databinding.FragmentDetailBinding
import com.yaromchikv.thecatapi.model.Cat

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // Permission Request Handler
    private var requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

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

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    private val cat: Cat by lazy {
        DetailFragmentArgs.fromBundle(requireArguments()).selectedCat
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.catId.text = getString(R.string.cat_id, cat.id)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_image) {
            checkPermissionAndSaveTheImage(cat)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private var alertDialog: AlertDialog? = null

    private fun checkPermissionAndSaveTheImage(cat: Cat) {
        val checkPermission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        when {
            checkPermission == PackageManager.PERMISSION_GRANTED -> {
                viewModel.saveTheCat(cat)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                showPermissionDialog()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun showPermissionDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_warning)
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            .setNegativeButton(R.string.cancel, null)
            .setCancelable(false)
        alertDialog = builder.create()
        alertDialog?.show()
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        if (alertDialog != null)
            state.putBoolean("dialog", alertDialog!!.isShowing)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.getBoolean("dialog", true)) {
            showPermissionDialog()
        }
    }
}
