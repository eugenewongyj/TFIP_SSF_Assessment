package sg.edu.nus.iss.app.workshop14.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import sg.edu.nus.iss.app.workshop14.models.DeliveryDetails;
import sg.edu.nus.iss.app.workshop14.models.Pizza;
import sg.edu.nus.iss.app.workshop14.service.DeliveryDetailsService;

@Controller
public class PizzaController {

    @Autowired
    private DeliveryDetailsService deliveryDetailsService;

    @GetMapping(path="/")
    public String contactForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "index";
    }

    @PostMapping(path="/pizza")
    public String savePizza(@ModelAttribute @Valid Pizza pizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        System.out.println(pizza);
        DeliveryDetails deliveryDetails = new DeliveryDetails(pizza);
        // model.addAttribute("pizza", pizza);
        model.addAttribute("deliveryDetails", deliveryDetails);
        System.out.println(deliveryDetails);
        return "orderdeliverydetails";
    }

    @PostMapping(path="/pizza/order")
    public String saveDeliveryDetails(@ModelAttribute @Valid DeliveryDetails deliveryDetails, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "orderdeliverydetails";
        }
        System.out.println(deliveryDetails);
        DeliveryDetails deliveryDetailsSaved = deliveryDetailsService.saveDeliveryDetails(deliveryDetails);
        model.addAttribute("deliveryDetailsSaved", deliveryDetailsSaved);
        return "result";
    }

}
