package corp.jasane.worker.data.response

import android.os.Message

data class LoginResponse(
//    val error: Boolean,
    val message: String,
    val access_token: String,
    val token_type: String
)
//    val loginResult: LoginResult?
//) {
//    data class LoginResult (
//        val userId: String,
//        val name: String,
//        val token: String
//    )
//}