package corp.jasane.worker.modules.onboarding.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.pref.UserModel
import corp.jasane.worker.modules.onboarding.`data`.model.OnboardingModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class OnboardingVM : ViewModel(), KoinComponent {
  private val userRepository: UserRepository by inject()
  val onboardingModel: MutableLiveData<OnboardingModel> = MutableLiveData(OnboardingModel())

  var navArguments: Bundle? = null

  fun getSession(): LiveData<UserModel> {
    return userRepository.getSession().asLiveData()
  }
}
