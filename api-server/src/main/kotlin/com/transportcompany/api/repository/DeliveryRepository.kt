package com.transportcompany.api.repository

import com.transportcompany.api.model.Delivery
import com.transportcompany.api.model.DeliveryStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository : JpaRepository<Delivery, Long> {
    fun findByPackageNumber(packageNumber: String): Delivery?
    fun findByStatus(status: DeliveryStatus): List<Delivery>
    fun findByRecipientNameContainingIgnoreCase(name: String): List<Delivery>
    fun findByDeliveryAddressContainingIgnoreCase(address: String): List<Delivery>
}

