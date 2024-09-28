package com.bus.reservationbus.service;

import com.bus.reservationbus.entities.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation addReservation(Reservation reservation);

    List<Reservation> getAllReservations();

    List<Reservation> getReservationByScheduleAndDepartureDate(Long scheduleId,String departureDate);

    List<Reservation> getReservationByMobile(String mobile);
}
