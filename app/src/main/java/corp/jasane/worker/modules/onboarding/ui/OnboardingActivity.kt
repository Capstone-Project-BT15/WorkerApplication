package corp.jasane.worker.modules.onboarding.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import corp.jasane.worker.R
import corp.jasane.worker.appcomponents.base.BaseActivity
import corp.jasane.worker.databinding.ActivityOnboardingBinding
import corp.jasane.worker.modules.home.ui.HomeActivity
import corp.jasane.worker.modules.login.ui.LoginActivity
import corp.jasane.worker.modules.onboarding.`data`.viewmodel.OnboardingVM
import kotlin.String
import kotlin.Unit

@Suppress("DEPRECATION")
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private val viewModel: OnboardingVM by viewModels<OnboardingVM>()

    private lateinit var progressDialog: Dialog

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")

        if (isOnboardingCompleted()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            binding.onboardingVM = viewModel
        }
    }

    override fun setUpClicks(): Unit {
        binding.btnMulai.setOnClickListener {
//      progressDialog = Dialog(this)
//      progressDialog.setContentView(R.layout.progress_dialog)
//      progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//      progressDialog.setCancelable(false)
//      showLoading()
            saveOnboardingStatus()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
//      Handler(Looper.getMainLooper()).postDelayed({
//        hideLoading()
//        startActivity(Intent(this, LoginActivity::class.java))
//      }, 4000)
        }
    }

    private fun saveOnboardingStatus() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("onBoarding", true)
        editor.apply()
    }

    private fun isOnboardingCompleted(): Boolean {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("onBoarding", false)
    }

//  private fun showLoading() {
//    progressDialog.show()
//  }

//  private fun hideLoading() {
//    progressDialog.dismiss()
//  }


    companion object {
        const val TAG: String = "ONBOARDING_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, OnboardingActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}
