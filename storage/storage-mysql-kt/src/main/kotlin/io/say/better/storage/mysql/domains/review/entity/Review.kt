package io.say.better.storage.mysql.domains.review.entity

import io.say.better.storage.mysql.common.model.BaseTimeEntity
import io.say.better.storage.mysql.domains.progress.entity.Progress
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "REVIEW")
class Review(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_id")
    val reviewId: Long = 0,
    @JoinColumn(name = "progress_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private val progress: Progress,
) : BaseTimeEntity()
