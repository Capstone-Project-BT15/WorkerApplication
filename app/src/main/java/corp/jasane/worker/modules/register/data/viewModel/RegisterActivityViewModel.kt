package corp.jasane.worker.modules.register.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.pref.UserModel
import corp.jasane.worker.data.response.LoginResponse
import corp.jasane.worker.data.response.RegisterResponse
import corp.jasane.worker.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call

class RegisterActivityViewModel(
    private val userRepository: UserRepository,
    private val apiService: ApiService
) : ViewModel()
  {
      fun register(telephone: String, email: String, password: String, passwordConfirmation: String): Call<RegisterResponse> {
          return apiService.register(telephone, email, password, passwordConfirmation)
      }


      fun saveSession(user: UserModel) {
          viewModelScope.launch {
              userRepository.saveSession(user)
          }
      }
}