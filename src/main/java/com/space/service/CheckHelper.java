package com.space.service;

import com.space.model.Ship;

import java.util.Calendar;
import java.util.Date;

public class CheckHelper {

    public static boolean wrongName(String name) {
        if (name == null) return false;
        return name.length() > 50 || name.isEmpty();
    }

    public static boolean wrongPlanet(String planet) {
        if (planet == null) return false;
        return planet.length() > 50 || planet.isEmpty();
    }

    public static boolean wrongSpeed(Double speed) {
        if (speed == null) return false;
        return speed < 0.01 || speed > 0.99;
    }

    public static boolean wrongCrewSize(Integer crewSize) {
        if (crewSize == null) return false;
        return crewSize < 1 || crewSize > 9999;
    }

    public static boolean wrongProdDate(Date date) {
        if (date == null) return false;
        if (date.getTime() < 0)
            return true;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) < 2800 || calendar.get(Calendar.YEAR) > 3019;
    }

    public static boolean isWrongId(String id) {
        return id == null || (!id.matches("\\d+") || Long.parseLong(id) <= 0);
    }

    public static boolean wrongParameters(Ship ship) {
        return wrongName(ship.getName())
                || wrongPlanet(ship.getPlanet())
                || wrongSpeed(ship.getSpeed())
                || wrongCrewSize(ship.getCrewSize())
                || wrongProdDate(ship.getProdDate());
    }
}