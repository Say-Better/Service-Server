package io.say.better.storage.mysql.domains.progress.entity

import io.say.better.storage.mysql.domains.progress.type.TimestampExitCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@Entity(name = "TIMESTAMPS")
class Timestamp(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ts_id")
    private val timestampId: Long,
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "progress_id", nullable = false)
    private val progress: Progress,
    @Enumerated(STRING)
    @Column(name = "exit_code")
    private var exitCode: TimestampExitCode,
    @Column(name = "start_time", nullable = false)
    private var startTime: LocalDateTime,
    @Column(name = "end_time", nullable = false)
    private var endTime: LocalDateTime?,
)
