package corp.jasane.worker.modules.home.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import corp.jasane.worker.R
import corp.jasane.worker.databinding.ActivityHomeBinding
import corp.jasane.worker.modules.ViewModelFactory
import corp.jasane.worker.modules.home.data.viewModel.HomeActivityViewModel
import corp.jasane.worker.modules.homeFragment.ui.HomeFragment
import corp.jasane.worker.modules.maps.ui.MapsFragment
import corp.jasane.worker.modules.offers.ui.OffersFragment
import corp.jasane.worker.modules.profile.ui.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomeActivityViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        showLoading()
//
//        viewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            } else {
////                getData()
//                hideLoading()
//            }
//        }

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val offersFragment = OffersFragment()
        val mapsFragment = MapsFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeId -> setCurrentFragment(homeFragment)
                R.id.jobId -> setCurrentFragment(homeFragment)
                R.id.offerId -> setCurrentFragment(profileFragment)
                R.id.profileId -> setCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        if (!supportFragmentManager.isStateSaved) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
                commitAllowingStateLoss()
            }
        }
    }
}