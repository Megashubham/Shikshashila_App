<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [3]); // Teachers only

$teacher_id = $user['user_id'];
$school_id  = $user['school_id'];

$class_id   = $_GET['class_id']   ?? null;
$section_id = $_GET['section_id'] ?? null;

if (!$class_id || !$section_id) {
    json_error("Missing class_id or section_id", 400);
}

// Verify teacher is assigned to this class-section
$stmt_check = $db->prepare("SELECT time_id FROM times WHERE school_id = ? AND teacher_admin_id = ? AND class_id = ? AND section_id = ? LIMIT 1");
$stmt_check->bind_param("ssss", $school_id, $teacher_id, $class_id, $section_id);
$stmt_check->execute();
if ($stmt_check->get_result()->num_rows === 0) {
    json_error("You are not assigned to this class-section", 403);
}

$stmt = $db->prepare("SELECT s.student_id, s.name, s.registration_no, s.mobile, s.student_photo
    FROM students s
    WHERE s.school_id = ? AND s.class_id = ? AND s.section_id = ? AND s.is_active = 1
    ORDER BY s.name ASC");
$stmt->bind_param("sss", $school_id, $class_id, $section_id);
$stmt->execute();
$result = $stmt->get_result();

$students = [];
while ($row = $result->fetch_assoc()) {
    $students[] = [
        'student_id'      => $row['student_id'],
        'name'            => $row['name'],
        'registration_no' => $row['registration_no'],
        'mobile'          => $row['mobile'],
        'photo_url'       => !empty($row['student_photo']) ? website_name . 'uploads/' . basename($row['student_photo']) : null
    ];
}

json_success(['students' => $students, 'count' => count($students)]);
