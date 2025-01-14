package com.example.looply

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class ChFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize YouTubePlayerView
        val youTubePlayerView: YouTubePlayerView = view.findViewById(R.id.youtubech)

        // Add lifecycle observer
        lifecycle.addObserver(youTubePlayerView)

        // Add YouTubePlayer listener to play video
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                // YouTube video ID
                val videoId = "qQF4OChjP9s" // Replace with your desired video ID
                youTubePlayer.loadVideo(videoId, 0f) // Load and play video
            }
        })
    }
}