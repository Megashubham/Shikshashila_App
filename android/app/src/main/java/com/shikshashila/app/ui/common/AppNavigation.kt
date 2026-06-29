package com.shikshashila.app.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shikshashila.app.ui.auth.LoginScreen
import com.shikshashila.app.ui.auth.MainViewModel

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val savedUser by mainViewModel.savedUser.collectAsState()
    
    // Determine the start destination based on whether a user is logged in
    val startDestination = if (savedUser != null) {
        when (savedUser?.role) {
            1, 2 -> Screen.AdminDashboard.route
            4 -> Screen.StudentDashboard.route
            3 -> Screen.TeacherDashboard.route
            else -> Screen.Login.route
        }
    } else {
        Screen.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { user ->
                    val destination = when (user.role) {
                        1, 2 -> Screen.AdminDashboard.route
                        4 -> Screen.StudentDashboard.route
                        3 -> Screen.TeacherDashboard.route
                        else -> Screen.Login.route
                    }
                    navController.navigate(destination) {
                        // Pop up to login and clear back stack so user can't press back to go to login
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.StudentDashboard.route) {
            com.shikshashila.app.ui.student.StudentDashboardScreen(
                onNavigateTo = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.TeacherDashboard.route) {
            com.shikshashila.app.ui.teacher.TeacherDashboardScreen(
                onNavigateTo = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.StudentRoutine.route) {
            com.shikshashila.app.ui.student.features.StudentRoutineScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.StudentResults.route) {
            com.shikshashila.app.ui.student.features.StudentResultsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.StudentHomework.route) {
            com.shikshashila.app.ui.student.features.StudentHomeworkScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.TeacherAttendance.route) {
            com.shikshashila.app.ui.teacher.features.TeacherAttendanceScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.TeacherAssignments.route) {
            com.shikshashila.app.ui.teacher.features.TeacherAssignmentsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AdminDashboard.route) {
            com.shikshashila.app.ui.admin.AdminDashboardScreen(
                onNavigateTo = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.AdminFeesReport.route) {
            com.shikshashila.app.ui.admin.features.FeeReportScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
