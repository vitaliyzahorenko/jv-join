package mate.jdbc.service;

import java.util.List;
import java.util.NoSuchElementException;
import mate.jdbc.dao.CarDao;
import mate.jdbc.lib.Inject;
import mate.jdbc.lib.Service;
import mate.jdbc.model.Car;
import mate.jdbc.model.Driver;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {

        return carDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("There is no car "
                        + "by id = " + id));
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {

        if (car.getDrivers().contains(driver) == false) {
            car.getDrivers().add(driver);
        }
        try {
            carDao.pairCarDriver(car.getId(), driver.getId());
        } catch (RuntimeException e) {
            System.out.println(e.toString());
        }

    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        car.getDrivers().remove(driver);
        carDao.unpairCarDriver(car.getId(), driver.getId());
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {

        return carDao.getAllByDriver(driverId);
    }
}