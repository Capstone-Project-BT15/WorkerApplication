package corp.jasane.worker.modules.profile.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.retrofit.ApiService
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(private val repository: UserRepository, private val apiService: ApiService) : ViewModel() {

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean>
        get() = _logoutSuccess

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _logoutSuccess.postValue(true)
        }
    }

}