package corp.jasane.worker.modules.home.ui

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import corp.jasane.worker.R
import corp.jasane.worker.modules.detailJob.ui.detailJobActivity
import corp.jasane.worker.modules.home.data.viewModel.HomeActivityViewModel
import corp.jasane.worker.modules.homeFragment.ui.HomeFragment
import corp.jasane.worker.modules.maps.ui.MapsFragment
import corp.jasane.worker.modules.offers.ui.OffersFragment
import corp.jasane.worker.modules.profile.ui.ProfileFragment

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val offersFragment = OffersFragment()
        val mapsFragment = MapsFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeId -> setCurrentFragment(homeFragment)
                R.id.jobId -> setCurrentFragment(mapsFragment)
                R.id.offerId -> setCurrentFragment(offersFragment)
                R.id.profileId -> setCurrentFragment(profileFragment)
            }
            true //returns true value
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            )
            replace(R.id.fragment_container, fragment)
//            addToBackStack(null)
            commitNow()
        }
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }
}