import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tim.coroutines.ui.home.SocialMedia
import com.tim.coroutines.ui.home.ThirdPartiesToken
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val thirdPartiesToken = ThirdPartiesToken()
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text
    init {
        initUpdates()
    }

    private fun initUpdates() {
        // Start collecting updates from the Flow
        viewModelScope.launch(Dispatchers.Main) {
            thirdPartiesToken.platformUpdates().collect { platform ->
                _text.value = platform.name
                when (platform) {
                    SocialMedia.TWITTER -> {
                        // Handle Twitter platform
                    }
                    SocialMedia.FACEBOOK -> {
                        // Handle Facebook platform
                    }
                    // Add more cases for other platforms as needed
                    else -> {

                    }
                }
            }
        }
    }

    // Update UI by callback (Not a good way)
    fun isLogin(username: String, onResult: (Boolean) -> Unit) {
        // Main thread
        Log.i("Tim", "Step 1:  ${Thread.currentThread().name}")
        viewModelScope.launch(Dispatchers.IO) {
            // I/O thread
            Log.i("Tim", "Step 2:  ${Thread.currentThread().name}")

            val result = loginRepository.login(username)

            onResult(result)
        }
        // Main thread
        Log.i("Tim", "Step 3:  ${Thread.currentThread().name}")
    }

    fun fetchPlatformToken(platform: SocialMedia) {
        _text.value = "Fetching token"
        thirdPartiesToken.fetchToken(platform)
    }

    fun cancel() {
        // thirdPartiesToken.cancelScope()
        thirdPartiesToken.cancelJob()
        _text.value = "Cancel"
    }

    fun exception() {
        thirdPartiesToken.exception()
        _text.value = "Exception"
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
