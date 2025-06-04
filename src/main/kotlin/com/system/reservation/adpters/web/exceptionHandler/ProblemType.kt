package com.system.reservation.adpters.web.exceptionHandler

enum class ProblemType(
    val path: String,
    val title: String,
) {
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    RESOURCE_ALREADY_EXISTS("/resource-already-exists", "Resource already exists"),
    BUSINESS_ERROR("/business-error", "Business rule violation"),
    UNCOMPREHENSIBLE_MESSAGE("/uncomprehensible-message", "Uncomprehensible message"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    MISSING_PARAMETER("/missing-parameter", "Missing parameter"),
    INVALID_DATA("/invalid-data", "Invalid data"),
    ACCESS_DENIED("/access-denied", "Access denied"),
    SYSTEM_ERROR("/system-error", "System error"), ;

    fun uri(): String = "http://localhost:8080${this.path}"
}
