package corp.jasane.worker.modules.register.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import corp.jasane.worker.R
import corp.jasane.worker.data.pref.UserModel
import corp.jasane.worker.data.response.LoginResponse
import corp.jasane.worker.data.response.RegisterResponse
import corp.jasane.worker.databinding.ActivityLoginBinding
import corp.jasane.worker.databinding.ActivityRegisterBinding
import corp.jasane.worker.modules.ViewModelFactory
import corp.jasane.worker.modules.home.ui.HomeActivity
import corp.jasane.worker.modules.login.data.viewmodel.LoginActivityViewModel
import corp.jasane.worker.modules.login.ui.LoginActivity
import corp.jasane.worker.modules.register.data.viewModel.RegisterActivityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterActivityViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: Dialog
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)

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
            showLoading()
            val fullName = binding.nameEditText.text.toString()
            val telephone = binding.phoneEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val passwordConfirmation = binding.confirmPasswordEditText.text.toString()
            viewModel.register(fullName, telephone, email, password, passwordConfirmation)
                .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        val registerResponse = response.body()
                        if (registerResponse != null) {
                                hideLoading()

                                val user = UserModel(registerResponse.access_token)
                                viewModel.saveSession(user)
                                AlertDialog.Builder(this@RegisterActivity).apply {
                                    setTitle(R.string.yeah)
                                    setMessage(R.string.berhasil_login)
                                    val intent = Intent(context, HomeActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                    create()
                                    show()
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        R.string.berhasil_login,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            AlertDialog.Builder(this@RegisterActivity).apply {
                                setTitle(R.string.gagal_login)
                                setMessage(R.string.username_password_salah)
                                setPositiveButton(R.string.oke) { _, _ ->
                                }
                                create()
                                show()
                            }
                            Toast.makeText(this@RegisterActivity, R.string.gagal_login, Toast.LENGTH_SHORT).show()
                        }
                    }
                    Log.d("registerViewModel","${viewModel.register(email, fullName, telephone, password, passwordConfirmation)}")
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    AlertDialog.Builder(this@RegisterActivity).apply {
                        setTitle(R.string.gagal_login)
                        setMessage(R.string.gagal_memuat_data)
                        setPositiveButton(R.string.oke) { _, _ ->
                        }
                        create()
                        show()
                    }
                }
            })
        }

        binding.textLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }
}