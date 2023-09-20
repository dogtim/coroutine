package com.tim.coroutines.ui.home

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

class ThirdPartiesToken {
    private var default = CoroutineExceptionHandler { context, throwable ->
        Log.e("Tim", "context: $context")
        Log.e("Tim", "throwable: $throwable")
    }
    // The Job represents the current state of the coroutine, and Dispatchers.IO specifies that the coroutines launched within this scope will run on the IO thread.
    private var scope = CoroutineScope(Job() + Dispatchers.IO + default)
    // Create a MutableStateFlow to emit the platform information
    private val platformFlow = MutableSharedFlow<SocialMedia>(0)
    private var job: Job? = null
    // Expose the platformFlow as a Flow for external subscribers
    fun platformUpdates(): Flow<SocialMedia> = platformFlow.asSharedFlow()
    fun fetchToken(platform: SocialMedia) {

        job = scope.launch {
            Log.i("Tim", "fetchToken thread:  ${Thread.currentThread().name}")
            platformFlow.emit(fetchTokenFrom(platform))
        }
    }

    fun cancelScope() {
        // Cancel the scope to cancel ongoing coroutines work
        scope.cancel()

        // If not create new scope, the canceled one can not launch anymore
        // scope = CoroutineScope(Job() + Dispatchers.IO)
    }

    fun cancelJob() {
        // Cancel the coroutine started above, this doesn't affect the scope
        // this coroutine was launched in
        job?.cancel()
    }

    fun exception() {
        // Cancel the coroutine started above, this doesn't affect the scope
        // this coroutine was launched in

        scope.launch(CoroutineName("Exception")) {
            Log.i("Tim", "coroutineContext name:  ${coroutineContext[CoroutineName]}")

            //try {
                throw Exception("trigger Exception")
            //} catch (e: Throwable) {
                // handle exception
            //}

        }
    }

    private suspend fun fetchTokenFrom(platform: SocialMedia): SocialMedia {
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