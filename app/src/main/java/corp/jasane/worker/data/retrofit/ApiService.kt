package corp.jasane.worker.data.retrofit

import corp.jasane.worker.data.response.GetJobDetailResponse
import corp.jasane.worker.data.response.GetWorkResponse
import corp.jasane.worker.data.response.HomeUserResponse
import corp.jasane.worker.data.response.LoginResponse
import corp.jasane.worker.data.response.RegisterResponse
import corp.jasane.worker.data.response.TakeJobResponse
import corp.jasane.worker.data.response.WorkDetail
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
        @Field("fullname") fullName: String,
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

    @GET("api/works/{id}")
    fun detailWork(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<GetWorkResponse>

    @GET("api/get-job/{id}")
    fun getJobDetail(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<GetJobDetailResponse>

//    @FormUrlEncoded
//    @POST("register")
//    fun register(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<RegisterResponse>
//
//    @GET("stories")
//    suspend fun getAllStories(
//        @Query("page") page: Int,
//        @Query("size") size: Int,
//        @Header("Authorization") authorization: String
//    ): StoryResponse
//
//    @GET("stories/{id}")
//    fun detailStories(
//        @Path("id") username: String,
//        @Header("Authorization") authorization: String,
//    ): Call<DetailStoryResponse>
//
//    @Multipart
//    @POST("stories")
//    suspend fun AddStory(
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") lat: RequestBody?,
//        @Part("lon") lon: RequestBody?,
//        @Header("Authorization") authorization: String,
//    ): AddStoryResponse
//
//    @GET("stories")
//    suspend fun getStoriesWithLocation(
//        @Query("location") location : Int,
//        @Header("Authorization") authorization: String,
//    ): Response<StoriesResponse>
}