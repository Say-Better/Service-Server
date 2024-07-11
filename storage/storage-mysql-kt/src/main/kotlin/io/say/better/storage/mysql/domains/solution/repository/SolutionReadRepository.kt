package io.say.better.storage.mysql.domains.solution.repository

import io.say.better.storage.mysql.domains.solution.entity.Solution
import org.springframework.data.jpa.repository.JpaRepository

interface SolutionReadRepository : JpaRepository<Solution, Long>
