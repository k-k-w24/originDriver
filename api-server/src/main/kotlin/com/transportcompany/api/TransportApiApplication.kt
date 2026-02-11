package com.transportcompany.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransportApiApplication

fun main(args: Array<String>) {
    runApplication<TransportApiApplication>(*args)
}

