package io.say.better.storage.mysql.domain.entity

import jakarta.persistence.*

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
        private val learner: Learner
) : BaseTimeEntity()
