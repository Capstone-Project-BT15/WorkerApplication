package corp.jasane.worker.modules.login.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import corp.jasane.worker.R
import corp.jasane.worker.databinding.ActivityLoginBinding
import corp.jasane.worker.modules.ViewModelFactory
import corp.jasane.worker.modules.home.ui.HomeActivity
import corp.jasane.worker.modules.login.data.viewmodel.LoginActivityViewModel
import corp.jasane.worker.modules.register.ui.registerActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginActivityViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val constraintLayout: ConstraintLayout = findViewById(R.id.constraintLayoutLogin)
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
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, registerActivity::class.java))
        }
    }
}