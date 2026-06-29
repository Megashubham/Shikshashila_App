package com.shikshashila.app.ui.common

sealed class Screen(val route: String) {
    // Auth
    object Login : Screen("login")

    // Student Dashboard + Features
    object StudentDashboard  : Screen("student_dashboard")
    object StudentRoutine    : Screen("student_routine")
    object StudentResults    : Screen("student_results")
    object StudentHomework   : Screen("student_homework")
    object StudentAttendance : Screen("student_attendance")
    object StudentFees       : Screen("student_fees")
    object StudentNotes      : Screen("student_notes")

    // Teacher Dashboard + Features
    object TeacherDashboard   : Screen("teacher_dashboard")
    object TeacherAttendance  : Screen("teacher_attendance")
    object TeacherAssignments : Screen("teacher_assignments")
    object TeacherRoutine     : Screen("teacher_routine")
    object TeacherStudents    : Screen("teacher_students")

    // Admin Dashboard + Features
    object AdminDashboard   : Screen("admin_dashboard")
    object AdminFeesReport  : Screen("admin_fees_report")
    object AdminStudents    : Screen("admin_students")
    object AdminStaff       : Screen("admin_staff")
    object AdminAttendance  : Screen("admin_attendance")
    object AdminClasses     : Screen("admin_classes")
}
