package corp.jasane.worker.modules.onboarding.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import corp.jasane.worker.R
import corp.jasane.worker.appcomponents.base.BaseActivity
import corp.jasane.worker.databinding.ActivityOnboardingBinding
import corp.jasane.worker.modules.detailJob.ui.detailJobActivity
import corp.jasane.worker.modules.login.ui.LoginActivity
import corp.jasane.worker.modules.onboarding.`data`.viewmodel.OnboardingVM
import corp.jasane.worker.modules.register.ui.registerActivity
import kotlin.String
import kotlin.Unit

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
  private val viewModel: OnboardingVM by viewModels<OnboardingVM>()

  private lateinit var progressDialog: Dialog

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.onboardingVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btnMulai.setOnClickListener {
      progressDialog = Dialog(this)
      progressDialog.setContentView(R.layout.progress_dialog)
      progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
      progressDialog.setCancelable(false)
      showLoading()
      Handler(Looper.getMainLooper()).postDelayed({
        hideLoading()
        startActivity(Intent(this, LoginActivity::class.java))
      }, 4000)
    }
  }

  private fun showLoading() {
    progressDialog.show()
  }

  private fun hideLoading() {
    progressDialog.dismiss()
  }


  companion object {
    const val TAG: String = "ONBOARDING_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, OnboardingActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
