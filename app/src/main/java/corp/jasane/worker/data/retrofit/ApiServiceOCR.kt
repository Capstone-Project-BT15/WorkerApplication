package corp.jasane.worker.data.retrofit

import corp.jasane.worker.data.response.KtpResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceOCR {

    @Multipart
    @POST("masuk/image")
    suspend fun scanKtp(
        @Part images: MultipartBody.Part,
    ): KtpResponse
}