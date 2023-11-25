package corp.jasane.worker.modules.maps.ui

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import corp.jasane.worker.R
import corp.jasane.worker.modules.takeJob.ui.TakeJobActivity
import java.io.IOException
import java.util.*

class MapsFragment : Fragment() {

    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userLocationMarker: Marker? = null
    private var googleMap: GoogleMap? = null
    private lateinit var progressDialog: Dialog

    private val callback = OnMapReadyCallback { map ->
        googleMap = map
        showUserLocation(googleMap!!)
        googleMap?.setOnMapClickListener { latLng ->
            updateMarkerPosition(latLng)
            updateAddressText(latLng)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = Dialog(requireContext())
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        textView = view.findViewById(R.id.textView)
        button = view.findViewById(R.id.button)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        button.setOnClickListener {
            val markerPosition = getCurrentMarkerPosition()
            Log.d("location User", "$markerPosition")
            updateAddressText(markerPosition)
            textView.invalidate()
            val intent = Intent(requireContext(), TakeJobActivity::class.java)
            // Pass any data you want to the TakeJobActivity using intent extras
            intent.putExtra("location", markerPosition?.toString())
            startActivity(intent)
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            googleMap?.let {
                showUserLocation(it)
            }
        }
    }

    private fun showUserLocation(googleMap: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showLoading()
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val userLatLng = LatLng(it.latitude, it.longitude)
                        if (userLocationMarker == null) {
                            userLocationMarker = googleMap.addMarker(
                                MarkerOptions()
                                    .position(userLatLng)
                                    .title("Your Location")
                                    .draggable(true)
                            )
                        } else {
                            userLocationMarker?.position = userLatLng
                            Log.d("Location User", "$userLocationMarker")
                        }
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                        updateAddressText(userLatLng)
                        hideLoading()
                    }
                }
        }
    }

    private fun getCurrentMarkerPosition(): LatLng? {
        return userLocationMarker?.position
    }

    private fun updateAddressText(position: LatLng?) {
        position?.let {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                val city = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown"
                textView.text = "PinPoint: $city"
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateMarkerPosition(latLng: LatLng) {
        showLoading()
        userLocationMarker?.position = latLng
        hideLoading()
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showUserLocation(googleMap!!)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Location permission denied. Cannot show user location.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}
