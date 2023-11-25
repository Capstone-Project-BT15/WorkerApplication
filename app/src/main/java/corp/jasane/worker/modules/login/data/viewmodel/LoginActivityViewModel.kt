package corp.jasane.worker.modules.login.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.pref.UserModel
import corp.jasane.worker.data.retrofit.ApiService
import corp.jasane.worker.data.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call

class LoginActivityViewModel(
    private val userRepository: UserRepository,
    private val apiService: ApiService
) : ViewModel() {

    fun login(email: String, password: String): Call<LoginResponse> {
        return apiService.login(email, password)
    }


    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
}