package com.tim.coroutines.ui.home

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.logging.SocketHandler

class ThirdPartiesToken {
    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    // Create a MutableStateFlow to emit the platform information
    private val platformFlow = MutableStateFlow(SocialMedia.NONE)

    // Expose the platformFlow as a Flow for external subscribers
    fun platformUpdates(): Flow<SocialMedia> = platformFlow.asStateFlow()
    fun fetchToken(platform: SocialMedia) {
        scope.launch {
            fetchTokenFrom(platform)
            Log.d("Tim", "fetchToken: ")
            platformFlow.value = platform
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