package com.bus.reservationbus.controller;

import com.bus.reservationbus.entities.Bus;
import com.bus.reservationbus.models.ResponseModel;
import com.bus.reservationbus.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/bus")

public class BusController {

    @Autowired
    private BusService busService;

    @PostMapping("/add")
    public ResponseModel<Bus> addBus(@RequestBody Bus bus){
        final Bus savedBus = busService.addBus(bus);
         return new ResponseModel<>(HttpStatus.OK.value(), "Bus saved", savedBus);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bus>> getAllBuses(){
       return ResponseEntity.ok(busService.getAllBus());
    }
}
