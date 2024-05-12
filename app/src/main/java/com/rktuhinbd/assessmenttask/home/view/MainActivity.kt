package com.rktuhinbd.assessmenttask.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.rktuhinbd.assessmenttask.R
import com.rktuhinbd.assessmenttask.databinding.ActivityMainBinding
import com.rktuhinbd.assessmenttask.home.model.ApiResponse
import com.rktuhinbd.assessmenttask.home.model.ApiResponseItem
import com.rktuhinbd.assessmenttask.home.model.VideoData
import com.rktuhinbd.assessmenttask.home.viewmodel.MyViewModel
import com.rktuhinbd.assessmenttask.home.viewmodel.RoomViewModel
import com.rktuhinbd.assessmenttask.utils.ResponseHandler
import com.rktuhinbd.assessmenttask.utils.TimeUtil
import com.rktuhinbd.assessmenttask.video_player.VideoPlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var roomViewModel: RoomViewModel

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        roomViewModel = ViewModelProvider(this)[RoomViewModel::class.java]

        myViewModel.getVideos()

        dataObservers()
    }

    private fun dataObservers() {
        lifecycleScope.launch {
            myViewModel.videoDataObserver.collectLatest {
                when (it) {
                    is ResponseHandler.Loading -> {
                        //Shimmer or Loader call here
                    }

                    is ResponseHandler.Success -> {
                        if (it.data != null) {
                            storeDataToDB(it.data)
                            setData(it.data)
                        }
                        //Hide Shimmer or Loader
                    }

                    is ResponseHandler.Error -> {
                        //Hide Shimmer or Loader
                        Log.e(
                            TAG,
                            "ERROR-> ${
                                GsonBuilder().setPrettyPrinting().create().toJson(it.error?.msg)
                            }"
                        )
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun storeDataToDB(data: ApiResponse) {
        val roomData = VideoData(date = TimeUtil.getCurrentDateTime(), apiData = data)
        roomViewModel.insertData(roomData)
    }

    private fun setData(data: List<ApiResponseItem>) {
        Log.d(TAG, "Data-> ${GsonBuilder().setPrettyPrinting().create().toJson(data)}")

        val recyclerAdapter = RecyclerAdapter(data)
        binding.recyclerView.adapter = recyclerAdapter
        recyclerAdapter.onItemClick = {
            startActivity(Intent(this@MainActivity, VideoPlayerActivity::class.java).apply {
                putExtra("videoUrl", it)
            })
        }
    }
}