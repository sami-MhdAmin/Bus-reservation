package com.bus.reservationbus.service;

import com.bus.reservationbus.entities.BusSchedule;

import java.util.List;

public interface BusScheduleService {
    BusSchedule addSchedule(BusSchedule busSchedule);

    List<BusSchedule> getAllBusSchedules();

    List<BusSchedule> getSchedulesByRoute(String routeName);

}
