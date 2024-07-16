package io.say.better.storage.mysql.domains.account.entity

import io.say.better.storage.mysql.common.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "CONNECT")
class Connect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connect_id")
    private val connectId: Long = 0,
    @JoinColumn(name = "educator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private val educator: Educator,
    @JoinColumn(name = "learner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private val learner: Learner,
) : BaseTimeEntity()
