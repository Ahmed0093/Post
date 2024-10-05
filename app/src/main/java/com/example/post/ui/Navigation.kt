package com.example.post.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.post.ui.screens.DetailScreen
import com.example.post.ui.screens.HomeListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.List.name) {
        composable(Screen.List.name) {
            HomeListScreen({
                navController.navigate(Screen.Details.name + "/" + it.toString())
            })
        }
        composable(Screen.Details.name + "/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            DetailScreen(navController, postId)
        }
    }
}


enum class Screen {
    List,
    Details,
}
