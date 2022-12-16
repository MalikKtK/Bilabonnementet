package com.example.bilabonnement.models;
//Udarbejdet af Malik Kütük
public class DamageReportLine {
    private int damageReportId;
    private int lineNumber;
    private String damageNotes;
    private int price;

    public DamageReportLine(int damageReportId, int lineNumber, String damageNotes, int price) {
        this.damageReportId = damageReportId;
        this.lineNumber = lineNumber;
        this.damageNotes = damageNotes;
        this.price = price;
    }

    public DamageReportLine() {

    }

    public int getDamageReportId() {
        return damageReportId;
    }

    public void setDamageReportId(int damageReportId) {
        this.damageReportId = damageReportId;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDamageNotes() {
        return damageNotes;
    }

    public void setDamageNotes(String damageNotes) {
        this.damageNotes = damageNotes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
