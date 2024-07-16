package io.say.better.storage.mysql.domains.solution.repository

import io.say.better.storage.mysql.domains.solution.entity.SolutionSymbol
import org.springframework.data.jpa.repository.JpaRepository

interface SolutionSymbolWriteRepository : JpaRepository<SolutionSymbol, Long>
