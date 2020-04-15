package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class Specs {

    public static Specification<Ship> mainSpec(String name,
                                               String planet,
                                               String shipType,
                                               Long after, Long before,
                                               Boolean isUsed,
                                               Double minSpeed, Double maxSpeed,
                                               Integer minCrewSize, Integer maxCrewSize,
                                               Double minRating, Double maxRating) {
        return findByName(name)
                .and(findByPlanet(planet))
                .and((findByShipType(shipType)))
                .and(findBetweenTwoDates(after, before))
                .and(findUsed(isUsed))
                .and(findBetweenSpeed(minSpeed, maxSpeed))
                .and(findCrew(minCrewSize, maxCrewSize))
                .and(findBetweenRating(minRating, maxRating));
    }

    public static Specification<Ship> findByName(String name) {
        if (name == null)
            name = "";
        String finalName = name;
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%" + finalName + "%");
            }
        };
    }

    public static Specification<Ship> findByPlanet(String planet) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (planet == null)
                    return null;
                return criteriaBuilder.like(root.get("planet"), "%" + planet + "%");
            }
        };
    }

    public static Specification<Ship> findByShipType(String shipType) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (shipType == null)
                    return null;
                return criteriaBuilder.equal(root.get("shipType"), ShipType.valueOf(shipType));
            }
        };
    }

    public static Specification<Ship> findBetweenTwoDates(Long after, Long before) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (after == null && before == null)
                    return null;
                else if (after == null) {
                    Date b = new Date(before);
                    return criteriaBuilder.lessThanOrEqualTo(root.<Date>get("prodDate"), b);
                } else if (before == null) {
                    Date a = new Date(after);
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), a);
                }
                Date a = new Date(after);
                Date b = new Date(before);

                return criteriaBuilder.between(root.<Date>get("prodDate"), a, b);
            }
        };
    }

    public static Specification<Ship> findCrew(Integer minCrewSize, Integer maxCrewSize) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (minCrewSize == null && maxCrewSize == null)
                    return null;
                else if (minCrewSize == null)
                    return criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize);
                else if (maxCrewSize == null)
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize);


                return criteriaBuilder.between(root.get("crewSize"), minCrewSize, maxCrewSize);
            }
        };
    }

    public static Specification<Ship> findUsed(Boolean isUsed) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (isUsed == null)
                    return null;
                return criteriaBuilder.equal(root.get("isUsed"), isUsed);
            }
        };
    }

    public static Specification<Ship> findBetweenSpeed(Double minSpeed, Double maxSpeed) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (minSpeed == null && maxSpeed == null)
                    return null;
                else if (minSpeed == null)
                    return criteriaBuilder.lessThanOrEqualTo(root.get("speed"), maxSpeed);
                else if (maxSpeed == null)
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), minSpeed);
                return criteriaBuilder.between(root.get("speed"), minSpeed, maxSpeed);
            }
        };
    }

    public static Specification<Ship> findBetweenRating(Double minRating, Double maxRating) {
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (minRating == null && maxRating == null)
                    return null;
                else if (minRating == null)
                    return criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating);
                else if (maxRating == null)
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating);
                return criteriaBuilder.between(root.get("rating"), minRating, maxRating);
            }
        };
    }


}
