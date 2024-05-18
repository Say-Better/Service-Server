package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Educator
import org.springframework.data.jpa.repository.JpaRepository

interface EducatorWriteRepository : JpaRepository<Educator, Long?>
