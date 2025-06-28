package com.example.taskapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * MainActivity that serves as the entry point after login.
 * Hosts the NavHostFragment and BottomNavigationView for navigating between fragments.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enforce dark mode throughout the app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BottomNavigationView from layout
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        // Get the NavHostFragment that contains the navigation graph
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        // Set up BottomNavigationView to work with NavController
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNav, navController);
        } else {
            // Defensive check
            throw new IllegalStateException("NavHostFragment not found");
        }
    }
}
