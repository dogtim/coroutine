package com.tim.coroutines

import LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // Demo skip the delay()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testLoadNewsWithInjectedDispatcher() = runTest {
        // Create a TestCoroutineDispatcher for testing
        val repository = LoginRepository()
        val resultSuccess = repository.login("username")
        val resultFailed = repository.login("Exception")

        assertEquals(true, resultSuccess)
        assertEquals(false, resultFailed)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test

    fun testLoadNewsHighCPULoad() = runTest {
        // Pass the testScheduler provided by runTest's coroutine scope to
        // the test dispatcher
        val testDispatcher = StandardTestDispatcher(testScheduler)
        val numberCruncher = NumberCruncher(testDispatcher)
        val result = numberCruncher.getResult()
        assertEquals(0, result)
    }
}