package io.say.better.storage.mysql.domain.entity

import io.say.better.storage.mysql.domain.constant.TimestampExitCode
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "TIMESTAMP")
class Timestamp(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ts_id")
        private val timestampId: Long,

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "progress_id", nullable = false)
        private val progress: Progress,

        @Enumerated(EnumType.STRING)
        @Column(name = "exit_code")
        private var exitCode: TimestampExitCode,

        @Column(name = "start_time", nullable = false)
        private var startTime: LocalDateTime,

        @Column(name = "end_time", nullable = false)
        private var endTime: LocalDateTime?
)
