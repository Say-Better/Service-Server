package io.say.better.storage.mysql.domains.progress.repository

import io.say.better.storage.mysql.domains.progress.entity.Progress
import org.springframework.data.jpa.repository.JpaRepository

interface ProgressReadRepository : JpaRepository<Progress, Long>
