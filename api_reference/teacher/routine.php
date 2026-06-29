<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [3]);

$teacher_id = $user['user_id'];
$school_id  = $user['school_id'];

$stmt = $db->prepare("SELECT t.day, t.start_time, t.end_time,
        sub.subject_name, c.class_name, sec.section_name
    FROM times t
    LEFT JOIN subject sub ON t.subject_id = sub.subject_id
    LEFT JOIN class c ON t.class_id = c.class_id
    LEFT JOIN section_sem sec ON t.section_id = sec.section_id
    WHERE t.school_id = ? AND t.teacher_admin_id = ?
    ORDER BY FIELD(t.day,'monday','tuesday','wednesday','thursday','friday','saturday','sunday'), t.start_time ASC");
$stmt->bind_param("ss", $school_id, $teacher_id);
$stmt->execute();
$result = $stmt->get_result();

$routine = [];
while ($row = $result->fetch_assoc()) {
    $day = $row['day'];
    if (!isset($routine[$day])) $routine[$day] = [];
    $routine[$day][] = [
        'start_time' => $row['start_time'],
        'end_time'   => $row['end_time'],
        'subject'    => $row['subject_name'],
        'class'      => $row['class_name'] . ' - ' . $row['section_name']
    ];
}

json_success(['routine' => $routine]);
