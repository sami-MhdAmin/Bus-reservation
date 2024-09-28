package com.bus.reservationbus.repository;

import com.bus.reservationbus.entities.BusSchedule;
import com.bus.reservationbus.entities.Customer;
import com.bus.reservationbus.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findByCustomer(Customer customer);
    Optional<List<Reservation>> findByBusScheduleAndDepartureDate(BusSchedule busSchedule,String departureDate);
}
