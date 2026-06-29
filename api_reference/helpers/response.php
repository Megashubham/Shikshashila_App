<?php

function json_success($data = null, $message = "Success", $code = 200) {
    http_response_code($code);
    echo json_encode([
        'status' => 'success',
        'code' => $code,
        'message' => $message,
        'data' => $data
    ]);
    exit;
}

function json_error($message = "An error occurred", $code = 400, $data = null) {
    http_response_code($code);
    echo json_encode([
        'status' => 'error',
        'code' => $code,
        'message' => $message,
        'data' => $data
    ]);
    exit;
}

function get_json_input() {
    $json = file_get_contents('php://input');
    return json_decode($json, true) ?: [];
}
