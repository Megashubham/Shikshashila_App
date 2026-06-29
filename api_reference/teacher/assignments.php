<?php
require_once __DIR__ . '/../middleware/auth.php';

$user = authenticate();
require_role($user, [3]);

$teacher_id = $user['user_id'];
$school_id  = $user['school_id'];

// ─────────────────────────────────────────
// GET: Fetch assignments given by this teacher
// ─────────────────────────────────────────
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    $class_id   = $_GET['class_id']   ?? null;
    $section_id = $_GET['section_id'] ?? null;

    $base_sql = "SELECT ah.id, ah.title, ah.description, ah.due_date, ah.create_date,
        sub.subject_name, c.class_name, sec.section_name, ah.attachment
        FROM assignment_homework ah
        LEFT JOIN subject sub ON ah.subject_id = sub.subject_id
        LEFT JOIN class c ON ah.class_id = c.class_id
        LEFT JOIN section_sem sec ON ah.section_id = sec.section_id
        WHERE ah.school_id = ? AND ah.admin_id = ?";

    $params = [$school_id, $teacher_id];
    $types  = "ss";

    if ($class_id) {
        $base_sql .= " AND ah.class_id = ?";
        $params[] = $class_id;
        $types   .= "s";
    }
    if ($section_id) {
        $base_sql .= " AND ah.section_id = ?";
        $params[] = $section_id;
        $types   .= "s";
    }

    $base_sql .= " ORDER BY ah.create_date DESC LIMIT 50";

    $stmt = $db->prepare($base_sql);
    $stmt->bind_param($types, ...$params);
    $stmt->execute();
    $result = $stmt->get_result();

    $assignments = [];
    while ($row = $result->fetch_assoc()) {
        $assignments[] = [
            'id'          => $row['id'],
            'title'       => $row['title'],
            'description' => $row['description'],
            'due_date'    => $row['due_date'],
            'created_at'  => $row['create_date'],
            'subject'     => $row['subject_name'],
            'class'       => $row['class_name'] . ' - ' . $row['section_name'],
            'attachment'  => !empty($row['attachment']) ? website_name . 'uploads/' . basename($row['attachment']) : null
        ];
    }

    json_success(['assignments' => $assignments]);
}

// ─────────────────────────────────────────
// POST: Create a new assignment
// ─────────────────────────────────────────
elseif ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $input = get_json_input();

    $title       = trim($input['title']       ?? '');
    $description = trim($input['description'] ?? '');
    $due_date    = $input['due_date']          ?? null;
    $class_id    = $input['class_id']          ?? null;
    $section_id  = $input['section_id']        ?? null;
    $subject_id  = $input['subject_id']        ?? null;

    if (!$title || !$due_date || !$class_id || !$section_id || !$subject_id) {
        json_error("Missing required fields: title, due_date, class_id, section_id, subject_id", 400);
    }

    $stmt = $db->prepare("INSERT INTO assignment_homework (school_id, admin_id, class_id, section_id, subject_id, title, description, due_date, create_date)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())");
    $stmt->bind_param("ssssssss", $school_id, $teacher_id, $class_id, $section_id, $subject_id, $title, $description, $due_date);

    if ($stmt->execute()) {
        json_success(['assignment_id' => $db->insert_id], "Assignment created successfully", 201);
    } else {
        json_error("Failed to create assignment", 500);
    }
}

else {
    json_error("Method not allowed", 405);
}
