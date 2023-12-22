package corp.jasane.worker.data.retrofit

import corp.jasane.worker.data.response.BiodataResponse
import corp.jasane.worker.data.response.GetJobDetailResponse
import corp.jasane.worker.data.response.GetWorkResponse
import corp.jasane.worker.data.response.HomeUserResponse
import corp.jasane.worker.data.response.LoginResponse
import corp.jasane.worker.data.response.RegisterResponse
import corp.jasane.worker.data.response.TakeJobResponse
import corp.jasane.worker.data.response.WorkDetail
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("api/login/user")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/register/user")
    fun register(
        @Field("telephone") telephone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/place-an-offer")
    fun offerJob(
        @Field("work_id") workId: Int,
        @Field("address_id") addressId: Int,
        @Field("tariff") offer: String,
        @Field("experience") experience: String,
        @Header("Authorization") authorization: String
    ): Call<TakeJobResponse>

    @GET("api/home/user")
    fun home(
        @Header("Authorization") authorization: String
    ): Call<HomeUserResponse>

    @GET("api/works/user/{id}")
    fun detailWork(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<GetWorkResponse>

    @GET("api/get-job/{id}")
    fun getJobDetail(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<GetJobDetailResponse>

    @Multipart
    @POST("api/biodata")
    fun insertBiodata(
        @Part("nik") nik: RequestBody,
        @Part("fullname") fullName: RequestBody,
        @Part("birthday") birthday: RequestBody,
        @Part("telephone") telephone: RequestBody,
        @Part("province") province: RequestBody,
        @Part("city") city: RequestBody,
        @Part("subdistrict") subDistrict: RequestBody,
        @Part("village") village: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") lat: RequestBody,
        @Part("longitude") lon: RequestBody,
        @Header("Authorization") authorization: String
    ): Call<BiodataResponse>
}