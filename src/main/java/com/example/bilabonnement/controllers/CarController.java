package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.Car;
import com.example.bilabonnement.models.CarStatus;
import com.example.bilabonnement.models.StatusSet;
import com.example.bilabonnement.models.UserRole;
import com.example.bilabonnement.services.CarService;
import com.example.bilabonnement.services.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
//Udarbejdet af Malik Kütük
@Controller
public class CarController {
    private final CarService carService = new CarService();
    private final LocationService locationService = new LocationService();
    private final StatusSet[] CAR_STATUS_SET = {
            new StatusSet(1, "klar til udlejning"),
            new StatusSet(2, "Klar til levering"),
            new StatusSet(3, "Udlejet"),
            new StatusSet(4, "Tilbage fra udlejning"),
            new StatusSet(5, "Klar til salg")
    };

    //En getmapping for opret bil. Så vi har adgang til vores html page gennem en browser.
    @GetMapping("/opret-bil")
    public String getCreateCar(HttpSession session, Model model) {
        if (session.getAttribute("userRole") == null) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());

        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("car", new Car());
        return "dataregistering/carcreate";
    }

    //En postmapping med opret bil. Her har vi sagt, at hvis vores userrole er ingenting, ryger vi til logout.
    @PostMapping("/opret-bil")
    public String createCar(HttpSession session, @ModelAttribute Car car) {
        if (session.getAttribute("userRole") == null) {
            return "redirect:/logout";
        }
//Når vi har en rolle, ryger vi på funktionen carService. Createcar som arbejder sammen med carRepo til at oprette en bil i en database.
        Car createdCar = carService.createCar(car);

        return "redirect:/bil/" + createdCar.getId();
    }

    @GetMapping("/biler")
    public String viewAllCars(HttpSession session, Model model) {
        if (session.getAttribute("userRole") == null) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());

        model.addAttribute("listOfCars", carService.getAllCars());

        return "dataregistering/cars";
    }

    @GetMapping("/biler/data")
    public String carData(HttpSession session, Model model) {
        if (session.getAttribute("userRole") != UserRole.BUSINESS_DEVELOPER) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());

        List<Car> listOfCars = carService.getCarsByStatus(CarStatus.RENTED);

        model.addAttribute("listOfRentedCars", listOfCars);
        model.addAttribute("numberOfRentedCars", listOfCars.size());
        model.addAttribute("totalPriceOfRentedCars", carService.getPriceOfRentedCars());

        return "dataregistering/cardata";
    }


    @GetMapping("/bil/{carID}")
    public String getEditCar(HttpSession session, @PathVariable() int carID, Model model) {
        if (session.getAttribute("userRole") == null) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());

        Car car = carService.getCar(carID);

        car.setCarStatusId(carService.getCarStatusValue(car));
        model.addAttribute("statuses", CAR_STATUS_SET);

        model.addAttribute("car", car);
        model.addAttribute("locations", locationService.getAllLocations());

        return "dataregistering/caredit";
    }

    @PostMapping("/bil/{carID}")
    public String editCar(HttpSession session, @ModelAttribute Car car, @PathVariable int carID) {
        if (session.getAttribute("userRole") == null) {
            return "redirect:/logout";
        }

        carService.updateCar(carID,
                car.getCarNumber(),
                CarStatus.values()[car.getCarStatusId() - 1],
                car.getMake(),
                car.getModel(),
                car.getCarPrice(),
                car.getRegistrationFee(),
                car.getCo2Emission(),
                car.getKilometersDriven(),
                car.getDamage(),
                car.getColour(),
                car.getFuelType(),
                car.getLocationId());

        return "redirect:/biler";

    }

    @GetMapping("/bil/{carID}/slet")
    public String deleteCar(HttpSession session, @PathVariable() int carID, Model model) {
        if (session.getAttribute("userRole") == null) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole);

        carService.deleteCar(carID);

        return "redirect:/biler";
    }

}
