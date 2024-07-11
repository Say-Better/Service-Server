package io.say.better.storage.mysql.domains.review.repository

import io.say.better.storage.mysql.domains.review.entity.RecordSymbol
import org.springframework.data.jpa.repository.JpaRepository

interface RecordSymbolWriteRepository : JpaRepository<RecordSymbol, Long>
