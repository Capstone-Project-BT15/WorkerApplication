package corp.jasane.worker.modules.offers.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import corp.jasane.worker.R
import corp.jasane.worker.modules.offerAccepted.ui.OfferAcceptedFragment
import corp.jasane.worker.modules.offerPending.ui.OfferPendingFragment
import corp.jasane.worker.modules.offerReject.ui.OfferRejectFragment
import corp.jasane.worker.modules.offerSuccess.ui.OfferSuccessFragment

class FragmentPageAdapter(private val mCtx: Context, fragmentmanager: FragmentManager, lifecycle: Lifecycle, bundle: Bundle) :
    FragmentStateAdapter(fragmentmanager, lifecycle)  {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = bundle
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.title_pending, R.string.title_accepted, R.string.title_finish, R.string.title_reject)
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OfferPendingFragment()
            1 -> OfferAcceptedFragment()
            2 -> OfferSuccessFragment()
            3 -> OfferRejectFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }.apply { arguments = fragmentBundle }
    }

    fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLES[position])
    }
}
