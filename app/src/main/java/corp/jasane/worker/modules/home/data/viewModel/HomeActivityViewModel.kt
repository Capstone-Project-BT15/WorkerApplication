package corp.jasane.worker.modules.home.data.viewModel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.pref.UserModel

class HomeActivityViewModel(private val userRepository: UserRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

}