package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberWriteRepository : JpaRepository<Member, Long>
