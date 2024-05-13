package com.rktuhinbd.assessmenttask.home.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.rktuhinbd.assessmenttask.R
import com.rktuhinbd.assessmenttask.databinding.ActivityMainBinding
import com.rktuhinbd.assessmenttask.home.model.ApiResponse
import com.rktuhinbd.assessmenttask.home.model.ApiResponseItem
import com.rktuhinbd.assessmenttask.home.model.VideoData
import com.rktuhinbd.assessmenttask.home.viewmodel.MyViewModel
import com.rktuhinbd.assessmenttask.utils.NetworkUtils
import com.rktuhinbd.assessmenttask.utils.ResponseHandler
import com.rktuhinbd.assessmenttask.utils.TimeUtil
import com.rktuhinbd.assessmenttask.video_player.VideoPlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myViewModel: MyViewModel by viewModels()

    private val TAG = "MainActivity"

    private var backPressedOnce = false
    private val handler = Handler(Looper.getMainLooper())
    private val delay: Long = 5 * 60 * 1000 // 5 minutes in milliseconds

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

        onBackPress()

        initClickAction()

        myViewModel.getVideos() //Api call

        dataObservers()
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    private fun startTimer() {
        handler.postDelayed(timerRunnable, delay)
    }

    private fun stopTimer() {
        handler.removeCallbacks(timerRunnable)
    }

    private val timerRunnable = Runnable { //Calls api after every 5 minutes
        myViewModel.getVideos()
        // Restart the timer
        startTimer()
    }

    private fun onBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedOnce) {
                    // Exit the app if back button pressed again within 2 seconds
                    finish()
                } else {
                    backPressedOnce = true
                    showToast("Press back again to exit")
                    // Reset backPressedOnce after 2 seconds
                    isEnabled = false
                    onBackPressedDelay()
                }
            }
        }

        // Add the callback to the back button dispatcher
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun onBackPressedDelay() {
        // Reset backPressedOnce after 2 seconds
        handler.postDelayed({ backPressedOnce = false }, 2000)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initClickAction() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
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

                        setDataFromDatabase()
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun setDataFromDatabase() {
        myViewModel.dataObserver.observe(this@MainActivity) { data ->
            if (data != null) {
                setData(data.apiData)

                if (!NetworkUtils.isInternetAvailable(this@MainActivity)) {
                    Toast.makeText(
                        this@MainActivity,
                        "Internet Connection Unavailable!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun storeDataToDB(data: ApiResponse) {
        val roomData = VideoData(date = TimeUtil.getCurrentDateTime(), apiData = data)
        myViewModel.insertData(roomData)
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