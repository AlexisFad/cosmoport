package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipServiceImpl;
import com.space.service.Specs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class Ships {
    @Autowired
    private ShipServiceImpl service;

    @GetMapping("/ships")
    public ResponseEntity<List<Ship>> getShips(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String planet,
            @RequestParam(required = false) String shipType,
            @RequestParam(required = false) Long after, @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean isUsed,
            @RequestParam(required = false) Double minSpeed, @RequestParam(required = false) Double maxSpeed,
            @RequestParam(required = false) Integer minCrewSize, @RequestParam(required = false) Integer maxCrewSize,
            @RequestParam(required = false) Double minRating, @RequestParam(required = false) Double maxRating,
            @RequestParam(defaultValue = "ID") ShipOrder order,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()).ascending());
        return ResponseEntity.ok(
                service.getAll(
                        Specs.mainSpec(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating), pageable)
                        .get()
                        .collect(Collectors.toList()));
    }

    @GetMapping(value = "/ships/count")
    public ResponseEntity<Integer> getCount(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String planet,
            @RequestParam(required = false) String shipType,
            @RequestParam(required = false) Long after, @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean isUsed,
            @RequestParam(required = false) Double minSpeed, @RequestParam(required = false) Double maxSpeed,
            @RequestParam(required = false) Integer minCrewSize, @RequestParam(required = false) Integer maxCrewSize,
            @RequestParam(required = false) Double minRating, @RequestParam(required = false) Double maxRating
    ) {

        return ResponseEntity.ok(
                service.getAllShips(Specs.mainSpec(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating))
                        .size());
    }

    @GetMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping(value = "/ships")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        return service.createShip(ship);
    }

    @DeleteMapping(value = "/ships/{id}")
    public ResponseEntity deleteById(@PathVariable String id) {
        return service.deleteById(id);
    }
    @PostMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> updateById(@RequestBody Ship values,
                                           @PathVariable String id) {
        return service.updateById(id, values);
    }
}
