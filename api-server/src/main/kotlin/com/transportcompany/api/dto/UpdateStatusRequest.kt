package com.transportcompany.api.dto

import com.transportcompany.api.model.DeliveryStatus

data class UpdateStatusRequest(
    val status: DeliveryStatus
)

