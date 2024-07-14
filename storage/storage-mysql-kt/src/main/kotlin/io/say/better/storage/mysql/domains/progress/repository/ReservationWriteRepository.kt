package io.say.better.storage.mysql.domains.progress.repository

import io.say.better.storage.mysql.domains.progress.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationWriteRepository : JpaRepository<Reservation, Long>
