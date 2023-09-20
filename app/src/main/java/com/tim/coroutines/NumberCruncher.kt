package com.tim.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Simulate high CPU load
// Reference: https://craigrussell.io/2021/12/testing-android-coroutines-using-runtest/
class NumberCruncher(
    private val coroutineScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) {
    private val _results = MutableSharedFlow<Int>()
    fun results() = _results.asSharedFlow()

    suspend fun getResult() = withContext(defaultDispatcher) {
        return@withContext longRunningOperation()
    }

    fun calculate() {
        // 👇 using dispatcher provider avoids hardcoding dispatcher, allowing for us to use a `TestDispatcher` while testing
        coroutineScope.launch {
            val result = longRunningOperation()
            delay(5_000)
            _results.emit(result)
        }
    }

    private fun longRunningOperation(): Int {
        val list = mutableListOf<Int>()

        for (i in 0..1_000) {
            list.add(i)
        }
        for (i in 0..20_000) {
            list.shuffle()
            list.sort()
        }

        return list.first()
    }

}