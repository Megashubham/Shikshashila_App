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

$stmt2 = $db->prepare("SELECT n.note_id, sub.subject_name, n.title, n.description, n.attachment, n.create_date
    FROM notes n
    LEFT JOIN subject sub ON n.subject_id = sub.subject_id
    WHERE n.school_id = ? AND n.class_id = ? AND n.section_id = ?
    ORDER BY n.create_date DESC
    LIMIT 50");
$stmt2->bind_param("sss", $school_id, $class_id, $section_id);
$stmt2->execute();
$result = $stmt2->get_result();

$notes = [];
while ($row = $result->fetch_assoc()) {
    $notes[] = [
        'id'          => $row['note_id'],
        'subject'     => $row['subject_name'],
        'title'       => $row['title'],
        'description' => $row['description'],
        'created_at'  => $row['create_date'],
        'attachment'  => !empty($row['attachment']) ? website_name . 'uploads/' . basename($row['attachment']) : null
    ];
}

json_success(['notes' => $notes]);
