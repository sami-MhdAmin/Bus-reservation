package com.bus.reservationbus.service.impl;

import com.bus.reservationbus.entities.BusRoute;
import com.bus.reservationbus.entities.BusSchedule;
import com.bus.reservationbus.models.ResrvationApiExcption;
import com.bus.reservationbus.repository.BusRouteRepository;
import com.bus.reservationbus.repository.BusScheduleRepository;
import com.bus.reservationbus.service.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusScheduleServiceImpl implements BusScheduleService {

    @Autowired
    private BusScheduleRepository busScheduleRepository;

    @Autowired
    private BusRouteRepository busRouteRepository;

    @Override
    public BusSchedule addSchedule(BusSchedule busSchedule) throws ResrvationApiExcption{
        final boolean exists = busScheduleRepository.existsByBusAndBusRouteAndDepartureTime(
                busSchedule.getBus(),
                busSchedule.getBusRoute(),
                busSchedule.getDepartureTime()
        );
        if(exists){
            throw new ResrvationApiExcption(HttpStatus.CONFLICT,"Duplicated");
        }
        return busScheduleRepository.save(busSchedule);
    }

    @Override
    public List<BusSchedule> getAllBusSchedules() {
        return busScheduleRepository.findAll();
    }

    @Override
    public List<BusSchedule> getSchedulesByRoute(String routeName) {
        final BusRoute busRoute = busRouteRepository.findByRouteName(routeName).orElseThrow(() -> new ResrvationApiExcption(HttpStatus.BAD_REQUEST, "Can't find route name"));

        return busScheduleRepository.findByBusRoute(busRoute).orElseThrow(() -> new ResrvationApiExcption(HttpStatus.BAD_REQUEST, "Can't find route name"));
    }
}
