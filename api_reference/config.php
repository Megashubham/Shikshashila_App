<?php
// Handle preflight requests
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Methods: POST, GET, DELETE, PUT, PATCH, OPTIONS');
    header('Access-Control-Allow-Headers: token, Content-Type');
    header('Access-Control-Max-Age: 1728000');
    header('Content-Length: 0');
    header('Content-Type: text/plain');
    die();
}

header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');

// Do not start sessions in the API. Mobile apps use JWT.
// if (session_status() === PHP_SESSION_NONE) { session_start(); } 

require_once __DIR__ . '/../config/config.php';
require_once __DIR__ . '/../config/basic.php';
require_once __DIR__ . '/helpers/response.php';

// JWT Configuration
define('JWT_SECRET', 'SHIKSHASHILA_SECURE_JWT_SECRET_KEY_2026_!@#');
define('JWT_ALGO', 'HS256');
define('JWT_EXPIRY', 86400); // 24 hours
