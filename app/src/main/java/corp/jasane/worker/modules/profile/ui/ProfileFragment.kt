package corp.jasane.worker.modules.profile.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import corp.jasane.worker.databinding.FragmentProfileBinding
import corp.jasane.worker.data.pref.UserPreference
import corp.jasane.worker.databinding.FragmentHomeBinding
import corp.jasane.worker.modules.ViewModelFactory
import corp.jasane.worker.modules.detailJob.ui.DetailJobActivity
import corp.jasane.worker.modules.home.ui.HomeActivity
import corp.jasane.worker.modules.homeFragment.ui.HomeFragment
import corp.jasane.worker.modules.login.ui.LoginActivity
import corp.jasane.worker.modules.profile.data.viewModel.ProfileFragmentViewModel

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileFragmentViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAction()
        viewModel.logoutSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }
        return root
    }

    private fun setupAction() {
        binding.cardViewLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}