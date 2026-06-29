package com.shikshashila.app.ui.common

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object StudentDashboard : Screen("student_dashboard")
    object TeacherDashboard : Screen("teacher_dashboard")
    object StudentRoutine : Screen("student_routine")
    object StudentResults : Screen("student_results")
    object StudentHomework : Screen("student_homework")
    object TeacherAttendance : Screen("teacher_attendance")
    object TeacherAssignments : Screen("teacher_assignments")
    object AdminDashboard : Screen("admin_dashboard")
    object AdminFeesReport : Screen("admin_fees_report")
}
