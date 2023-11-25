package corp.jasane.worker.modules.splashscreen.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import corp.jasane.worker.data.pref.UserModel
import corp.jasane.worker.modules.splashscreen.`data`.model.SplashScreenModel
import org.koin.core.KoinComponent

class SplashScreenVM : ViewModel(), KoinComponent {
  val splashScreenModel: MutableLiveData<SplashScreenModel> = MutableLiveData(SplashScreenModel())

  var navArguments: Bundle? = null

//  fun getSession(): LiveData<UserModel> {
//    return userRepository.getSession().asLiveData()
//  }
}
