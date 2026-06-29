<?php
require_once __DIR__ . '/../config.php';

// Simple JWT encode/decode since we don't have composer vendor libs in api/
function base64url_encode($data) {
    return rtrim(strtr(base64_encode($data), '+/', '-_'), '=');
}

function base64url_decode($data) {
    return base64_decode(str_pad(strtr($data, '-_', '+/'), strlen($data) % 4, '=', STR_PAD_RIGHT));
}

function generate_jwt($payload) {
    $header = json_encode(['typ' => 'JWT', 'alg' => JWT_ALGO]);
    $payload['iat'] = time();
    $payload['exp'] = time() + JWT_EXPIRY;
    $payload = json_encode($payload);

    $base64UrlHeader = base64url_encode($header);
    $base64UrlPayload = base64url_encode($payload);
    $signature = hash_hmac('sha256', $base64UrlHeader . "." . $base64UrlPayload, JWT_SECRET, true);
    $base64UrlSignature = base64url_encode($signature);

    return $base64UrlHeader . "." . $base64UrlPayload . "." . $base64UrlSignature;
}

function verify_jwt($token) {
    $parts = explode('.', $token);
    if (count($parts) !== 3) return false;

    $header = $parts[0];
    $payload = $parts[1];
    $signature = $parts[2];

    $valid_signature = base64url_encode(hash_hmac('sha256', $header . "." . $payload, JWT_SECRET, true));
    
    if (!hash_equals($valid_signature, $signature)) return false;

    $payload_data = json_decode(base64url_decode($payload), true);
    
    if (isset($payload_data['exp']) && $payload_data['exp'] < time()) {
        return false; // Expired
    }
    
    return $payload_data;
}

function authenticate() {
    $headers = apache_request_headers();
    $auth_header = isset($headers['Authorization']) ? $headers['Authorization'] : (isset($headers['authorization']) ? $headers['authorization'] : '');
    
    if (empty($auth_header)) {
        json_error("Unauthorized: Missing Authorization header", 401);
    }
    
    if (!preg_match('/Bearer\s(\S+)/', $auth_header, $matches)) {
        json_error("Unauthorized: Invalid token format", 401);
    }
    
    $token = $matches[1];
    $payload = verify_jwt($token);
    
    if (!$payload) {
        json_error("Unauthorized: Invalid or expired token", 401);
    }
    
    return $payload;
}

function require_role($payload, $allowed_roles) {
    if (!in_array($payload['role'], $allowed_roles)) {
        json_error("Forbidden: Insufficient permissions", 403);
    }
}
