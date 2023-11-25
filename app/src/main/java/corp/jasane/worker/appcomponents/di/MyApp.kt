package corp.jasane.worker.appcomponents.di

import android.app.Application
import corp.jasane.worker.appcomponents.utility.PreferenceHelper
import corp.jasane.worker.modules.home.data.viewModel.HomeActivityViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * The application class which used to start koin for dependency injection
 */
class MyApp : Application() {

    public override fun onCreate(): Unit {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            loadKoinModules(getKoinModules())
        }
    }

    /**
     * method which prepares [PreferenceHelper]s koin module
     * @return [Module] - the koin module
     */
    private fun preferenceModule(): Module {
        val prefsModule = module {
            single {
                PreferenceHelper()
            }
            single { HomeActivityViewModel(get()) }
        }
        return prefsModule
    }

    private fun homeViewModelModule(): Module {
        return module(override = true) {
            viewModel { HomeActivityViewModel(get()) }
        }
    }

    /**
     * method which returns the list of koin module to register
     * @return MutableList<Module> - list of koin modules
     */
    private fun getKoinModules(): MutableList<Module> {
        val koinModules = mutableListOf<Module>()
        koinModules.add(preferenceModule())
        koinModules.add(homeViewModelModule())
        return koinModules
    }

    public companion object {

        // the application instance
        private lateinit var instance: MyApp

        /**
         * method to get instance of application object
         */
        public fun getInstance(): MyApp = instance
    }
}