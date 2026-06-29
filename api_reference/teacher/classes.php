<?php
// Returns list of classes & sections assigned to this teacher
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [3]);

$teacher_id = $user['user_id'];
$school_id  = $user['school_id'];

$stmt = $db->prepare("SELECT DISTINCT t.class_id, t.section_id, c.class_name, sec.section_name
    FROM times t
    LEFT JOIN class c ON t.class_id = c.class_id
    LEFT JOIN section_sem sec ON t.section_id = sec.section_id
    WHERE t.school_id = ? AND t.teacher_admin_id = ?
    ORDER BY c.class_name, sec.section_name");
$stmt->bind_param("ss", $school_id, $teacher_id);
$stmt->execute();
$result = $stmt->get_result();

$classes = [];
while ($row = $result->fetch_assoc()) {
    $classes[] = [
        'class_id'     => $row['class_id'],
        'section_id'   => $row['section_id'],
        'class_name'   => $row['class_name'],
        'section_name' => $row['section_name'],
        'display'      => $row['class_name'] . ' - ' . $row['section_name']
    ];
}

json_success(['classes' => $classes]);
