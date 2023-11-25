package corp.jasane.worker.modules.homeFragment.data.viewModel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.pref.UserModel
import corp.jasane.worker.data.response.HomeUserResponse
import corp.jasane.worker.data.response.WorkDetail
import corp.jasane.worker.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class HomeFragmentViewModel(private val userRepository: UserRepository, private val apiService: ApiService): ViewModel() {

    private val _workDetails = MutableLiveData<List<WorkDetail>>()
    val workDetails: LiveData<List<WorkDetail>> get() = _workDetails

    init {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    fetchWorkDetails(it.access_token)
                }
            }
        }
    }

    private fun fetchWorkDetails(authorizationHeader: String) {
        apiService.home("Bearer $authorizationHeader").enqueue(object : Callback<HomeUserResponse> {
            override fun onResponse(call: Call<HomeUserResponse>, response: Response<HomeUserResponse>) {
                if (response.isSuccessful) {
                    val homeUserResponse = response.body()
                    val allWorksWithDistance = homeUserResponse?.data?.allWorksWithDistance.orEmpty()
                    _workDetails.value = allWorksWithDistance
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<HomeUserResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Request", "Failed: ${t.message}", t)
            }
        })
    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

}