<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [4]);

$a_id      = $user['user_id'];
$school_id = $user['school_id'];

// Get student record
$stmt = $db->prepare("SELECT student_id, class_id, section_id FROM students WHERE a_id = ? AND school_id = ? ORDER BY student_id DESC LIMIT 1");
$stmt->bind_param("ss", $a_id, $school_id);
$stmt->execute();
$student = $stmt->get_result()->fetch_assoc();
if (!$student) json_error("Student not found", 404);

$student_id = $student['student_id'];
$class_id   = $student['class_id'];
$section_id = $student['section_id'];

// Get exam types for this school/class
$stmt2 = $db->prepare("SELECT et_id, exam_type_name FROM exam_type WHERE school_id = ? AND class_id = ?");
$stmt2->bind_param("ss", $school_id, $class_id);
$stmt2->execute();
$exam_types_result = $stmt2->get_result();

$results = [];
while ($et = $exam_types_result->fetch_assoc()) {
    // Get marks for each exam type
    $stmt3 = $db->prepare("SELECT sub.subject_name, om.marks_obtained, om.total_marks, om.grade
        FROM offline_exam_marks om
        LEFT JOIN subject sub ON om.subject_id = sub.subject_id
        WHERE om.student_id = ? AND om.exam_type_id = ?");
    $stmt3->bind_param("ss", $student_id, $et['et_id']);
    $stmt3->execute();
    $marks_result = $stmt3->get_result();

    $marks = [];
    while ($mark = $marks_result->fetch_assoc()) {
        $marks[] = $mark;
    }

    if (!empty($marks)) {
        $results[] = [
            'exam_type_id'   => $et['et_id'],
            'exam_type_name' => $et['exam_type_name'],
            'subjects'       => $marks
        ];
    }
}

json_success(['results' => $results]);
