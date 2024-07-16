package io.say.better.storage.mysql.config

import io.say.better.storage.mysql.DomainPackageLocation
import io.say.better.storage.mysql.domains.JpaPackegeLocation
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EntityScan(basePackageClasses = [DomainPackageLocation::class])
@EnableJpaRepositories(basePackageClasses = [JpaPackegeLocation::class])
class CoreJpaConfig
