package com.workplaces.aslapov.app.newpost

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import coil.load
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.workplaces.aslapov.R
import com.workplaces.aslapov.app.base.fragment.BaseFragment
import com.workplaces.aslapov.databinding.NewPostFragmentBinding
import com.workplaces.aslapov.di.DI
import timber.log.Timber

class NewPostFragment : BaseFragment(R.layout.new_post_fragment) {

    companion object {
        private const val TAG = "NewPostFragment"
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val RESULT_INTENT_EXTRA_DATA = "data"
    }

    private val viewModel: NewPostViewModel by viewModels { viewModelFactory }
    private val binding: NewPostFragmentBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DI.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showKeyBoard()

        observe(viewModel.eventsQueue, ::onEvent)

        with(binding) {
            newPostToolbar.setOnClickListener {
                hideKeyBoard()
                viewModel.onBackClicked()
            }

            newPostText.apply {
                doAfterTextChanged { viewModel.onPostTextChanged(it.toString()) }
                requestFocus()
            }

            newPostSave.setOnClickListener {
                hideKeyBoard()
                viewModel.onPostAddClicked()
            }

            newPostAddImage.setOnClickListener {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    @Suppress("DEPRECATION")
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                } catch (e: ActivityNotFoundException) {
                    Timber.tag(TAG).d(e)
                    showMessage(getString(R.string.new_post_camera_activity_not_found))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get(RESULT_INTENT_EXTRA_DATA) as Bitmap
            binding.newPostImage.load(imageBitmap)
            viewModel.onImageTook(imageBitmap)
        }
    }

    private fun showKeyBoard() {
        val imgr: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun hideKeyBoard() {
        val imgr: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
