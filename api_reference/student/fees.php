<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    json_error("Method not allowed", 405);
}

$user = authenticate();
require_role($user, [4]);

$a_id      = $user['user_id'];
$school_id = $user['school_id'];

$stmt = $db->prepare("SELECT student_id, class_id, section_id, session, transport, stay FROM students WHERE a_id = ? AND school_id = ? ORDER BY student_id DESC LIMIT 1");
$stmt->bind_param("ss", $a_id, $school_id);
$stmt->execute();
$student = $stmt->get_result()->fetch_assoc();
if (!$student) json_error("Student not found", 404);

$student_id = $student['student_id'];

// Get payment history
$stmt2 = $db->prepare("SELECT fc.receipt_no, fc.paid_amount, fc.payment_date, fc.payment_mode, fc.session
    FROM fee_collection fc
    WHERE fc.student_id = ? AND fc.school_id = ?
    ORDER BY fc.payment_date DESC");
$stmt2->bind_param("ss", $student_id, $school_id);
$stmt2->execute();
$result = $stmt2->get_result();

$payments = [];
$total_paid = 0;
while ($row = $result->fetch_assoc()) {
    $total_paid += (float)$row['paid_amount'];
    $payments[] = [
        'receipt_no'   => $row['receipt_no'],
        'amount'       => (float)$row['paid_amount'],
        'date'         => $row['payment_date'],
        'mode'         => $row['payment_mode'],
        'session'      => $row['session']
    ];
}

json_success([
    'total_paid' => $total_paid,
    'payments'   => $payments
]);
