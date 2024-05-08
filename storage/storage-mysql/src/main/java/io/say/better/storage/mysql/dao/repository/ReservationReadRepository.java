package io.say.better.storage.mysql.dao.repository;

import io.say.better.storage.mysql.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationReadRepository extends JpaRepository<Reservation, Long> {
}
