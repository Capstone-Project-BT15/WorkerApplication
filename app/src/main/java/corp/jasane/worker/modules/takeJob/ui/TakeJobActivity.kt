package corp.jasane.worker.modules.takeJob.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.gms.maps.model.LatLng
import corp.jasane.worker.R
import corp.jasane.worker.modules.ViewModelFactory
import corp.jasane.worker.modules.detailJob.data.viewModel.DetailJobViewModel
import corp.jasane.worker.modules.detailJob.ui.DetailJobActivity
import corp.jasane.worker.modules.takeJob.data.viewModel.TakeJobViewModel
import corp.jasane.worker.databinding.ActivityTakeJobBinding
import corp.jasane.worker.modules.home.ui.HomeActivity
import java.io.IOException
import java.util.Locale
import kotlin.properties.Delegates

class TakeJobActivity : AppCompatActivity() {

    private lateinit var detailAddressTextView: TextView
    private lateinit var binding: ActivityTakeJobBinding
    private lateinit var locationString: String
    private lateinit var viewModel: TakeJobViewModel
    private lateinit var progressDialog: Dialog
    private var addressId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[TakeJobViewModel::class.java]

        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val id = intent.getIntExtra(DetailJobActivity.EXTRA_ID, 0)
        viewModel.init(id)

        showLoading()

        detailAddressTextView = findViewById(R.id.detail_address)

        locationString = intent.getStringExtra("location") ?: ""

        viewModel._workDetail.observe(this) { getWorkDetailResponse ->
            if (getWorkDetailResponse != null) {
                binding.apply {
                    itemWork.text = getWorkDetailResponse.title
                    jobSection.text = getWorkDetailResponse.typeOfWork
                    lowPrice.text = getWorkDetailResponse.minBudget
                    highPrice.text = getWorkDetailResponse.maxBudget

                    Glide.with(this@TakeJobActivity)
                        .load(getWorkDetailResponse.image)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(tvItemPhoto)

                }
                hideLoading()
            } else {
                Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel._addressDetail.observe(this) { getAddressDetailResponse ->
            if (getAddressDetailResponse != null) {
                binding.apply {
                    detailAddressTextView.text = getAddressDetailResponse.address
                    titleName.text = getAddressDetailResponse.fullName
                    phoneNumber.text = getAddressDetailResponse.telephone
                    addressId = getAddressDetailResponse.id
                }
                hideLoading()
            } else {
                Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show()
            }
        }


        val startIndex = locationString.indexOf("(")
        val endIndex = locationString.indexOf(")")
        if (startIndex != -1 && endIndex != -1) {
            val latLngString = locationString.substring(startIndex + 1, endIndex)
            val latLngArray = latLngString.split(",")
            if (latLngArray.size == 2) {
                val latitude = latLngArray[0].toDouble()
                val longitude = latLngArray[1].toDouble()
                val position = LatLng(latitude, longitude)

                position?.let {
                    updateAddressText(it)
                }
            }
        }
        setupAction()
    }

    @SuppressLint("SetTextI18n")
    private fun updateAddressText(position: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1)
            val city = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown"
            detailAddressTextView.text = getString(R.string.pin_point, city)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setupAction() {
        binding.button.setOnClickListener {
            showLoading()
            val offerString = binding.offerEditText.text.toString()
            val offer = offerString.filter { it.isDigit() }.toIntOrNull()?.toString() ?: ""
            val experience = binding.editDescription.text.toString()
            val workId = intent.getIntExtra(DetailJobActivity.EXTRA_ID, 0)
            Log.d("offerJobviewmodel", "$workId, $addressId, $offer, $experience")

            viewModel.offerJob(workId, addressId, offer, experience)
            Log.d("offerJobviewmodel", "$workId, $addressId, $offer, $experience")
        }
        viewModel.offerJobSuccess.observe(this) { success ->
            hideLoading()
            if (success) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                Toast.makeText(this, "Offer job Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Offer job failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}