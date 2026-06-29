<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [4]);

$a_id      = $user['user_id'];
$school_id = $user['school_id'];

$stmt = $db->prepare("SELECT s.*, c.class_name, sec.section_name 
    FROM students s
    LEFT JOIN class c ON s.class_id = c.class_id
    LEFT JOIN section_sem sec ON s.section_id = sec.section_id
    WHERE s.a_id = ? AND s.school_id = ?
    ORDER BY s.student_id DESC LIMIT 1");
$stmt->bind_param("ss", $a_id, $school_id);
$stmt->execute();
$student = $stmt->get_result()->fetch_assoc();
if (!$student) json_error("Student not found", 404);

// Build clean photo URL
$photo_url = null;
if (!empty($student['student_photo'])) {
    $photo_url = website_name . 'uploads/' . basename($student['student_photo']);
}

json_success([
    'student_id'      => $student['student_id'],
    'name'            => $student['name'],
    'registration_no' => $student['registration_no'],
    'class'           => $student['class_name'],
    'section'         => $student['section_name'],
    'date_of_birth'   => $student['dob'] ?? null,
    'father_name'     => $student['father_name'] ?? null,
    'mother_name'     => $student['mother_name'] ?? null,
    'mobile'          => $student['mobile'] ?? null,
    'email'           => $student['email'] ?? null,
    'address'         => $student['address'] ?? null,
    'photo_url'       => $photo_url,
    'session'         => $student['session'] ?? null
]);
