import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginRepository {

    // Simulate a network login request
    suspend fun login(username: String, password: String): Boolean {
        // Simulate network delay
        delay(1000)
        if (username == "Exception") {
            throw Exception("Network request failed")
        }
        // Here, you can add your actual login logic, like API calls
        // For this example, we'll simulate a successful login
        return true
    }


}
