package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Connect
import org.springframework.data.jpa.repository.JpaRepository

interface ConnectReadRepository : JpaRepository<Connect, Long>
