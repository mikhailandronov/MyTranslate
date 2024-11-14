package ru.andronov.translate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.andronov.translate.app.ui.theme.MyTranslateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTranslateTheme {
                MainScreen()
            }
        }
    }

    private val destinations = listOf(
        "history", "translate", "favourites"
    )

    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            },
            modifier = Modifier.fillMaxSize(),

            ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "translate"
            ) {
                composable("history") { }
                composable("translate") { }
                composable("favourites") { }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        var selectedItem by rememberSaveable { mutableIntStateOf(1) }
        val icons = listOf(
            ImageVector.vectorResource(R.drawable.ic_history),
            ImageVector.vectorResource(R.drawable.ic_translate),
            ImageVector.vectorResource(R.drawable.ic_fav),
        )

        NavigationBar(
            content = {
                destinations.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = item.capitalize(Locale.current),
                            )
                        },
                        label = {
                            Text(
                                text = item.capitalize(Locale.current)
                            )
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item)
                        }
                    )
                }
            }
        )
    }

    @Composable
    fun RowScope.BottomNavigationItem(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        alwaysShowLabel: Boolean = true,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null
    ) {
        NavigationBarItem(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            alwaysShowLabel = alwaysShowLabel,
            icon = if (selected) selectedIcon else icon,
            label = label,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavDefaults.navSelectedItemColor(),
                unselectedIconColor = NavDefaults.navContentColor(),
                selectedTextColor = NavDefaults.navContentColor(),
                unselectedTextColor = NavDefaults.navContentColor(),
                indicatorColor = NavDefaults.navIndicatorColor()
            )
        )
    }

    object NavDefaults {
        @Composable
        fun navContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

        @Composable
        fun navSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

        @Composable
        fun navIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
    }
}
