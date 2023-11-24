package corp.jasane.worker.modules.splashscreen.`data`.model

import corp.jasane.worker.R
import corp.jasane.worker.appcomponents.di.MyApp
import kotlin.String

data class SplashScreenModel(

  var txtTitleOnboarding: String? = MyApp.getInstance().resources.getString(R.string.lbl_jasane)

)
