package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.SolutionSymbol
import org.springframework.data.jpa.repository.JpaRepository

interface SolutionSymbolReadRepository : JpaRepository<SolutionSymbol, Long>
