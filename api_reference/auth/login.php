<?php
require_once __DIR__ . '/../middleware/auth.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    json_error("Method not allowed", 405);
}

$input = get_json_input();
$login_id = $input['login_id'] ?? '';
$password = $input['password'] ?? '';

if (empty($login_id) || empty($password)) {
    json_error("Missing login_id or password", 400);
}

// Clean input
$login_id = mysqli_real_escape_string($db, $login_id);
$password_md5 = md5($password);

// Note: Reusing the exact logic from loginaction.php
$query = "SELECT a_id, a_email, a_usertype, a_pagepermission, a_type, schools_id, a_name 
          FROM `admin` 
          WHERE (login_id='$login_id' OR a_email = '$login_id') 
          AND a_password='$password_md5' 
          AND a_status = '1'";

$result = $db->query($query);

if ($result && $result->num_rows > 0) {
    $user = $result->fetch_assoc();
    
    // Check if 2FA (OTP) is required (based on loginaction.php logic)
    // For now, returning token directly for app simplicity, or implement OTP flow.
    // If OTP is required, we would generate OTP here, send email, and return a "requires_otp" response.
    
    // Generate JWT Token
    $payload = [
        'user_id' => $user['a_id'],
        'email' => $user['a_email'],
        'role' => $user['a_type'], // 3 = Teacher, 4 = Student, etc.
        'school_id' => $user['schools_id'],
        'name' => $user['a_name']
    ];
    
    $token = generate_jwt($payload);
    
    json_success([
        'token' => $token,
        'user' => [
            'id' => $user['a_id'],
            'name' => $user['a_name'],
            'email' => $user['a_email'],
            'role' => (int)$user['a_type'],
            'school_id' => $user['schools_id']
        ]
    ], "Login successful");

} else {
    json_error("Invalid credentials", 401);
}
