package com.example.kotlin_influxdb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinInfluxDbApplication

fun main(args: Array<String>) {
	runApplication<KotlinInfluxDbApplication>(*args)
}
