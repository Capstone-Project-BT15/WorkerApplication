package corp.jasane.worker.modules.takeJob.data.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.response.AddressJob
import corp.jasane.worker.data.response.GetJobDetailResponse
import corp.jasane.worker.data.response.GetWork
import corp.jasane.worker.data.response.HomeUserResponse
import corp.jasane.worker.data.response.RegisterResponse
import corp.jasane.worker.data.response.TakeJobResponse
import corp.jasane.worker.data.response.WorkDetail
import corp.jasane.worker.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class TakeJobViewModel(
    private val apiService: ApiService,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val workDetail = MutableLiveData<GetWork?>()
    private val addressDetail = MutableLiveData<AddressJob?>()
    private val _offerJobSuccess = MutableLiveData<Boolean>()
    val offerJobSuccess: LiveData<Boolean> get() = _offerJobSuccess
    val _workDetail: LiveData<GetWork?> get() = workDetail
    val _addressDetail: LiveData<AddressJob?> get() = addressDetail

    fun init(id: Int) {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    fetchWorkDetails(id, it.access_token)
                }
            }
        }
    }

    private fun fetchWorkDetails(id: Int, authorizationHeader: String) {
        apiService.getJobDetail(id, "Bearer $authorizationHeader")
            .enqueue(object : Callback<GetJobDetailResponse> {
                override fun onResponse(
                    call: Call<GetJobDetailResponse>,
                    response: Response<GetJobDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val getJobDetailResponse = response.body()
                        val workDetailData = getJobDetailResponse?.data
                        val work = workDetailData?.work
                        val address = workDetailData?.address

                        workDetail.value = work
                        addressDetail.value = address
                    } else {
                        // Handle error
                    }
                }

                override fun onFailure(call: Call<GetJobDetailResponse>, t: Throwable) {
                    Log.e("API Request", "Failed: ${t.message}", t)
                }
            })
    }

    fun offerJob(
        workId: Int,
        addressId: Int,
        offer: String,
        experience: String
    ) {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    apiService.offerJob(
                        workId,
                        addressId,
                        offer,
                        experience,
                        "Bearer ${it.access_token}"
                    )
                        .enqueue(object : Callback<TakeJobResponse> {
                            override fun onResponse(
                                call: Call<TakeJobResponse>,
                                response: Response<TakeJobResponse>
                            ) {
                                _offerJobSuccess.value = response.isSuccessful
                                Log.d("offerJobviewmodel", "$response")
                            }

                            override fun onFailure(call: Call<TakeJobResponse>, t: Throwable) {
                                // Handle failure
                                Log.e("API Request", "Failed: ${t.message}", t)
                            }
                        })
                    Log.d("offerJobviewmodel", "$workId, $addressId, $offer, $experience, ${it.access_token}")
                }
            }
        }
    }
}