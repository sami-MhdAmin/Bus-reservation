package com.bus.reservationbus.service.impl;

import com.bus.reservationbus.entities.BusRoute;
import com.bus.reservationbus.models.ResrvationApiExcption;
import com.bus.reservationbus.repository.BusRouteRepository;
import com.bus.reservationbus.service.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusRouteServiceImpl implements BusRouteService {

    @Autowired
    private BusRouteRepository busRouteRepository;

    @Override
    public BusRoute addRoute(BusRoute busRoute) {
        return busRouteRepository.save(busRoute);
    }

    @Override
    public List<BusRoute> getAllBusRoutes() {
        return busRouteRepository.findAll();
    }

    @Override
    public BusRoute getRouteByRouteName(String routeName) {
        //.orElse Because the return type is Optional
        return busRouteRepository.findByRouteName(routeName).orElseThrow( () -> new ResrvationApiExcption(HttpStatus.BAD_REQUEST,"Bus route not found"));
    }

    @Override
    public BusRoute getRouteByCityFromAndCityTo(String cityFrom, String cityTo) {
        return busRouteRepository.findByCityFromAndCityTo(cityFrom, cityTo).orElseThrow( () -> new ResrvationApiExcption(HttpStatus.BAD_REQUEST,"Bus route not found"));
    }
}
