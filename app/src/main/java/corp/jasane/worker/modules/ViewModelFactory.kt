package corp.jasane.worker.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import corp.jasane.worker.appcomponents.di.Injection
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.retrofit.ApiService
import corp.jasane.worker.modules.detailJob.data.viewModel.DetailJobViewModel
import corp.jasane.worker.modules.home.data.viewModel.HomeActivityViewModel
import corp.jasane.worker.modules.homeFragment.data.viewModel.HomeFragmentViewModel
import corp.jasane.worker.modules.login.data.viewmodel.LoginActivityViewModel
import corp.jasane.worker.modules.profile.data.viewModel.ProfileFragmentViewModel
import corp.jasane.worker.modules.register.data.viewModel.RegisterActivityViewModel
import corp.jasane.worker.modules.takeJob.data.viewModel.TakeJobViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val apiService: ApiService,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginActivityViewModel::class.java) -> {
                LoginActivityViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(RegisterActivityViewModel::class.java) -> {
                RegisterActivityViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(HomeActivityViewModel::class.java) -> {
                HomeActivityViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) -> {
                HomeFragmentViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java) -> {
                ProfileFragmentViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(DetailJobViewModel::class.java) -> {
                DetailJobViewModel(apiService, userRepository) as T
            }
            modelClass.isAssignableFrom(TakeJobViewModel::class.java) -> {
                TakeJobViewModel(apiService, userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context),
                        Injection.provideApiService(),
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}