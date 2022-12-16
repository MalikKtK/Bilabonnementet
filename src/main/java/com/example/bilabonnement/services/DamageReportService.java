package com.example.bilabonnement.services;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.DamageReport;
import com.example.bilabonnement.repositories.DamageReportRepository;
import com.example.bilabonnement.repositories.UserRepository;

import java.util.List;

public class DamageReportService {
    UserRepository userRepository = new UserRepository();
    DamageReportRepository damageReportRepository = new DamageReportRepository();

    //Opretter DamageReport
    public DamageReport createDamageReport(DamageReport damageReport) {
        return damageReportRepository.create(damageReport);
    }

    public void deleteDamageReport(int id) {
        damageReportRepository.deleteById(id);
    }


    public DamageReport getDamageReport(int id) {
        return damageReportRepository.getCarByID(id);
    }


    public List<DamageReport> getAllDamageReports() {
        List<DamageReport> list = damageReportRepository.getAllCars();

        for (DamageReport dr : list) {
            dr.setTechnicianName(
                    userRepository.
                            getCarByID(dr.getTechnicianId()).
                            getUsername());
        }

        return list;
    }
    public void updateDamageReport(int id, String notes, int technicianId, int carId) {
        DamageReport damageReport = damageReportRepository.getCarByID(id);

        damageReport.setCarId(carId);
        damageReport.setTechnicianId(technicianId);
        damageReport.setNotes(notes);

        damageReportRepository.update(damageReport);
    }

}
