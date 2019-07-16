package spring.task.repos;

import org.springframework.data.repository.CrudRepository;
import spring.entities.Car;

public interface CarRepository extends CrudRepository<Car,Long> {
}
