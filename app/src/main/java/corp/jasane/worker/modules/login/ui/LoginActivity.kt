package corp.jasane.worker.modules.login.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import corp.jasane.worker.R
import corp.jasane.worker.data.pref.UserModel
import corp.jasane.worker.databinding.ActivityLoginBinding
import corp.jasane.worker.modules.ViewModelFactory
import corp.jasane.worker.modules.home.ui.HomeActivity
import corp.jasane.worker.data.response.LoginResponse
import corp.jasane.worker.modules.login.data.viewmodel.LoginActivityViewModel
import corp.jasane.worker.modules.register.ui.registerActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginActivityViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog: Dialog


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
            progressDialog = Dialog(this)
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            progressDialog.setCancelable(false)
            showLoading()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            val message = loginResponse.message
                            if (message == "Login Success") {
                                hideLoading()
                                // Logika jika login berhasil
                                val user = UserModel(loginResponse.access_token)
                                viewModel.saveSession(user)
                                AlertDialog.Builder(this@LoginActivity).apply {
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
                                        this@LoginActivity,
                                        R.string.berhasil_login,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            AlertDialog.Builder(this@LoginActivity).apply {
                                setTitle(R.string.gagal_login)
                                setMessage(R.string.username_password_salah)
                                setPositiveButton(R.string.oke) { _, _ ->
                                }
                                create()
                                show()
                            }
                            Toast.makeText(this@LoginActivity, R.string.gagal_login, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle(R.string.gagal_login)
                            setMessage(R.string.username_password_salah)
                            setPositiveButton(R.string.oke) { _, _ ->
                            }
                            create()
                            show()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    AlertDialog.Builder(this@LoginActivity).apply {
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

        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, registerActivity::class.java))
        }
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }
}