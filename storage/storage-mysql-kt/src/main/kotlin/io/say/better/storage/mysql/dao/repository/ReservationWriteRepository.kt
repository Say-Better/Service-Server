package io.say.better.storage.mysql.dao.repository

import io.say.better.storage.mysql.domain.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationWriteRepository : JpaRepository<Reservation, Long>
