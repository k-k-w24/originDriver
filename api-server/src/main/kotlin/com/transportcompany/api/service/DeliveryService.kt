package com.transportcompany.api.service

import com.transportcompany.api.model.Delivery
import com.transportcompany.api.model.DeliveryStatus
import com.transportcompany.api.repository.DeliveryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeliveryService(
    private val deliveryRepository: DeliveryRepository
) {
    fun getAllDeliveries(): List<Delivery> {
        return deliveryRepository.findAll()
    }
    
    fun getDeliveryById(id: Long): Delivery? {
        return deliveryRepository.findById(id).orElse(null)
    }
    
    fun updateDeliveryStatus(id: Long, status: DeliveryStatus): Delivery? {
        val delivery = deliveryRepository.findById(id).orElse(null) ?: return null
        delivery.status = status
        return deliveryRepository.save(delivery)
    }
    
    fun searchDeliveries(query: String): List<Delivery> {
        val results = mutableListOf<Delivery>()
        
        // 荷物番号で検索
        deliveryRepository.findByPackageNumber(query)?.let { results.add(it) }
        
        // 受取人名で検索
        results.addAll(deliveryRepository.findByRecipientNameContainingIgnoreCase(query))
        
        // 住所で検索
        results.addAll(deliveryRepository.findByDeliveryAddressContainingIgnoreCase(query))
        
        // 重複を除去
        return results.distinctBy { it.id }
    }
}

