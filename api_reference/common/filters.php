<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
$school_id = $user['school_id'];
$type = $_GET['type'] ?? '';

switch ($type) {
    case 'classes':
        $query = "SELECT class_id, class_name FROM class WHERE school_id = '$school_id'";
        $result = $db->query($query);
        $data = [];
        if ($result) {
            while ($row = $result->fetch_assoc()) {
                $data[] = $row;
            }
        }
        json_success($data, "Classes fetched successfully");
        break;

    case 'sections':
        $class_id = $_GET['class_id'] ?? '';
        if (empty($class_id)) json_error("Missing class_id", 400);
        
        $query = "SELECT section_id, section_name FROM section_sem WHERE school_id = '$school_id' AND class_id = '$class_id'";
        $result = $db->query($query);
        $data = [];
        if ($result) {
            while ($row = $result->fetch_assoc()) {
                $data[] = $row;
            }
        }
        json_success($data, "Sections fetched successfully");
        break;

    case 'subjects':
        $class_id = $_GET['class_id'] ?? '';
        $section_id = $_GET['section_id'] ?? '';
        if (empty($class_id) || empty($section_id)) json_error("Missing class_id or section_id", 400);
        
        $query = "SELECT subject_id, subject_name FROM subject WHERE school_id = '$school_id' AND class_id = '$class_id' AND section_id = '$section_id'";
        $result = $db->query($query);
        $data = [];
        if ($result) {
            while ($row = $result->fetch_assoc()) {
                $data[] = $row;
            }
        }
        json_success($data, "Subjects fetched successfully");
        break;

    default:
        json_error("Invalid type. Supported types: classes, sections, subjects", 400);
}
