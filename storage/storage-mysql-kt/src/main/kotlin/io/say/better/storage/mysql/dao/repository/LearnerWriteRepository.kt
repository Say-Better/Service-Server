package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Learner
import org.springframework.data.jpa.repository.JpaRepository

interface LearnerWriteRepository : JpaRepository<Learner, Long>
