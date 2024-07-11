package io.say.better.storage.mysql.domains.account.repository

import io.say.better.storage.mysql.domains.account.entity.Connect
import org.springframework.data.jpa.repository.JpaRepository

interface ConnectWriteRepository : JpaRepository<Connect, Long>
