package com.transportcompany.app.models

import java.util.Date

data class Delivery(
    val id: String,
    val packageNumber: String,
    val recipientName: String,
    val deliveryAddress: String,
    val status: DeliveryStatus,
    val deliveryDate: Date?,
    val driverName: String?,
    val phoneNumber: String?,
    val notes: String? = null
)

enum class DeliveryStatus {
    PENDING,        // 未配送
    IN_TRANSIT,     // 配送中
    DELIVERED,      // 配送完了
    FAILED          // 配送失敗
}

