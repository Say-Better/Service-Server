package io.say.better.storage.mysql.domain.entity;

import io.say.better.storage.mysql.domain.constant.TimestampExitCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Entity(name = "TIMESTAMP")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ts_id")
    private Long timestampId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id", nullable = false)
    private Progress progress;

    @Enumerated(EnumType.STRING)
    @Column(name = "exit_code")
    private TimestampExitCode exitCode;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;
}
