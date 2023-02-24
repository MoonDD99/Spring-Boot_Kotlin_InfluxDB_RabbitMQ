package com.example.kotlin_influxdb.repository

import com.example.kotlin_influxdb.entity.mysql.Device
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRepository : JpaRepository<Device, String> {


}