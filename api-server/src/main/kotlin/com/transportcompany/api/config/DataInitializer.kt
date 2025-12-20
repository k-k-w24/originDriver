package com.transportcompany.api.config

import com.transportcompany.api.model.Delivery
import com.transportcompany.api.model.DeliveryStatus
import com.transportcompany.api.model.User
import com.transportcompany.api.model.UserRole
import com.transportcompany.api.repository.DeliveryRepository
import com.transportcompany.api.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.Date

@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val deliveryRepository: DeliveryRepository
) : CommandLineRunner {
    
    override fun run(vararg args: String?) {
        // 初期ユーザーを作成
        if (userRepository.findByUsername("admin") == null) {
            userRepository.save(
                User(
                    username = "admin",
                    password = "admin123",
                    name = "管理者",
                    role = UserRole.ADMIN
                )
            )
        }
        
        if (userRepository.findByUsername("driver1") == null) {
            userRepository.save(
                User(
                    username = "driver1",
                    password = "driver123",
                    name = "佐藤一郎",
                    role = UserRole.DRIVER
                )
            )
        }
        
        // サンプル配送データを作成
        if (deliveryRepository.count() == 0L) {
            deliveryRepository.saveAll(listOf(
                Delivery(
                    packageNumber = "PKG-2025-001",
                    recipientName = "山田太郎",
                    deliveryAddress = "東京都渋谷区1-2-3",
                    status = DeliveryStatus.PENDING,
                    deliveryDate = Date(),
                    driverName = "佐藤一郎",
                    phoneNumber = "090-1234-5678"
                ),
                Delivery(
                    packageNumber = "PKG-2025-002",
                    recipientName = "鈴木花子",
                    deliveryAddress = "東京都新宿区4-5-6",
                    status = DeliveryStatus.IN_TRANSIT,
                    deliveryDate = Date(),
                    driverName = "田中次郎",
                    phoneNumber = "090-2345-6789"
                ),
                Delivery(
                    packageNumber = "PKG-2025-003",
                    recipientName = "高橋三郎",
                    deliveryAddress = "東京都港区7-8-9",
                    status = DeliveryStatus.DELIVERED,
                    deliveryDate = Date(),
                    driverName = "佐藤一郎",
                    phoneNumber = "090-3456-7890"
                ),
                Delivery(
                    packageNumber = "PKG-2025-004",
                    recipientName = "伊藤四郎",
                    deliveryAddress = "東京都品川区10-11-12",
                    status = DeliveryStatus.FAILED,
                    deliveryDate = Date(),
                    driverName = "田中次郎",
                    phoneNumber = "090-4567-8901"
                )
            ))
        }
    }
}

