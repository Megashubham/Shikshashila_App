<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [4]); // Only students (role 4)

$a_id = $user['user_id'];
$schools_id = $user['school_id'];

// Get student_id from a_id
$udata = $db->query("SELECT * FROM `students` WHERE a_id = '$a_id' AND school_id = '$schools_id' ORDER BY student_id DESC");
if (!$udata || $udata->num_rows === 0) {
    json_error("Student profile not found", 404);
}
$uvalue = $udata->fetch_assoc();
$student_id = $uvalue['student_id'];

// Fetch current month attendance
$sql = "SELECT COUNT(*) AS total_attendance, 
       SUM(present = 1) AS days_present, 
       SUM(present = 0) AS days_absent
FROM attendance_new 
WHERE student_id = '$student_id' 
AND MONTH(create_date) = MONTH(CURRENT_DATE()) 
AND YEAR(create_date) = YEAR(CURRENT_DATE())";

$result = $db->query($sql);
$attendance_data = $result->fetch_assoc();

// Prepare dashboard response data
$dashboard_data = [
    'student_info' => [
        'student_id' => $student_id,
        'name' => $uvalue['name'],
        'registration_no' => $uvalue['registration_no']
    ],
    'attendance' => [
        'current_month' => [
            'total' => (int)($attendance_data['total_attendance'] ?? 0),
            'present' => (int)($attendance_data['days_present'] ?? 0),
            'absent' => (int)($attendance_data['days_absent'] ?? 0),
            'percentage' => ($attendance_data['total_attendance'] > 0) ? 
                round((($attendance_data['days_present'] / $attendance_data['total_attendance']) * 100), 2) : 0
        ]
    ]
];

json_success($dashboard_data, "Dashboard data fetched successfully");
