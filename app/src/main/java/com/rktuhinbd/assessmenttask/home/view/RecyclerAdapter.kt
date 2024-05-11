package com.rktuhinbd.assessmenttask.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rktuhinbd.assessmenttask.R
import com.rktuhinbd.assessmenttask.databinding.RvItemVideoBinding
import com.rktuhinbd.assessmenttask.home.model.ApiResponseItem
import com.rktuhinbd.assessmenttask.utils.ExtensionFunc.loadImage

class RecyclerAdapter(private var dataList: List<ApiResponseItem>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    class ViewHolder(val binding: RvItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = RvItemVideoBinding.inflate(inflater)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val data = dataList[position]

        val binding = viewHolder.binding

        binding.tvVideoTitle.text = data.title
        binding.tvVideoDescription.text = data.description
        binding.ivVideoThumb.loadImage(data.thumbnailUrl, R.drawable.ic_play)

        viewHolder.binding.body.setOnClickListener {
            onItemClick?.invoke(data.videoUrl)
        }
    }

    override fun getItemCount() = dataList.size

}
