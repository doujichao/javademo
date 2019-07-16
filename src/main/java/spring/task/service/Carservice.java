package spring.task.service;

import spring.entities.Car;

import java.util.List;

public interface Carservice {

    List<Car> findAll();

    Car save(Car car);

    void updateCarAgeJob();

    boolean isDone();
}
