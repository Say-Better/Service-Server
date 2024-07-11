package io.say.better.storage.mysql.domains.account.repository

import io.say.better.storage.mysql.domains.account.entity.Learner
import org.springframework.data.jpa.repository.JpaRepository

interface LearnerWriteRepository : JpaRepository<Learner, Long>
