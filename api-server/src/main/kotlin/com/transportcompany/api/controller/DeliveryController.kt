package com.transportcompany.api.controller

import com.transportcompany.api.dto.UpdateStatusRequest
import com.transportcompany.api.model.Delivery
import com.transportcompany.api.model.DeliveryStatus
import com.transportcompany.api.service.DeliveryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/deliveries")
@CrossOrigin(origins = ["*"])
class DeliveryController(
    private val deliveryService: DeliveryService
) {
    @GetMapping
    fun getAllDeliveries(): ResponseEntity<List<Delivery>> {
        val deliveries = deliveryService.getAllDeliveries()
        return ResponseEntity.ok(deliveries)
    }
    
    @GetMapping("/{id}")
    fun getDeliveryById(@PathVariable id: Long): ResponseEntity<Delivery> {
        val delivery = deliveryService.getDeliveryById(id)
        return if (delivery != null) {
            ResponseEntity.ok(delivery)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PutMapping("/{id}/status")
    fun updateDeliveryStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateStatusRequest
    ): ResponseEntity<Delivery> {
        val delivery = deliveryService.updateDeliveryStatus(id, request.status)
        return if (delivery != null) {
            ResponseEntity.ok(delivery)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/search")
    fun searchDeliveries(@RequestParam query: String): ResponseEntity<List<Delivery>> {
        val deliveries = deliveryService.searchDeliveries(query)
        return ResponseEntity.ok(deliveries)
    }
}

