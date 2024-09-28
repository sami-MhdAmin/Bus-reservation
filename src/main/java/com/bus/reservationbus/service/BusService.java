package com.bus.reservationbus.service;

import com.bus.reservationbus.entities.Bus;

import java.util.List;

public interface BusService {
    Bus addBus(Bus bus);

    List<Bus> getAllBus();
}
