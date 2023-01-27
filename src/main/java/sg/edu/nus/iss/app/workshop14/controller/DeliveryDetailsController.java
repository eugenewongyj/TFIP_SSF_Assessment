package sg.edu.nus.iss.app.workshop14.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import sg.edu.nus.iss.app.workshop14.service.DeliveryDetailsService;

@RestController
@RequestMapping(path="/order")
public class DeliveryDetailsController {

    @Autowired
    private DeliveryDetailsService deliveryDetailsService;

    @GetMapping(path="/{orderId}")
    public ResponseEntity<String> getDeliveryDetailsById(@PathVariable String orderId) {
        Optional<String> deliveryDetailsJson = deliveryDetailsService.getDeliveryDetailsJsonById(orderId);

        if (deliveryDetailsJson.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(deliveryDetailsJson.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(Json.createObjectBuilder().add("message", "Order %s not found".formatted(orderId)).build().toString());
    }


    
}
