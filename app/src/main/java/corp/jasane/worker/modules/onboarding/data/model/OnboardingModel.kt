package corp.jasane.worker.modules.onboarding.`data`.model

import corp.jasane.worker.R
import corp.jasane.worker.appcomponents.di.MyApp
import kotlin.String

data class OnboardingModel(

  var txtTitleOnboarding: String? =
      MyApp.getInstance().resources.getString(R.string.msg_selamat_datang)
  ,

  var txtBodyTextOnboard: String? =
      MyApp.getInstance().resources.getString(R.string.msg_tempat_di_mana)

)
