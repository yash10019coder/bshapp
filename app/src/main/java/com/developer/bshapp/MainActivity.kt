package com.developer.bshapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.developer.bshapp.activities.AddNewsActivity
import com.developer.bshapp.activities.UserAccount
import com.developer.bshapp.activities.UserProfile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "languageSharedPreference"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.mainToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView2)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
        bottomNavigationView.setupWithNavController(navController)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
        val addNewsBtn = findViewById<FloatingActionButton>(R.id.fab)
        addNewsBtn.setOnClickListener {
            startActivity(Intent(this, AddNewsActivity::class.java))
        }
        val language = sharedPreferences.getString("language", "empty")
        if(language!="empty"){
            when (language) {
                "kannada" -> {
                    bottomNavigationView.menu.findItem(R.id.homeFragment).title =
                            this.getString(R.string.kannadaLocalNews)
                    bottomNavigationView.menu.findItem(R.id.jobsFragment).title =
                            this.getString(R.string.kannadaJobs)
                    bottomNavigationView.menu.findItem(R.id.realEstateFragment).title =
                            this.getString(R.string.kannadaRealEstate)
                    bottomNavigationView.menu.findItem(R.id.marriageFragment).title =
                            this.getString(R.string.kannadaMarriage)
                }
                "tamil" -> {
                    bottomNavigationView.menu.findItem(R.id.homeFragment).title =
                            this.getString(R.string.tamilLocalNews)
                    bottomNavigationView.menu.findItem(R.id.jobsFragment).title =
                            this.getString(R.string.tamilJobs)
                    bottomNavigationView.menu.findItem(R.id.realEstateFragment).title =
                            this.getString(R.string.tamilRealEstate)

                    bottomNavigationView.menu.findItem(R.id.marriageFragment).title =
                            this.getString(R.string.tamilMarriage)
                }
                else -> {
                    bottomNavigationView.menu.findItem(R.id.homeFragment).title =
                        this.getString(R.string.teluguLocalNews)
                    bottomNavigationView.menu.findItem(R.id.jobsFragment).title =
                        this.getString(R.string.teluguJobs)
                    bottomNavigationView.menu.findItem(R.id.realEstateFragment).title =
                        this.getString(R.string.teluguRealEstate)

                    bottomNavigationView.menu.findItem(R.id.marriageFragment).title =
                        this.getString(R.string.teluguMarriage)
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if(id==R.id.youtubelive){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCSOXe_-nKJJE8v_JTZD93Hg")))
        }
        if(id==R.id.userprofile){
            startActivity(Intent(this, UserAccount::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}