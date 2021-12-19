package org.skyfaced.smartremont.ui.gallery

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.FragmentGalleryBinding
import org.skyfaced.smartremont.model.adapter.PhotoItem
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.ui.common.BaseState
import org.skyfaced.smartremont.util.extensions.flowObserver
import org.skyfaced.smartremont.util.extensions.lazySafetyNone
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener
import org.skyfaced.smartremont.util.extensions.showSnack
import org.skyfaced.smartremont.util.ui.SquareItemDecoration

class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {
    private val viewModel by viewModel<GalleryViewModel>()

    private val photoAdapter by lazySafetyNone { PhotoAdapter() }

    private val takePhotoResult =
        registerForActivityResult(ActivityResultContracts.TakePicture(), ::onPhotoTaken)
    private val selectPhotoResult =
        registerForActivityResult(ActivityResultContracts.GetContent(), ::onPhotoSelected)

    private var lastSavedUri: Uri? = null

    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentGalleryBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        setupObserver()
    }

    private fun setupContent() = binding {
        with(recyclerView) {
            val offset = resources.getDimensionPixelOffset(R.dimen.offset_8dp)
            addItemDecoration(SquareItemDecoration(offset))
            adapter = photoAdapter
        }

        btnAddPhoto.setOnDebounceClickListener {
            selectPhotoResult.launch("image/*")
        }

        btnTakePhoto.setOnDebounceClickListener {
            viewModel.tempFileUri.let {
                logcat { it.toString() }
                lastSavedUri = it
                takePhotoResult.launch(it)
            }
        }
    }

    private fun setupObserver() {
        flowObserver(viewModel.galleryState) { state ->
            when (state) {
                is BaseState.OnFailure -> onFailure()
                is BaseState.OnLoading -> onLoading()
                is BaseState.OnSuccess -> onSuccess(state.data)
            }
        }
    }

    private fun onSuccess(items: List<PhotoItem>) = binding {
        progressBar.isVisible = false
        photoAdapter.submitList(items)
    }

    private fun onLoading() = binding {
        progressBar.isVisible = true
    }

    private fun onFailure() = binding {
        progressBar.isVisible = false
        showSnack(R.string.error_something_went_wrong)
    }

    private fun onPhotoTaken(isSuccess: Boolean) {
        if (isSuccess) lastSavedUri?.let {
            updateAdapter(it)
        }
    }

    private fun onPhotoSelected(uri: Uri?) {
        uri?.let {
            updateAdapter(viewModel.saveToTemp(it))
        }
    }

    private fun updateAdapter(uri: Uri) = binding {
        root.postDelayed(
            {
                viewModel.list.add(PhotoItem(uri))
                photoAdapter.submitList(viewModel.list)
            },
            1_500L
        )
    }

    companion object {
        const val SCREEN_KEY = "galleryFragmentScreen"
    }
}