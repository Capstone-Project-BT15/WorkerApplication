package corp.jasane.worker.modules.register.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import corp.jasane.worker.R
import corp.jasane.worker.databinding.ActivityLoginBinding
import corp.jasane.worker.databinding.ActivityRegisterBinding
import corp.jasane.worker.modules.login.ui.LoginActivity

class registerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val constraintLayout: ConstraintLayout = findViewById(R.id.constraintRegister)

        constraintLayout.setOnTouchListener { v, event ->
            val view: View? = currentFocus
            if (view != null) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            v.performClick()
            true
        }
        setupAction()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.textLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}