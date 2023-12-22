package corp.jasane.worker.appcomponents.di

import android.content.Context
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.pref.UserPreference
import corp.jasane.worker.data.pref.dataStore
import corp.jasane.worker.data.retrofit.ApiConfig
import corp.jasane.worker.data.retrofit.ApiConfigOCR
import corp.jasane.worker.data.retrofit.ApiService
import corp.jasane.worker.data.retrofit.ApiServiceOCR

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideApiService(): ApiService {
        return ApiConfig.apiInstant
    }

    fun provideApiServiceOCR(): ApiServiceOCR {
        return ApiConfigOCR.apiInstant
    }

}