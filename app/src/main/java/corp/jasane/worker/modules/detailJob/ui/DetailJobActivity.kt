package corp.jasane.worker.modules.detailJob.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import corp.jasane.worker.R
import corp.jasane.worker.data.UserRepository
import corp.jasane.worker.databinding.ActivityDetailJobBinding
import corp.jasane.worker.modules.ViewModelFactory
import corp.jasane.worker.modules.detailJob.data.viewModel.DetailJobViewModel
import corp.jasane.worker.modules.login.ui.LoginActivity
import corp.jasane.worker.modules.takeJob.ui.TakeJobActivity
import kotlinx.coroutines.launch

class DetailJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailJobBinding
    private lateinit var viewModel: DetailJobViewModel
    private lateinit var progressDialog: Dialog
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))
            .get(DetailJobViewModel::class.java)

        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        viewModel.init(id)


//        val bundle = Bundle()


//        viewModel.getWorkDetail().observe(this, {
//            if (it != null){
//                binding.apply {
//                    tvItemName.text = it.name
//                    tvItemUsername.text = it.login
//                    tvItemFollowers.text = "${it.followers} Followers"
//                    tvItemFollowing.text = "${it.following} Following"
//                    Glide.with(this@DetailUserActivity)
//                        .load(it.avatar_url)
//                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .centerCrop()
//                        .into(ivProfile)
//                }
//                showLoading(false)
//            }
//        })


        showLoading()
        viewModel.workDetail.observe(this) { getWorkResponse ->
            if (getWorkResponse?.data != null) {
                binding.apply {
                    titleJob.text = getWorkResponse.data.title
                    startDate.text = getWorkResponse.data.startDate
                    minBudget.text = getWorkResponse.data.minBudget
                    maxBudget.text = getWorkResponse.data.maxBudget
                    typeWork.text = getWorkResponse.data.typeOfWork
                    jobDistance.text = getWorkResponse.data.distanceToUser
                    description.text = getWorkResponse.data.description

                    Glide.with(this@DetailJobActivity)
                        .load(getWorkResponse.data.image)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imageWork)
                }
                hideLoading()
            } else {
                // Handle null response
            }

            setOnClickListener()
        }

//        viewModel.workDetail.observe(viewLifecycleOwner) { workDetails ->
//            adapter.setList(workDetails)
//        }
    }

    private fun setOnClickListener(){
        binding.takeJob.setOnClickListener {
            val data = viewModel.workDetail.value?.data
            if (data != null) {
                val intent = Intent(this, TakeJobActivity::class.java)
                intent.putExtra(EXTRA_ID, data.id)
                startActivity(intent)
            }
        }
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    companion object{
        const val EXTRA_ID = "extra_id"
    }
}