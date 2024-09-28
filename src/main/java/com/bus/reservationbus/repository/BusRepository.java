package com.bus.reservationbus.repository;

import com.bus.reservationbus.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}
