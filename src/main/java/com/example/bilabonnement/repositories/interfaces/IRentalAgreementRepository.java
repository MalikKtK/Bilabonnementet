package com.example.bilabonnement.repositories.interfaces;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.RentalAgreement;

import java.util.List;

public interface IRentalAgreementRepository extends IRepository<RentalAgreement> {

    List<RentalAgreement> getAllRentalPastEndDate();
}
