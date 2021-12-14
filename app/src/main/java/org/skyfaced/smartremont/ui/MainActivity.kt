package org.skyfaced.smartremont.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.terrakok.modo.MultiScreen
import com.github.terrakok.modo.NavigationState
import com.github.terrakok.modo.android.ModoRender
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.ActivityMainBinding
import org.skyfaced.smartremont.navigation.Screens
import org.skyfaced.smartremont.util.extensions.lazySafetyNone

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)
    private fun binding(block: ActivityMainBinding.() -> Unit) = with(binding, block)

    private val modoRender by lazySafetyNone {
        object : ModoRender(this@MainActivity, R.id.fragment_container) {
            override fun invoke(state: NavigationState) {
                super.invoke(state)
                when (val stack = state.chain.lastOrNull()) {
                    is MultiScreen -> {
                        val selectedStack = stack.selectedStack
                        val lastScreen = stack.stacks[selectedStack].chain.last()
                        when (lastScreen) {
                            else -> showNavigationView()
                        }
                    }
                    is Screens.StartScreen -> hideNavigationView()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBinding()
        setupModo(savedInstanceState)
        setupContent()
    }

    private fun setupContent() = binding {
        bottomNavigationView.setOnItemReselectedListener { /* Ignore */ }
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            // If you want change tabs position don't forgot to sync order in [bottom_nav_menu.xml]
            viewModel.onTabSelected(menuItem.order)

            return@setOnItemSelectedListener true
        }
    }

    private fun setupBinding() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupModo(savedInstanceState: Bundle?) {
        viewModel.initModo(savedInstanceState, Screens.StartScreen())
    }

    private fun hideNavigationView() = binding {
        bottomNavigationView.isVisible = false
    }

    private fun showNavigationView() = binding {
        bottomNavigationView.isVisible = true
    }

    override fun onResume() {
        super.onResume()
        viewModel.setModoRender(modoRender)
    }

    override fun onPause() {
        viewModel.setModoRender(null)
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveModoState(outState)
    }

    override fun onBackPressed() = viewModel.navigateBack()
}