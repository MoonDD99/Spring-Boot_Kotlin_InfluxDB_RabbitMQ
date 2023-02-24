package com.example.kotlin_influxdb.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "mysqlTransactionManager",
    basePackages = ["com.example.kotlin_influxdb.repository"]
)
class MySQLDataSourceConfig(
    private val env: Environment
) {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource-mysql")
    fun mysqlDataSource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean("mysqlEntityManagerFactory")
    @Primary
    fun mysqlEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean{
        val properties = HashMap<String, Any?>()
        properties["hibernate.hbm2ddl.auto"] = env.getProperty("spring.jpa.hibernate.ddl-auto")
        return builder
            .dataSource(mysqlDataSource())
            .packages("com.example.kotlin_influxdb.entity.mysql")
            .properties(properties)
            .persistenceUnit("mysql")
            .build()
    }

    @Primary
    @Bean
    fun transactionManager(builder: EntityManagerFactoryBuilder): JpaTransactionManager {
        return JpaTransactionManager(mysqlEntityManagerFactory(builder).`object`!!)
    }
}