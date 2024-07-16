package io.say.better.storage.mysql.domains.account.repository

import io.say.better.storage.mysql.domains.account.entity.Educator
import org.springframework.data.jpa.repository.JpaRepository

interface EducatorWriteRepository : JpaRepository<Educator, Long?>
