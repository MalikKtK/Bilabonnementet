package com.example.bilabonnement.services;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.RentalAgreement;
import com.example.bilabonnement.repositories.RentalAgreementsRepository;
import com.example.bilabonnement.repositories.interfaces.IRentalAgreementRepository;

import java.util.List;

public class RentalAgreementService {
    // En RentalAgreementRepository til at ikke få stackoverflow fejl. Da vi både bruger RentalAgreementService i rental controller og CarController
    private static final RentalAgreementService instance = new RentalAgreementService();

    public static RentalAgreementService getInstance() {
        return instance;
    }

    private RentalAgreementService() {
    }

    private final IRentalAgreementRepository repo = RentalAgreementsRepository.getInstance();
    private final CarService carService = new CarService();


    // Opretter en rental
    public RentalAgreement create(RentalAgreement rentalAgreement) {
        return repo.create(rentalAgreement);
    }


    public List<RentalAgreement> getAll() {
        List<RentalAgreement> list = repo.getAllCars();

        bindCarsToRentalAgreement(list);

        return list;
    }


    public List<RentalAgreement> getPastEndDate() {
        List<RentalAgreement> list = repo.getAllRentalPastEndDate();

        bindCarsToRentalAgreement(list);

        return list;
    }


    private void bindCarsToRentalAgreement(List<RentalAgreement> list) {
        // For loop der kører alle rental igennem og finder den rigtig en af car id, og derved binder dem sammen.
        for (RentalAgreement ra : list) {
            ra.setCar(carService.getCar(ra.getCarId()));
        }
    }


    public RentalAgreement get(int id) {
        RentalAgreement ra = repo.getCarByID(id);
        ra.setCar(carService.getCar(ra.getCarId()));
        return ra;
    }


    public void delete(int rentalAgreementId) {
        repo.deleteById(rentalAgreementId);
    }

    public boolean update(RentalAgreement rentalAgreement) {
        repo.update(rentalAgreement);
        return true;
    }
}
