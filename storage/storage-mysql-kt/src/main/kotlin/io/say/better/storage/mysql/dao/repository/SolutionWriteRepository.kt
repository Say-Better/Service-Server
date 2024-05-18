package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Solution
import org.springframework.data.jpa.repository.JpaRepository

interface SolutionWriteRepository : JpaRepository<Solution, Long>
