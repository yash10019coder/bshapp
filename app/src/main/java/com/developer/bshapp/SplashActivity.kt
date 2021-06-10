package com.developer.bshapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SplashActivity : AppCompatActivity() {

    var videoView: VideoView? = null
    var mediaControls: MediaController? = null

    private val sharedPrefFile = "languageSharedPreference"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//    requestWindowFeature(1)
//    window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//    window.statusBarColor = Color.TRANSPARENT
        window.statusBarColor = ContextCompat.getColor(this, R.color.background_color)
        setContentView(R.layout.activity_splash)

    videoView = findViewById<View>(R.id.viewvideo) as VideoView
        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.videoView)
        }
        // set the media controller for video view
        videoView!!.setMediaController(mediaControls)

        // set the absolute path of the video file which is going to be played
        videoView!!.setVideoURI(Uri.parse("android.resource://"
                + packageName + "/" + R.raw.splashvideo))

        videoView!!.requestFocus()
        videoView!!.start()

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        val language = sharedPreferences.getString("language", "empty")

        if (language!="empty"){


            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000)
        }
        else{
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, SelectorActivity::class.java))
                finish()
            }, 1000)
        }
    }
}