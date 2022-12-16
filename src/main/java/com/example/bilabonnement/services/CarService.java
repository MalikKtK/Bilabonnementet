package com.example.bilabonnement.services;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.Car;
import com.example.bilabonnement.models.CarStatus;
import com.example.bilabonnement.models.RentalAgreement;
import com.example.bilabonnement.repositories.CarRepository;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    CarRepository carRepository = new CarRepository();
    private final RentalAgreementService RAService = RentalAgreementService.getInstance();

    //Opretter en bil
    public Car createCar(Car car) {
        car.setStatus(CarStatus.READY_TO_BE_RENTED);
        return carRepository.create(car);
    }

    public void deleteCar(int carId) {
        carRepository.deleteById(carId);
    }

    public Car getCar(int id) {
        return carRepository.getCarByID(id);
    }


    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }


    //Får fat i en status af en bil ved hjælp af switch.
    public int getCarStatusValue(Car car) {
        return switch (car.getStatus()) {
            case READY_TO_BE_RENTED -> 1;
            case READY_FOR_DELIVERY -> 2;
            case RENTED -> 3;
            case BACK_FROM_BEING_RENTED -> 4;
            case READY_FOR_SALE -> 5;
        };
    }

    public List<Car> getValidCarsForDamageReportCreation() {
        List<RentalAgreement> rentalAgreements = RAService.getPastEndDate();
        ArrayList<Car> cars = new ArrayList<>();

        for (RentalAgreement ra : rentalAgreements) {
            if (ra.getCar().getStatus() == CarStatus.BACK_FROM_BEING_RENTED) {
                cars.add(ra.getCar());
            }
        }

        return cars;
    }

    //Får fat i den samlede pris af lejede biler ved hjælp af en for loop.
    public int getPriceOfRentedCars() {
        int totalPrice = 0;


        for (Car car : getCarsByStatus(CarStatus.RENTED)
        ) {
            totalPrice += car.getCarPrice();
        }

        return totalPrice;
    }


    public List<Car> getCarsByStatus(CarStatus status) {
        return carRepository.getCarByStatus(status);
    }

    public void updateCar(int id, String carNumber, CarStatus status, String make, String model,
                          int carPrice, int registrationFee, int co2Emission, int kilometersDriven,
                          String damage, String colour, String fuelType, int locationId) {

        Car car = carRepository.getCarByID(id);
        car.setCarNumber(carNumber);
        car.setStatus(status);
        car.setMake(make);
        car.setModel(model);
        car.setCarPrice(carPrice);
        car.setRegistrationFee(registrationFee);
        car.setCo2Emission(co2Emission);
        car.setKilometersDriven(kilometersDriven);
        car.setDamage(damage);
        car.setColour(colour);
        car.setFuelType(fuelType);
        car.setLocationId(locationId);

        carRepository.update(car);
    }
}