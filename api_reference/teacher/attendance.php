<?php
require_once __DIR__ . '/../middleware/auth.php';

$user = authenticate();
require_role($user, [3]); // Teachers only

$teacher_id = $user['user_id'];
$school_id  = $user['school_id'];

// ─────────────────────────────────────────
// GET: Fetch attendance for a class on a date
// ─────────────────────────────────────────
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $class_id   = $_GET['class_id']   ?? null;
    $section_id = $_GET['section_id'] ?? null;
    $date       = $_GET['date']       ?? date('Y-m-d');

    if (!$class_id || !$section_id) json_error("Missing class_id or section_id", 400);

    // Validate date format
    if (!preg_match('/^\d{4}-\d{2}-\d{2}$/', $date)) json_error("Invalid date format. Use YYYY-MM-DD", 400);

    $stmt = $db->prepare("SELECT s.student_id, s.name, s.registration_no,
            a.present, a.attendance_id
        FROM students s
        LEFT JOIN attendance_new a ON a.student_id = s.student_id AND DATE(a.create_date) = ?
        WHERE s.school_id = ? AND s.class_id = ? AND s.section_id = ? AND s.is_active = 1
        ORDER BY s.name ASC");
    $stmt->bind_param("ssss", $date, $school_id, $class_id, $section_id);
    $stmt->execute();
    $result = $stmt->get_result();

    $students = [];
    while ($row = $result->fetch_assoc()) {
        $students[] = [
            'student_id'      => $row['student_id'],
            'name'            => $row['name'],
            'registration_no' => $row['registration_no'],
            'status'          => is_null($row['present']) ? 'not_marked' : ($row['present'] ? 'present' : 'absent'),
            'attendance_id'   => $row['attendance_id']
        ];
    }

    json_success(['date' => $date, 'students' => $students]);
}

// ─────────────────────────────────────────
// POST: Submit attendance for a class
// ─────────────────────────────────────────
elseif ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $input = get_json_input();

    $class_id   = $input['class_id']   ?? null;
    $section_id = $input['section_id'] ?? null;
    $date       = $input['date']       ?? date('Y-m-d');
    $records    = $input['records']    ?? []; // [{ student_id: 1, present: true }, ...]

    if (!$class_id || !$section_id) json_error("Missing class_id or section_id", 400);
    if (empty($records))            json_error("No attendance records provided", 400);
    if (!preg_match('/^\d{4}-\d{2}-\d{2}$/', $date)) json_error("Invalid date format", 400);

    // Verify teacher is assigned to this class
    $stmt_check = $db->prepare("SELECT time_id FROM times WHERE school_id = ? AND teacher_admin_id = ? AND class_id = ? AND section_id = ? LIMIT 1");
    $stmt_check->bind_param("ssss", $school_id, $teacher_id, $class_id, $section_id);
    $stmt_check->execute();
    if ($stmt_check->get_result()->num_rows === 0) {
        json_error("You are not assigned to this class-section", 403);
    }

    $saved = 0;
    $stmt_insert = $db->prepare("INSERT INTO attendance_new (student_id, class_id, section_id, school_id, present, create_date, teacher_id)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE present = VALUES(present), teacher_id = VALUES(teacher_id)");

    foreach ($records as $record) {
        $sid     = $record['student_id'] ?? null;
        $present = isset($record['present']) ? ($record['present'] ? 1 : 0) : 0;
        if (!$sid) continue;

        $stmt_insert->bind_param("sssssss", $sid, $class_id, $section_id, $school_id, $present, $date, $teacher_id);
        $stmt_insert->execute();
        $saved++;
    }

    json_success(['saved' => $saved], "Attendance saved successfully");
}

else {
    json_error("Method not allowed", 405);
}
