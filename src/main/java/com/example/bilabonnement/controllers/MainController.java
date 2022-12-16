package com.example.bilabonnement.controllers;

import com.example.bilabonnement.models.User;
import com.example.bilabonnement.models.UserRole;
import com.example.bilabonnement.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
//Udarbejdet af Malik Kütük
@Controller
public class MainController {
    private final UserService USER_SERVICE = new UserService();

    @GetMapping("/")
    public String getLandingPage(HttpSession session, Model model) {

        if (session.getAttribute("loginSuccess") == null) {
            session.setAttribute("loginSuccess", "none");
        }
        model.addAttribute("loginValidity", session.getAttribute("loginSuccess"));

        if (session.getAttribute("userID") != null) {
            return "redirect:/home";
        }

        model.addAttribute("user", new User());
        return "home/landingpage";
    }

    //Når vi ryger på main pagen. Hvilken er funktionen over den her. Kan man logge ind.
    //Vi har gjort så, at når du skriver forkerte værdier ind, ryger du bare tilbage til den samme side.
    //Men hvis du logger ind på en rigtig bruger ryger du på /home som er en getmapping.
    @PostMapping("/login")
    public String landingPage(WebRequest dataFromForm, HttpSession session) {

        User user = USER_SERVICE.login(dataFromForm.getParameter("uname"), dataFromForm.getParameter("psw"));

        if (user != null) {
            session.setAttribute("userID", user.getId());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("loginSuccess", "success");
            return "redirect:/home";
        } else {
            session.setAttribute("loginSuccess", "fail");
            return "redirect:/";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //Vores brugere har forskellige roller. Og disse roller afgør hvilken page du ryger på.
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        UserRole userRole = (UserRole) session.getAttribute("userRole");
        if (userRole == UserRole.ADMINISTRATOR) {
            model.addAttribute("userRole", userRole.toString());
            return "administrator/administrator";
        } else if (userRole == UserRole.BUSINESS_DEVELOPER) {
            model.addAttribute("userRole", userRole.toString());
            return "businessdeveloper/businessdeveloper";
        } else if (userRole == UserRole.DAMAGE_AND_RECTIFICATION) {
            model.addAttribute("userRole", userRole.toString());
            return "skade/damage";
        } else if (userRole == UserRole.DATA_REGISTRATION) {
            model.addAttribute("userRole", userRole.toString());
            return "dataregistering/dataregistration";
        }

        return "redirect:/";
    }
}
