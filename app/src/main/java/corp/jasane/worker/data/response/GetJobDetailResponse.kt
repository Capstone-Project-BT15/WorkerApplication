package corp.jasane.worker.data.response

import com.google.gson.annotations.SerializedName

data class GetJobDetailResponse(
    @SerializedName("meta") val meta: MetaJobDetail,
    @SerializedName("data") val data: GetJobDetailData
)

data class MetaJobDetail(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class GetJobDetailData(
    @SerializedName("work") val work: GetWork,
    @SerializedName("address") val address: AddressJob
)

data class GetWork(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("min_budget") val minBudget: String,
    @SerializedName("max_budget") val maxBudget: String,
    @SerializedName("type_of_work") val typeOfWork: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("description") val description: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("distance") val distance: Double,
    @SerializedName("distance_to_user") val distanceToUser: String
)

data class AddressJob(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: String,
    @SerializedName("fullname") val fullName: String,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("province") val province: String,
    @SerializedName("city") val city: String,
    @SerializedName("subdistrict") val subDistrict: String,
    @SerializedName("village") val typeOfWork: String,
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)

