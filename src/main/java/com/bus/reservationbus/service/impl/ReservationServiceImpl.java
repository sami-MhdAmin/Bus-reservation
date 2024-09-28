package com.bus.reservationbus.service.impl;

import com.bus.reservationbus.entities.BusSchedule;
import com.bus.reservationbus.entities.Customer;
import com.bus.reservationbus.entities.Reservation;
import com.bus.reservationbus.models.ResrvationApiExcption;
import com.bus.reservationbus.repository.BusScheduleRepository;
import com.bus.reservationbus.repository.CustomerRepository;
import com.bus.reservationbus.repository.ReservationRepository;
import com.bus.reservationbus.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BusScheduleRepository busScheduleRepository;

    @Override
    public Reservation addReservation(Reservation reservation) {
        final Customer customer;
        final boolean doesCustomerExist = customerRepository
                .existsByMobileOrEmail(
                reservation.getCustomer().getMobile(),
                reservation.getCustomer().getEmail()
        );
        if(doesCustomerExist){
            customer = customerRepository.findByMobileOrEmail(
                    reservation.getCustomer().getMobile(),
                    reservation.getCustomer().getEmail()
            ).orElseThrow();
        }else{
                customer = customerRepository.save(reservation.getCustomer());
            }
            reservation.setCustomer(customer);
            return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationByScheduleAndDepartureDate(Long scheduleId, String departureDate) {
        final BusSchedule schedule = busScheduleRepository.findById(scheduleId).orElseThrow(() -> new ResrvationApiExcption(HttpStatus.BAD_REQUEST, "Schedule not found"));
        return reservationRepository.findByBusScheduleAndDepartureDate(schedule,departureDate).orElseThrow(() -> new ResrvationApiExcption(HttpStatus.BAD_REQUEST, "Reservation not found"));
    }

    @Override
    public List<Reservation> getReservationByMobile(String mobile) {
        final Customer customer = customerRepository.findByMobile(mobile).orElseThrow(() -> new ResrvationApiExcption(HttpStatus.BAD_REQUEST, "no record find"));

        return reservationRepository.findByCustomer(customer).orElseThrow(() -> new ResrvationApiExcption(HttpStatus.BAD_REQUEST, "Schedule not found"));
    }
}
