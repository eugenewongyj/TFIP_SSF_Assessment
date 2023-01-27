package sg.edu.nus.iss.app.workshop14.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.workshop14.models.Contact;
import sg.edu.nus.iss.app.workshop14.models.DeliveryDetails;
import sg.edu.nus.iss.app.workshop14.models.Pizza;
import sg.edu.nus.iss.app.workshop14.repository.ContactsRedis;
import sg.edu.nus.iss.app.workshop14.service.DeliveryDetailsService;

@Controller
public class PizzaController {
    @Autowired
    private ContactsRedis contactsRedis;

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

    @PostMapping("/contact")
    public String saveContact(@Valid Contact contact, BindingResult bindingResult, Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "contact";
        }
        contactsRedis.save(contact);
        model.addAttribute("contact", contact);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "showContact";
    }

    @GetMapping(path="/contact")
    public String getAllContacts(@RequestParam(name="startIndex") Integer startIndex, Model model) {
        List<Contact> result = contactsRedis.findAll(startIndex);
        model.addAttribute("contacts", result);
        return "listContact";
    }

    @GetMapping(path="/contact/{contactId}") 
    public String getContactInfoById(@PathVariable(value="contactId") String contactId, Model model) {
        Contact contact = contactsRedis.findById(contactId);
        System.out.println("Taken from repo");
        model.addAttribute("contact", contact);
        return "showContact";
    }
}
