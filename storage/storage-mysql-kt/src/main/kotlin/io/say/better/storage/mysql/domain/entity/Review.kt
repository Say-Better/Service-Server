package io.say.better.storage.mysql.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "REVIEW")
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    val reviewId: Long = 0,
    @JoinColumn(name = "progress_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private val progress: Progress,
) : BaseTimeEntity()
