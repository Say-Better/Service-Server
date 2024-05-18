package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Progress
import org.springframework.data.jpa.repository.JpaRepository

interface ProgressReadRepository : JpaRepository<Progress, Long>
