package io.say.better.storage.mysql.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    private var updatedAt: LocalDateTime? = null
}
