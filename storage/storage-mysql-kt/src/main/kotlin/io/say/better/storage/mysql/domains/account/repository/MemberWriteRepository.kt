package io.say.better.storage.mysql.domains.account.repository

import io.say.better.storage.mysql.domains.account.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberWriteRepository : JpaRepository<Member, Long>
