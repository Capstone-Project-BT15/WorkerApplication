package corp.jasane.worker.modules.homeFragment.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import corp.jasane.worker.R
import corp.jasane.worker.data.response.WorkDetail
import corp.jasane.worker.databinding.ActivityHomeBinding
import corp.jasane.worker.databinding.FragmentHomeBinding
import corp.jasane.worker.modules.ViewModelFactory
import corp.jasane.worker.modules.biodata.ui.BiodataActivity
import corp.jasane.worker.modules.detailJob.ui.DetailJobActivity
import corp.jasane.worker.modules.home.data.viewModel.HomeActivityViewModel
import corp.jasane.worker.modules.homeFragment.data.viewModel.HomeFragmentViewModel
import corp.jasane.worker.modules.login.ui.LoginActivity
import corp.jasane.worker.modules.verificationBiodata.verificationTwo.VerificationTwoActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeFragmentViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var progressDialog: Dialog
    private lateinit var adapter: HomeFragmentAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressDialog = Dialog(requireContext())
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        showLoading()

        val spanCount = 2
        val layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerListWorker.layoutManager = layoutManager

        adapter = HomeFragmentAdapter()
        binding.recyclerListWorker.adapter = adapter

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                viewModel.workDetails.observe(viewLifecycleOwner) { workDetails ->
                    adapter.setList(ArrayList(workDetails))
                }
                hideLoading()
            }
        }

        adapter.setOnItemClickCallback(object : HomeFragmentAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: WorkDetail) {
                Log.d("HomeFragment", "Clicked item with ID: ${data.id}")
                Intent(requireContext(), DetailJobActivity::class.java).also {
                    it.putExtra(DetailJobActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })
        return root
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}