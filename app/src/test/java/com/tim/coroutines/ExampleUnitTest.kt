package com.tim.coroutines

import LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

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

    // demo Inject TestDispatchers in tests
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testLoadNewsHighCPULoad() = runTest {
        // Pass the testScheduler provided by runTest's coroutine scope to
        // the test dispatcher
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val numberCruncher = NumberCruncher(this, testDispatcher)
        val result = numberCruncher.getResult()
        assertEquals(0, result)
    }

    // Demo the different scope
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testLoadNewsHighCPULoadWithSharedObject() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        // val customizedScope = CoroutineScope(Dispatchers.Default)
        val numberCruncher = NumberCruncher(this, testDispatcher)
        numberCruncher.calculate()
        assertEquals(0, numberCruncher.results().first())
    }
}