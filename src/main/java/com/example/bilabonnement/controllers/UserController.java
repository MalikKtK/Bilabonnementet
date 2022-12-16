package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.StatusSet;
import com.example.bilabonnement.models.User;
import com.example.bilabonnement.models.UserRole;
import com.example.bilabonnement.services.LocationService;
import com.example.bilabonnement.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
//Udarbejdet af Malik Kütük
@Controller
public class UserController {
    private final UserService USER_SERVICE = new UserService();
    private final LocationService LOCATION_SERVICE = new LocationService();


    @GetMapping("/opret-bruger")
    public String getCreateUser(HttpSession session, Model model) {
        if (session.getAttribute("userRole") != UserRole.ADMINISTRATOR) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());

        model.addAttribute("locations", LOCATION_SERVICE.getAllLocations());
        model.addAttribute("user", new User());
        return "administrator/usercreate";
    }


    @PostMapping("/opret-bruger")
    public String createUser(HttpSession session, @ModelAttribute User user) {
        if (session.getAttribute("userRole") != UserRole.ADMINISTRATOR) {
            return "redirect:/logout";
        }

        User createdUser = USER_SERVICE.createUser(user.getUsername(), user.getPassword(), user.getRoleID(), user.getLocationId());

        return "redirect:/bruger/" + createdUser.getId();
    }


    @GetMapping("/brugere")
    public String getAllUsers(HttpSession session, Model model) {
        if (session.getAttribute("userRole") != UserRole.ADMINISTRATOR) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");
        model.addAttribute("userRole", userRole.toString());

        model.addAttribute("listOfUsers", USER_SERVICE.getAllUsers());

        return "administrator/users";
    }


    @GetMapping("/bruger/{userID}")
    public String getEditUser(HttpSession session, @PathVariable() int userID, Model model) {
        if (session.getAttribute("userRole") != UserRole.ADMINISTRATOR) {
            return "redirect:/logout";
        }

        UserRole userRole = (UserRole) session.getAttribute("userRole");

        User user = USER_SERVICE.getUser(userID);

        switch (user.getRole()) {
            case DATA_REGISTRATION -> user.setRoleID(0);
            case DAMAGE_AND_RECTIFICATION -> user.setRoleID(1);
            case BUSINESS_DEVELOPER -> user.setRoleID(2);
            case ADMINISTRATOR -> user.setRoleID(3);
        }

        model.addAttribute("userRole", userRole.toString());

        model.addAttribute("user", user);
        model.addAttribute("locations", LOCATION_SERVICE.getAllLocations());

        StatusSet[] roles = {
                new StatusSet(0, "Dataregistrering"),
                new StatusSet(1, "Skade og Udbedring"),
                new StatusSet(2, "Forretningsudvikler"),
                new StatusSet(3, "Administrator")
        };
        model.addAttribute("roles", roles);

        return "administrator/useredit";
    }

    @PostMapping("/bruger/{userID}")
    public String editUser(HttpSession session, @PathVariable() int userID, @ModelAttribute User user) {
        if (session.getAttribute("userRole") != UserRole.ADMINISTRATOR) {
            return "redirect:/logout";
        }

        if (user.getPassword().equals("")) {
            USER_SERVICE.updateUser(userID, user.getUsername(), user.getRoleID(), user.getLocationId());
        } else {
            USER_SERVICE.updateUser(userID, user.getUsername(), user.getPassword(), user.getRoleID(), user.getLocationId());
        }

        return "redirect:/bruger/" + userID;
    }

    @GetMapping("/bruger/{userID}/slet")
    public String deleteUser(HttpSession session, @PathVariable() int userID) {
        if (session.getAttribute("userRole") != UserRole.ADMINISTRATOR) {
            return "redirect:/logout";
        }

        USER_SERVICE.deleteUser(userID);
        return "redirect:/brugere";
    }
}
