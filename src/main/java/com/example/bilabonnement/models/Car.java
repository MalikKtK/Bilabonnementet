package com.example.bilabonnement.models;
//Udarbejdet af Malik Kütük
public class Car {

    private int id;
    private String carNumber;
    private CarStatus status;
    private int CarStatusId;
    private String make;
    private String model;
    private int carPrice;
    private int registrationFee;
    private int co2Emission;
    private int kilometersDriven;
    private String damage;
    private String colour;
    private String fuelType;
    private int locationId;

    public Car(int id, String carNumber, CarStatus status, String make, String model,
               int carPrice, int registrationFee, int co2Emission, int kilometersDriven,
               String damage, String colour, String fuelType, int locationId) {
        this.id = id;
        this.carNumber = carNumber;
        this.status = status;
        this.make = make;
        this.model = model;
        this.carPrice = carPrice;
        this.registrationFee = registrationFee;
        this.co2Emission = co2Emission;
        this.kilometersDriven = kilometersDriven;
        this.damage = damage;
        this.colour = colour;
        this.fuelType = fuelType;
        this.locationId = locationId;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(int carPrice) {
        this.carPrice = carPrice;
    }

    public int getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(int registrationFee) {
        this.registrationFee = registrationFee;
    }

    public int getCo2Emission() {
        return co2Emission;
    }

    public void setCo2Emission(int co2Emission) {
        this.co2Emission = co2Emission;
    }

    public int getKilometersDriven() {
        return kilometersDriven;
    }

    public void setKilometersDriven(int kilometersDriven) {
        this.kilometersDriven = kilometersDriven;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getCarStatusId() {
        return CarStatusId;
    }

    public void setCarStatusId(int carStatusId) {
        CarStatusId = carStatusId;
    }
}
