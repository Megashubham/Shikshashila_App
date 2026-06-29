<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [4]);

$a_id      = $user['user_id'];
$school_id = $user['school_id'];

$stmt = $db->prepare("SELECT class_id, section_id FROM students WHERE a_id = ? AND school_id = ? ORDER BY student_id DESC LIMIT 1");
$stmt->bind_param("ss", $a_id, $school_id);
$stmt->execute();
$student = $stmt->get_result()->fetch_assoc();
if (!$student) json_error("Student not found", 404);

$class_id   = $student['class_id'];
$section_id = $student['section_id'];

$stmt2 = $db->prepare("SELECT t.day, t.start_time, t.end_time, sub.subject_name,
        CONCAT(a.a_name) AS teacher_name
    FROM times t
    LEFT JOIN subject sub ON t.subject_id = sub.subject_id
    LEFT JOIN admin a ON t.teacher_admin_id = a.a_id
    WHERE t.school_id = ? AND t.class_id = ? AND t.section_id = ?
    ORDER BY FIELD(t.day,'monday','tuesday','wednesday','thursday','friday','saturday','sunday'), t.start_time ASC");
$stmt2->bind_param("sss", $school_id, $class_id, $section_id);
$stmt2->execute();
$result = $stmt2->get_result();

// Group by day
$routine = [];
while ($row = $result->fetch_assoc()) {
    $day = $row['day'];
    if (!isset($routine[$day])) $routine[$day] = [];
    $routine[$day][] = [
        'start_time'   => $row['start_time'],
        'end_time'     => $row['end_time'],
        'subject'      => $row['subject_name'],
        'teacher'      => $row['teacher_name']
    ];
}

json_success(['routine' => $routine]);
