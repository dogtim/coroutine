package com.tim.coroutines

import LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testLoadNewsWithInjectedDispatcher() = runTest {
        // Create a TestCoroutineDispatcher for testing
        val testDispatcher = StandardTestDispatcher()

        // Instantiate the repository with the test dispatcher
        val repository = LoginRepository(testDispatcher)

        // Perform the test
        val resultSuccess = repository.login("username")
        val resultFailed = repository.login("Exception")

        assertEquals(true, resultSuccess)
        assertEquals(false, resultFailed)
    }
}