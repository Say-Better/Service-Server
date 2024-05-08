package io.say.better.storage.mysql.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Entity(name = "RESERVATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Long reservationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id", nullable = false)
    private Progress progress;

    @Column(name = "res_start_time", nullable = false)
    private LocalDateTime ResponseStartTime;

    @Column(name = "res_end_time", nullable = false)
    private LocalDateTime ResponseEndTime;

    @Column(name = "res_date", nullable = false)
    private LocalDate ResponseDate;
}
