package com.example.notesversion_1.screens.tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.notesversion_1.R
import com.example.notesversion_1.databinding.TabsFragmentBinding

class TabsFragment : Fragment(R.layout.tabs_fragment)
{
    private lateinit var binding : TabsFragmentBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TabsFragmentBinding.bind(view)
        val navigationView = binding.tabsBottomNavigation
        val navHost = childFragmentManager.findFragmentById(R.id.tabsFragmentContainerView) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupWithNavController(navigationView,navController)

        navController.addOnDestinationChangedListener{ controller, destination, _->
            if(destination.id == R.id.noteFragment){
                binding.tabsBottomNavigation.isGone = true
            }else
            {
                binding.tabsBottomNavigation.isVisible = true
            }
        }
    }

}
