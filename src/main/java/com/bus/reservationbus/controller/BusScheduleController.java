package com.bus.reservationbus.controller;

import com.bus.reservationbus.entities.BusSchedule;
import com.bus.reservationbus.models.ResponseModel;
import com.bus.reservationbus.service.BusScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class BusScheduleController {

    @Autowired
    private BusScheduleService busScheduleService;

    @PostMapping("/add")
    public ResponseModel<BusSchedule> addBusSchedule(@RequestBody BusSchedule busSchedule){
        final BusSchedule schedule = busScheduleService.addSchedule(busSchedule);
        return new ResponseModel<>(HttpStatus.OK.value(), "schedule saved", schedule);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BusSchedule>> getAllBusSchedule(){
        return ResponseEntity.ok(busScheduleService.getAllBusSchedules());
    }

    @GetMapping("/{routeName}")
    public ResponseEntity<List<BusSchedule>> getBusScheduleByRouteName(@PathVariable(name = "routeName") String routeName){
       return ResponseEntity.ok(busScheduleService.getSchedulesByRoute(routeName));
    }

}
