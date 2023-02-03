package com.example.kotlin_influxdb.entity.mysql

import jakarta.persistence.*

@Entity
@Table(schema = "springProject", name = "user")
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var user_id: Long? = null,
        @Column(nullable = false)
        var user_name: String? = null,
        @Column(nullable = false)
        var user_password: String? = null

)
