package io.say.better.storage.mysql.domain.entity

import io.say.better.storage.mysql.domain.constant.ReactionType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "RECORD")
class Record(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private var recordId: Long = 0,
    @JoinColumn(name = "review_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private val review: Review,
    @Column(name = "order_num", nullable = false, columnDefinition = "int default 0")
    private var orderNum: Int,
    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", length = 20)
    private var reactionType: ReactionType? = null,
)
