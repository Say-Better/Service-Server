package io.say.better.storage.mysql.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "RESERVATION")
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private val reservationId: Long,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id", nullable = false)
    private val progress: Progress,
    @Column(name = "res_start_time", nullable = false)
    private val ResponseStartTime: LocalDateTime,
    @Column(name = "res_end_time", nullable = false)
    private val ResponseEndTime: LocalDateTime,
    @Column(name = "res_date", nullable = false)
    private val ResponseDate: LocalDate,
)
