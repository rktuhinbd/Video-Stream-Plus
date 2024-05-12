package com.rktuhinbd.assessmenttask.video_player

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.rktuhinbd.assessmenttask.R
import com.rktuhinbd.assessmenttask.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var playerView: PlayerView
    private lateinit var player: ExoPlayer

    @UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializePlayer()

        val videoUri = intent.getStringExtra("videoUrl") ?: "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

        val mediaItem = MediaItem.Builder()
            .setUri(videoUri)
            .setMimeType(MimeTypes.APPLICATION_MP4)
            .build()

        val mediaSource = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(this))
            .createMediaSource(mediaItem)

        player.setMediaSource(mediaSource)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun initializePlayer() {
        playerView = binding.playerView
        player = ExoPlayer.Builder(this).build()
        playerView.player = player
    }

//    override fun onResume() {
//        super.onResume()
//        player.play()
//    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onStop() {
        super.onStop()
        player.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release() // Release the player when the activity is destroyed
    }
}