package corp.jasane.worker.appcomponents.di

import android.content.Context
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.data.pref.UserPreference
import corp.jasane.worker.data.pref.dataStore
import corp.jasane.worker.data.retrofit.ApiConfig
import corp.jasane.worker.data.retrofit.ApiService

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideApiService(): ApiService {
        return ApiConfig.apiInstant
    }

//    fun provideStoryRepository(context: Context, userPreference: UserPreference): StoryRepository {
////        val database = StoryDatabase.getDatabase(context)
//        val apiService = ApiConfig.apiInstant
//        return StoryRepository(database, apiService, userPreference)
//    }

}