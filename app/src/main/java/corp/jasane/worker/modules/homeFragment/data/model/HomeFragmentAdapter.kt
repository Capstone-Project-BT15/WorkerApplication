package corp.jasane.worker.modules.homeFragment.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Worker
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import corp.jasane.worker.R
import corp.jasane.worker.data.response.WorkDetail
import corp.jasane.worker.databinding.ItemWorkerBinding

class HomeFragmentAdapter : RecyclerView.Adapter<HomeFragmentAdapter.DataViewHolder>() {

    private val list = ArrayList<WorkDetail>()
    private var onItemClickCallBack: OnItemClickCallBack? = null


    fun setOnItemClickCallback (onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(workData: ArrayList<WorkDetail>) {
        list.clear()
        list.addAll(workData)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(val binding: ItemWorkerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(work: WorkDetail) {
            binding.root.setOnClickListener {
                onItemClickCallBack?.onItemClicked(work)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(work.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(tvItemPhoto)
                typeWork.text = work.typeOfWork
                jobDistance.text = work.distanceToUser
                jobDetail.text = work.title
                lowPrice.text = work.minBudget
                highPrice.text = work.maxBudget
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemWorkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallBack{
        fun onItemClicked(data: WorkDetail)
    }
}