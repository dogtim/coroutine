import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        // Main thread
        Log.i("Tim", "Step 1:  ${Thread.currentThread().name}")
        viewModelScope.launch(Dispatchers.IO) {
            // I/O thread
            Log.i("Tim", "Step 2:  ${Thread.currentThread().name}")
            val isSuccess = loginRepository.login(username, password)
            // Notify the result on the main thread
            onResult(isSuccess)
        }
        // Main thread
        Log.i("Tim", "Step 3:  ${Thread.currentThread().name}")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    loginRepository = LoginRepository()
                )
            }
        }
    }
}
