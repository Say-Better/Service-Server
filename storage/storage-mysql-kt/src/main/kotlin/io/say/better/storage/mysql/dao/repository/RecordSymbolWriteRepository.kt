package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.RecordSymbol
import org.springframework.data.jpa.repository.JpaRepository

interface RecordSymbolWriteRepository : JpaRepository<RecordSymbol, Long>
