package com.example.kotlin_influxdb.entity.mysql

import jakarta.persistence.*

@Entity
@Table(schema = "springProject", name = "device")
data class Device(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var device_id: Long? = null,
    @Column(nullable = false, length = 50)
    var type: String? = null,
    @Column(nullable = false, length = 50)
    var location: String? = null
)
