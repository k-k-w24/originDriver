package com.transportcompany.api.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "deliveries")
data class Delivery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false, unique = true)
    val packageNumber: String,
    
    @Column(nullable = false)
    val recipientName: String,
    
    @Column(nullable = false)
    val deliveryAddress: String,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: DeliveryStatus = DeliveryStatus.PENDING,
    
    @Temporal(TemporalType.TIMESTAMP)
    val deliveryDate: Date? = null,
    
    val driverName: String? = null,
    
    val phoneNumber: String? = null,
    
    @Column(length = 1000)
    val notes: String? = null,
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    val createdAt: Date = Date(),
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    var updatedAt: Date = Date()
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = Date()
    }
}

enum class DeliveryStatus {
    PENDING,        // 未配送
    IN_TRANSIT,     // 配送中
    DELIVERED,      // 配送完了
    FAILED          // 配送失敗
}

