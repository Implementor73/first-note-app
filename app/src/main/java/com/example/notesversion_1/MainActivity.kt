package com.example.notesversion_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.notesversion_1.actions.OnActionNoteListener
import com.example.notesversion_1.databinding.ActivityMainBinding
import com.example.notesversion_1.screens.note.NoteFragment
import com.example.notesversion_1.screens.tabs.TabsFragment
import com.example.notesversion_1.navigator.Navigator
import com.example.notesversion_1.toolbar.ICustomToolbar
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var bind : ActivityMainBinding
    private var controller: NavController? = null
    private val topLevelDestination = setOf(R.id.tabsFragment)
    private var currentFragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repositories.init(applicationContext)
        bind = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(bind.toolbar)
        val navController = getRootNavController()
        onSetController(navController)
        supportFragmentManager.registerFragmentLifecycleCallbacks(listener,true)
    }

    private fun getRootNavController() : NavController{
        val navHost = supportFragmentManager.findFragmentById(bind.fragmentContainerView.id) as NavHostFragment
        return navHost.navController
    }

    private fun onSetController(navController : NavController){
        if(this.controller == navController) return
        this.controller?.removeOnDestinationChangedListener(destinationListener)
        navController.addOnDestinationChangedListener(destinationListener)
        this.controller = navController
    }
    override fun onSupportNavigateUp(): Boolean {
        notesUpdate()
        return controller?.navigateUp() ?: false || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(isStartDestination(controller?.currentDestination)){
            onBackPressedDispatcher.onBackPressed()
        }else{
            notesUpdate()
            controller?.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(listener)
    }

    private fun isStartDestination(navDestination: NavDestination?) : Boolean{
        if(navDestination == null) return false
        val graph = navDestination.parent ?: return false
        val controllerDestination = this.controller?.graph?.startDestinationId
        //return navDestination.id == graph.startDestinationId || navDestination.id == controllerDestination
        val startDestinations = topLevelDestination.plus(graph.startDestinationId)
        return startDestinations.contains(navDestination.id)
    }

    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))
    }
    private val listener = object : FragmentManager.FragmentLifecycleCallbacks(){
        override fun onFragmentCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            if(f is TabsFragment || f is NavHostFragment) return
            onSetController(f.findNavController())
            currentFragment = f
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            updateMenu(f)
        }
    }

    private fun updateMenu(fragment: Fragment){
        val menu = fragment as? ICustomToolbar
        menu?.let {
            addMenuProvider(it, fragment.viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

    override fun navigateTo(directions: NavDirections) {
        controller?.navigate(directions)
    }

    private fun notesUpdate(){
        val f = currentFragment as OnActionNoteListener
        f.updateNotes()
    }
    override fun goBack() {
        controller?.popBackStack()
    }
}

/*
    когда мы нажимаем на кнопку назад, вызвать функцию добавления
    и перейти назад
 */