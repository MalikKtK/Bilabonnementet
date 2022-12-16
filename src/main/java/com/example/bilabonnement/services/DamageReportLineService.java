package com.example.bilabonnement.services;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.DamageReportLine;
import com.example.bilabonnement.repositories.DamageReportLineRepository;

import java.util.List;

public class DamageReportLineService {
    DamageReportLineRepository damageReportLineRepository = new DamageReportLineRepository();

    //Opretter DamageReportLine
    public DamageReportLine createDamageReportLine(DamageReportLine damageReportLine) {
        return damageReportLineRepository.create(damageReportLine);
    }


    public void deleteDamageReportLine(int lineNumber, int damageReportId) {
        damageReportLineRepository.deleteById(lineNumber, damageReportId);
    }


    public DamageReportLine getDamageReportLine(int id, int damageReportId) {
        return damageReportLineRepository.getDamageReportLineByLineNumberDamageReportID(id, damageReportId);
    }


    public List<DamageReportLine> getAllDamageReportLinesWithReportId(int damageReportId) {
        return damageReportLineRepository.getDamageReportByID(damageReportId);
    }

    public void updateDamageReportLine(int damageReportLineId, int lineNumber, String damageNotes, int price) {
        DamageReportLine damageReportLine = damageReportLineRepository.getDamageReportLineByLineNumberDamageReportID(lineNumber, damageReportLineId);
        damageReportLine.setDamageReportId(damageReportLineId);
        damageReportLine.setLineNumber(lineNumber);
        damageReportLine.setDamageNotes(damageNotes);
        damageReportLine.setPrice(price);

        damageReportLineRepository.update(damageReportLine);
    }


}
