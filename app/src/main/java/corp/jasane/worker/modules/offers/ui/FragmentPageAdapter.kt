package corp.jasane.worker.modules.offers.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import corp.jasane.worker.modules.offerAccepted.ui.OfferAcceptedFragment
import corp.jasane.worker.modules.offerPending.ui.OfferPendingFragment
import corp.jasane.worker.modules.offerReject.ui.OfferRejectFragment
import corp.jasane.worker.modules.offerSuccess.ui.OfferSuccessFragment

class FragmentPageAdapter(fragmentmanager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentmanager, lifecycle) {
        override fun getItemCount(): Int {
            return 4
        }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OfferPendingFragment()
            1 -> OfferAcceptedFragment()
            2 -> OfferSuccessFragment()
            else -> OfferRejectFragment()
        }
    }
}