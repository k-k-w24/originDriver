package com.transportcompany.api.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false, unique = true)
    val username: String,
    
    @Column(nullable = false)
    val password: String,
    
    @Column(nullable = false)
    val name: String,
    
    val email: String? = null,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole = UserRole.DRIVER,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    val branch: Branch? = null,
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    val createdAt: Date = Date()
)

enum class UserRole {
    ADMIN,      // 管理者
    DRIVER,     // ドライバー
    DISPATCHER  // 配送担当者
}

