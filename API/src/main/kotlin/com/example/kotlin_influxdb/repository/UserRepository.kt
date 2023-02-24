package com.example.kotlin_influxdb.repository

import com.example.kotlin_influxdb.entity.mysql.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
}