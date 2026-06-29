<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [4]); // Students only

$a_id     = $user['user_id'];
$school_id = $user['school_id'];

// Get student record
$stmt = $db->prepare("SELECT student_id FROM students WHERE a_id = ? AND school_id = ? ORDER BY student_id DESC LIMIT 1");
$stmt->bind_param("ss", $a_id, $school_id);
$stmt->execute();
$student = $stmt->get_result()->fetch_assoc();
if (!$student) json_error("Student not found", 404);
$student_id = $student['student_id'];

// Optional filters: month & year (default = current)
$month = isset($_GET['month']) ? (int)$_GET['month'] : (int)date('m');
$year  = isset($_GET['year'])  ? (int)$_GET['year']  : (int)date('Y');

$stmt2 = $db->prepare("SELECT create_date, present 
    FROM attendance_new 
    WHERE student_id = ? AND MONTH(create_date) = ? AND YEAR(create_date) = ?
    ORDER BY create_date ASC");
$stmt2->bind_param("sii", $student_id, $month, $year);
$stmt2->execute();
$rows = $stmt2->get_result();

$attendance = [];
$total = 0; $present = 0; $absent = 0;
while ($row = $rows->fetch_assoc()) {
    $total++;
    if ($row['present'] == 1) $present++; else $absent++;
    $attendance[] = [
        'date'    => $row['create_date'],
        'present' => (bool)$row['present']
    ];
}

json_success([
    'month' => $month,
    'year'  => $year,
    'summary' => [
        'total'      => $total,
        'present'    => $present,
        'absent'     => $absent,
        'percentage' => $total > 0 ? round(($present / $total) * 100, 2) : 0
    ],
    'records' => $attendance
]);
