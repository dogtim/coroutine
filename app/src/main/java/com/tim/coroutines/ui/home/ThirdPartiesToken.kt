package com.tim.coroutines.ui.home

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThirdPartiesToken {
    // The Job represents the current state of the coroutine, and Dispatchers.IO specifies that the coroutines launched within this scope will run on the IO thread.
    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    // Create a MutableStateFlow to emit the platform information
    private val platformFlow = MutableSharedFlow<SocialMedia>(0)

    // Expose the platformFlow as a Flow for external subscribers
    fun platformUpdates(): Flow<SocialMedia> = platformFlow.asSharedFlow()
    fun fetchToken(platform: SocialMedia) {
        scope.launch {
            platformFlow.emit(fetchTokenFrom(platform))
        }
    }

    fun cleanUp() {
        // Cancel the scope to cancel ongoing coroutines work
        scope.cancel()
    }

    private suspend fun fetchTokenFrom(platform: SocialMedia): SocialMedia {                             // Dispatchers.Main
        // Fetch token from platform
        delay(3000)
        return platform
    }
}

enum class SocialMedia {
    TWITTER,
    FACEBOOK,
    INSTAGRAM,
    YOUTUBE,
    NONE
}