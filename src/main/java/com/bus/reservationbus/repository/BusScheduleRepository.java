package com.bus.reservationbus.repository;

import com.bus.reservationbus.entities.Bus;
import com.bus.reservationbus.entities.BusRoute;
import com.bus.reservationbus.entities.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long> {

    Optional<List<BusSchedule>> findByBusRoute(BusRoute busRoute);

    Boolean existsByBusAndBusRouteAndDepartureTime(Bus bus,BusRoute busRoute,String date);
}
