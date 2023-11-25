package corp.jasane.worker.modules.detailJob.data.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.response.GetWorkResponse
import corp.jasane.worker.data.response.HomeUserResponse
import corp.jasane.worker.data.retrofit.ApiConfig
import corp.jasane.worker.data.retrofit.ApiService
import corp.jasane.worker.modules.detailJob.ui.DetailJobActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailJobViewModel(
    private val apiService: ApiService,
    private val userRepository: UserRepository,
): ViewModel() {

    private val _workDetail = MutableLiveData<GetWorkResponse?>()
    val workDetail: LiveData<GetWorkResponse?> get() = _workDetail

    fun init(id: Int) {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    getWorkDetailById(id, it.access_token)
                    Log.d("token", it.access_token)
                }
            }
        }
    }


    fun getWorkDetailById(id: Int, authorizationHeader: String) {
        apiService.detailWork(id, "Bearer $authorizationHeader").enqueue(object : Callback<GetWorkResponse> {
            override fun onResponse(call: Call<GetWorkResponse>, response: Response<GetWorkResponse>) {
                if (response.isSuccessful) {
                    val getWorkResponse = response.body()
                    Log.d("getWorkResponse", "$getWorkResponse")
//                    val allWorksWithDistance = getWorkResponse?.data?()
                    if (getWorkResponse != null) {
                        _workDetail.value = getWorkResponse
                        Log.d("_workDetail", "$_workDetail")
                    } else {
                    }
                } else {
                    // Handle error
                }
                Log.d("getWorkResponse", "$response")
            }

            override fun onFailure(call: Call<GetWorkResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Request", "Failed: ${t.message}", t)
            }
        })
    }

//    fun getWorkDetail(): MutableLiveData<GetWorkResponse?> {
//        return _workDetail
//    }

//    private fun getWorkDetailById(id: Int, authorizationHeader: String) {
//        apiService.detailWork(id, "Bearer $authorizationHeader").enqueue(object : Callback<GetWorkResponse> {
//            override fun onResponse(call: Call<GetWorkResponse>, response: Response<GetWorkResponse>) {
//                if (response.isSuccessful) {
//                    val getWorkResponse = response.body()
//                    val allWorksWithDistance = getWorkResponse?.data?.orEmpty()
//                    _workDetails.value = allWorksWithDistance
//                } else {
//                    // Handle error
//                }
//            }
//
//            override fun onFailure(call: Call<HomeUserResponse>, t: Throwable) {
//                // Handle failure
//                Log.e("API Request", "Failed: ${t.message}", t)
//            }
//        })
//    }

}