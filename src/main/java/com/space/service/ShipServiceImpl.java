package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl {
    private ShipRepository shipRepository;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }


    public ResponseEntity<Ship> createShip(Ship ship) {
        if (ship.getUsed() == null)
            ship.setUsed(false);

        if (ship.getName() == null || ship.getPlanet() == null
                || ship.getSpeed() == null || ship.getCrewSize() == null
                || ship.getProdDate() == null) {
            return ResponseEntity.status(400).build();
        }

        if (CheckHelper.wrongParameters(ship))
            return ResponseEntity.status(400).build();

        ship.setRating();
        shipRepository.saveAndFlush(ship);
        return ResponseEntity.ok(ship);
    }

    public Page<Ship> getAll(Specification<Ship> specification, Pageable page) {
        return shipRepository.findAll(specification, page);
    }


    public List<Ship> getAllShips(Specification<Ship> specification) {
        return shipRepository.findAll(specification);
    }

    public Optional<Ship> findById(Long id) {
        return shipRepository.findById(id);
    }

    public ResponseEntity<Ship> findById(String id) {
        if (CheckHelper.isWrongId(id))
            return ResponseEntity.status(400).build();
        if (!findById(Long.parseLong(id)).isPresent())
            return ResponseEntity.status(404).build();

        Ship ship = findById(Long.parseLong(id)).get();
        return ResponseEntity.ok(ship);
    }

    public ResponseEntity deleteById(String id) {
        if (CheckHelper.isWrongId(id))
            return ResponseEntity.status(400).build();
        if (!findById(Long.parseLong(id)).isPresent())
            return ResponseEntity.status(404).build();
        shipRepository.deleteById(Long.parseLong(id));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Ship> updateById(String id, Ship values) {
        if (CheckHelper.isWrongId(id) || CheckHelper.wrongParameters(values))
            return ResponseEntity.status(400).build();
        if (!findById(Long.parseLong(id)).isPresent())
            return ResponseEntity.status(404).build();

        Ship ship = findById(Long.parseLong(id)).get();

        if (values.getName() != null)
            ship.setName(values.getName());
        if (values.getPlanet() != null)
            ship.setPlanet(values.getPlanet());
        if (values.getShipType() != null)
            ship.setShipType(values.getShipType());
        if (values.getProdDate() != null)
            ship.setProdDate(values.getProdDate());
        if (values.getUsed() != null)
            ship.setUsed(values.getUsed());
        if (values.getUsed() != null)
            ship.setSpeed(values.getSpeed());
        if (values.getCrewSize() != null)
            ship.setCrewSize(values.getCrewSize());

        ship.setRating();
        shipRepository.saveAndFlush(ship);
        return ResponseEntity.ok(ship);
    }
}
