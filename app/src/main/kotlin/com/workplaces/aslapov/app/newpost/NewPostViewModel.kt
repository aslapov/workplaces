package com.workplaces.aslapov.app.newpost

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.workplaces.aslapov.app.base.viewmodel.BaseViewModel
import com.workplaces.aslapov.app.base.viewmodel.MessageEvent
import com.workplaces.aslapov.domain.newpost.NewPostException
import com.workplaces.aslapov.domain.newpost.NewPostUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewPostViewModel @Inject constructor(
    private val newPostUseCase: NewPostUseCase,
) : BaseViewModel<NewPostViewState>() {

    init {
        createInitialState()
    }

    fun onPostTextChanged(post: String) {
        state = state.copy(text = post)
    }

    fun onPostAddClicked() {
        viewModelScope.launch {
            try {
                newPostUseCase.addNewPost(
                    text = state.text,
                    imageFile = state.image,
                    lon = null,
                    lat = null,
                )
                navigateUp()
            } catch (e: NewPostException) {
                eventsQueue.offerEvent(MessageEvent(e.messageId))
            }
        }
    }

    fun onBackClicked() {
        navigateUp()
    }

    fun onImageTook(image: Bitmap) {
        state = state.copy(image = image)
    }

    private fun createInitialState() {
        state = NewPostViewState("", null)
    }
}

data class NewPostViewState(
    val text: String,
    val image: Bitmap?,
)
