package corp.jasane.worker.modules.takeJob.ui

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.maps.model.LatLng
import corp.jasane.worker.R
import java.io.IOException
import java.util.Locale

class TakeJobActivity : AppCompatActivity() {

    private lateinit var detailAddressTextView: TextView
    private lateinit var locationString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_job)

        detailAddressTextView = findViewById(R.id.detail_address)

        locationString = intent.getStringExtra("location") ?: ""


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
}