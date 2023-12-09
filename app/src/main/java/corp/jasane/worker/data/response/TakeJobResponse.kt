package corp.jasane.worker.data.response

import com.google.gson.annotations.SerializedName

data class TakeJobResponse(
    @SerializedName("meta") val meta: MetaTakeJob,
    @SerializedName("data") val data: DataTakeJob
)

data class MetaTakeJob(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class DataTakeJob(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("work_id") val workId: String,
    @SerializedName("address_id") val addressId: String,
    @SerializedName("tariff") val offer: String,
    @SerializedName("experience") val experience: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)
