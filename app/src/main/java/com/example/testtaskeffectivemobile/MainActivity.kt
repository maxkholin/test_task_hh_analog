package com.example.testtaskeffectivemobile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initAndSetupNavigation()



    }

    private fun initAndSetupNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.setOnApplyWindowInsetsListener { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }

//        navController = try {
//            findNavController(R.id.nav_host_fragment)
//        } catch (e: Exception) {
//            Log.e("NavigationSetup", "NavController initialization failed: ${e.message}")
//            throw e
//        }
//        // Настройка NavController с BottomNavigationView
//        bottomNavigationView.setupWithNavController(navController)
    }


}