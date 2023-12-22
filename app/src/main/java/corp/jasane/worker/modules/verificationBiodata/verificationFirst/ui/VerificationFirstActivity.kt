package corp.jasane.worker.modules.verificationBiodata.verificationFirst.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import corp.jasane.worker.R
import corp.jasane.worker.databinding.ActivityVerificationFirstBinding
import corp.jasane.worker.modules.verificationBiodata.verificationTwo.VerificationTwoActivity

class VerificationFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar

        binding.lottieViewCheck.setAnimation(R.raw.ic_check)
        binding.buttonSave.setOnClickListener {
            val intent = Intent(this, VerificationTwoActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        setupView()
    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.verification_identity)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}