package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.*;
import com.example.bilabonnement.services.CarService;
import com.example.bilabonnement.services.RentalAgreementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
//Udarbejdet af Malik Kütük
@Controller
public class RentalAgreementController {

    private final CarService CAR_SERVICE = new CarService();
    private final RentalAgreementService RENTAL_AGREEMENT_SERVICE = RentalAgreementService.getInstance();

    @GetMapping("/opret-lejekontrakt")
    public String getCreateRentalAgreement(HttpSession session, Model model) {

        if (session.getAttribute("userRole") != UserRole.DATA_REGISTRATION) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());

        model.addAttribute("cars", CAR_SERVICE.getCarsByStatus(CarStatus.READY_TO_BE_RENTED));
        model.addAttribute("rentalAgreement", new RentalAgreement());
        return "dataregistering/rentalagreementcreate";
    }


    @PostMapping("/opret-lejekontrakt")
    public String createRentalAgreement(HttpSession session, Model model,
                                        @ModelAttribute RentalAgreement rentalAgreement) {

        if (session.getAttribute("userRole") != UserRole.DATA_REGISTRATION) {
            return "redirect:/logout";
        }

        if (rentalAgreement.getCarId() == -1 ||
                rentalAgreement.getEndDate().isBefore(rentalAgreement.getStartDate())) {

            model.addAttribute("cars", CAR_SERVICE.getCarsByStatus(CarStatus.READY_TO_BE_RENTED));
            model.addAttribute("rentalAgreement", new RentalAgreement());

            if (rentalAgreement.getCarId() == -1) {
                model.addAttribute("noCar", true);
            }
            if (rentalAgreement.getEndDate().isBefore(rentalAgreement.getStartDate())) {
                model.addAttribute("dateBeforeStart", true);
            }

            return "dataregistering/rentalagreementcreate";
        }

        rentalAgreement = RENTAL_AGREEMENT_SERVICE.create(rentalAgreement);

        return "redirect:/lejekontrakt/" + rentalAgreement.getId();
    }


    @GetMapping("/lejekontrakter")
    public String viewAllRentalAgreements(HttpSession session, Model model) {
        if (session.getAttribute("userRole") != UserRole.DATA_REGISTRATION) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());
        model.addAttribute("listOfRentalAgreement", RENTAL_AGREEMENT_SERVICE.getAll());

        return "dataregistering/rentalagreements";
    }


    @GetMapping("/lejekontrakt/{id}")
    public String getEditRentalAgreement(@PathVariable() int id, HttpSession session, Model model) {
        if (session.getAttribute("userRole") != UserRole.DATA_REGISTRATION) {
            return "redirect:/logout";
        }
        StatusSet[] types = {
                new StatusSet(0, "Limited"),
                new StatusSet(1, "Unlimited")};
        model.addAttribute("types", types);

        RentalAgreement ra = RENTAL_AGREEMENT_SERVICE.get(id);

        if (ra.getType() == RentalType.LIMITED) {
            ra.setTypeId(0);
        } else {
            ra.setTypeId(1);
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());

        model.addAttribute("RA", ra);
        model.addAttribute("cars", CAR_SERVICE.getCarsByStatus(CarStatus.READY_TO_BE_RENTED));

        return "dataregistering/rentalagreementedit";
    }


    @PostMapping("/lejekontrakt/{id}")
    public String editRentalAgreement(@PathVariable() int id, HttpSession session, @ModelAttribute RentalAgreement rentalAgreement) {
        if (session.getAttribute("userRole") != UserRole.DATA_REGISTRATION) {
            return "redirect:/logout";
        }
        rentalAgreement.setId(id);
        RENTAL_AGREEMENT_SERVICE.update(rentalAgreement);
        return "redirect:/lejekontrakt/" + id;
    }


    @GetMapping("/lejekontrakt/{id}/slet")
    public String deleteRentalAgreement(@PathVariable() int id) {

        RENTAL_AGREEMENT_SERVICE.delete(id);
        return "redirect:/lejekontrakter";
    }
}
