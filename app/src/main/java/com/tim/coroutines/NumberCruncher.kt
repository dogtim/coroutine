package com.tim.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Simulate high CPU load
// Reference: https://craigrussell.io/2021/12/testing-android-coroutines-using-runtest/
class NumberCruncher(private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) {

    suspend fun getResult() = withContext(defaultDispatcher) {
        return@withContext longRunningOperation()
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