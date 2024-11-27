package com.watcher.app.the_watcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watcher.app.the_watcher.dto.User;
import com.watcher.app.the_watcher.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String welcomePage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "Welcome";
    }

    @PostMapping("/home")
    public String login(@ModelAttribute User user, RedirectAttributes redirectAttributes ) {

        // System.out.println("Username entered: " + user.getUsername());
        
        boolean doesUserExist = userService.userExist(user.getUsername());
        // System.out.println("User's existence: " + doesUserExist);

        if (!doesUserExist){
            redirectAttributes.addFlashAttribute("wrongusername", "Incorrect username");
            return "redirect:/";
        }

        String userName = user.getUsername();
        // System.out.println("Users password is " + userService.getPasswordbyUsername(userName));

        if (!userService.getPasswordbyUsername(userName).equals(user.getPassword())){
            redirectAttributes.addFlashAttribute("wrongpassword", "Incorrect password");
            return "redirect:/";

        }

        System.out.println("Successful login");

        return "HomePage";
    }
    

    @PostMapping("/registered") //take both the inputs and add them into the database, sents them into /registered
    public String register(@Valid User details, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Welcome";
        }

        userService.savePerson(details);

        System.out.println("PERSON HAS BEEN CREATED" + details);


        return "registered";
    }

}
