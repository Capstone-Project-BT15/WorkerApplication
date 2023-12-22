package corp.jasane.worker.modules.verificationBiodata.verificationTwo.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import corp.jasane.worker.data.response.KtpResponse
import corp.jasane.worker.data.retrofit.ApiServiceOCR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class VerificationTwoActivityVM(private val apiServiceOCR: ApiServiceOCR)
    :ViewModel(){

    private val _ktpResponse = MutableLiveData<KtpResponse>()
    val ktpResponse: LiveData<KtpResponse> get() = _ktpResponse

    fun captureAndUploadImage(
        image: File?,
        onSuccess: (KtpResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch() {
            try {
                if (image == null || !image.exists() || image.length() == 0L) {
                    onError("Invalid image file")
                    return@launch
                }

                val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
                val imagePart = MultipartBody.Part.createFormData(
                    "images",
                    image.name,
                    requestImageFile
                )

                try {
                    val successResponse = apiServiceOCR.scanKtp(imagePart)
                    onError(successResponse.message ?: "Unknown error")
                    onSuccess(successResponse)
                    Log.d("onSuccess", "$onSuccess")
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, KtpResponse::class.java)
                    onError(errorResponse.message ?: "Unknown error")
                }
                Log.d("cekdataLAig", "$image")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, KtpResponse::class.java)

                withContext(Dispatchers.Main) {
                    onError(errorResponse.message)
                }
            }
        }
    }
}