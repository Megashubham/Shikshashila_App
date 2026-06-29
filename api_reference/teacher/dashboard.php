<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [3]); // Only teachers (role 3)

$school_id = $user['school_id'];
$teacher_id = $user['user_id'];

// Get teacher details
$teacher_ana = $db->query("SELECT * FROM staf WHERE school_id = '$school_id' AND admin_id = '$teacher_id'");
if (!$teacher_ana || $teacher_ana->num_rows === 0) {
    json_error("Teacher profile not found", 404);
}
$teacher_ana_value = $teacher_ana->fetch_assoc();

// Count assigned classes
$as_class = $db->query("SELECT * FROM times WHERE school_id = '$school_id' AND teacher_admin_id = '$teacher_id' GROUP BY (class_id AND section_id)");
$assigned_classes_count = $as_class ? $as_class->num_rows : 0;

// Count assigned subjects
$sub_count_data = $db->query("SELECT * FROM times WHERE school_id = '$school_id' AND teacher_admin_id = '$teacher_id' GROUP BY subject_id");
$assigned_subjects_count = $sub_count_data ? $sub_count_data->num_rows : 0;

// Get today's classes
$today_day = strtolower(date('l')); // e.g., 'monday'
$today_classes_sql = "SELECT t.*, c.class_name, s.section_name, sub.subject_name 
                      FROM times t
                      LEFT JOIN class c ON t.class_id = c.class_id
                      LEFT JOIN section_sem s ON t.section_id = s.section_id
                      LEFT JOIN subject sub ON t.subject_id = sub.subject_id
                      WHERE t.school_id = '$school_id' 
                      AND t.teacher_admin_id = '$teacher_id'
                      AND t.day = '$today_day'
                      ORDER BY t.start_time ASC";
$today_classes_result = $db->query($today_classes_sql);
$today_schedule = [];
if ($today_classes_result) {
    while ($row = $today_classes_result->fetch_assoc()) {
        $today_schedule[] = [
            'class' => $row['class_name'] . ' - ' . $row['section_name'],
            'subject' => $row['subject_name'],
            'start_time' => $row['start_time'],
            'end_time' => $row['end_time']
        ];
    }
}

$dashboard_data = [
    'teacher_info' => [
        'name' => $teacher_ana_value['staf_name'],
        'designation' => $teacher_ana_value['designation'] ?? ''
    ],
    'stats' => [
        'assigned_classes' => $assigned_classes_count,
        'assigned_subjects' => $assigned_subjects_count
    ],
    'today_schedule' => $today_schedule
];

json_success($dashboard_data, "Dashboard data fetched successfully");
