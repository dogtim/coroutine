import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginRepository(private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) {

    // Simulate a network login request
    suspend fun login(username: String): Boolean {
        // Simulate network delay
        delay(10000)

        if (username == "Exception") {
            // throw Exception("Network request failed")
            return false
        }
        // Here, you can add your actual login logic, like API calls
        // For this example, we'll simulate a successful login
        return true
    }

    suspend fun loadUserName() = withContext(defaultDispatcher) {
        delay(1000)
        return@withContext "Tim"
    }

}
