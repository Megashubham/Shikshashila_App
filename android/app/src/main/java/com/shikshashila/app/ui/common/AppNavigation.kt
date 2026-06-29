package com.shikshashila.app.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    val startDestination = if (savedUser != null) {
        when (savedUser?.role) {
            1, 2 -> Screen.AdminDashboard.route
            4    -> Screen.StudentDashboard.route
            3    -> Screen.TeacherDashboard.route
            else -> Screen.Login.route
        }
    } else {
        Screen.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {

        // ── Auth ─────────────────────────────────────────────────────────────
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { user ->
                    val dest = when (user.role) {
                        1, 2 -> Screen.AdminDashboard.route
                        4    -> Screen.StudentDashboard.route
                        3    -> Screen.TeacherDashboard.route
                        else -> Screen.Login.route
                    }
                    navController.navigate(dest) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Student ──────────────────────────────────────────────────────────
        composable(Screen.StudentDashboard.route) {
            com.shikshashila.app.ui.student.StudentDashboardScreen(
                onNavigateTo = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
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
        composable(Screen.StudentAttendance.route) {
            com.shikshashila.app.ui.student.features.StudentAttendanceScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StudentFees.route) {
            com.shikshashila.app.ui.student.features.StudentFeesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StudentNotes.route) {
            com.shikshashila.app.ui.student.features.StudentNotesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Teacher ──────────────────────────────────────────────────────────
        composable(Screen.TeacherDashboard.route) {
            com.shikshashila.app.ui.teacher.TeacherDashboardScreen(
                onNavigateTo = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
                }
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
        composable(Screen.TeacherRoutine.route) {
            com.shikshashila.app.ui.teacher.features.TeacherRoutineScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.TeacherStudents.route) {
            com.shikshashila.app.ui.teacher.features.TeacherStudentsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Admin ────────────────────────────────────────────────────────────
        composable(Screen.AdminDashboard.route) {
            com.shikshashila.app.ui.admin.AdminDashboardScreen(
                onNavigateTo = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) { popUpTo(0) { inclusive = true } }
                }
            )
        }
        composable(Screen.AdminFeesReport.route) {
            com.shikshashila.app.ui.admin.features.FeeReportScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminStudents.route) {
            com.shikshashila.app.ui.admin.features.AdminStudentsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminStaff.route) {
            com.shikshashila.app.ui.admin.features.AdminStaffScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminAttendance.route) {
            com.shikshashila.app.ui.admin.features.AdminAttendanceScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminClasses.route) {
            com.shikshashila.app.ui.admin.features.AdminClassesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
