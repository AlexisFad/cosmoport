package com.space.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table
public class Ship {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String planet;
    @Column
    @Enumerated(EnumType.STRING)
    private ShipType shipType;
    @Column
    private Date prodDate;
    @Column
    private Boolean isUsed;
    @Column
    private Double speed;
    @Column
    private Integer crewSize;
    @Column
    private Double rating;

    public Ship() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlanet() {
        return planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public Double getSpeed() {
        return speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating() {
        this.rating = rate();
    }

    private double rate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);

        double v = speed;
        double k = isUsed ? 0.5 : 1;
        int y0 = 3019;
        int y1 = calendar.get(Calendar.YEAR);

        BigDecimal result = new BigDecimal(80 * v * k / (y0 - y1 + 1));
        return rating = result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
