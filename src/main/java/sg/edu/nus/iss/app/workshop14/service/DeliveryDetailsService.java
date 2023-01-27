package sg.edu.nus.iss.app.workshop14.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.workshop14.models.DeliveryDetails;

@Service
public class DeliveryDetailsService {

    private static final double RUSH_MULTIPLIER = 1.2;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public DeliveryDetails saveDeliveryDetails(DeliveryDetails deliveryDetails) {
        deliveryDetails.setId(generateId(8));
        deliveryDetails.setTotalCost(calculateTotalCost(deliveryDetails));
        // redisTemplate.opsForValue().set(deliveryDetails.getId(), deliveryDetails.toJsonObject().toString());
        return deliveryDetails;
    }

    private double calculateTotalCost(DeliveryDetails deliveryDetails) {
        double pizzaPrice = getPizzaPrice(deliveryDetails.getPizza().getType());
        int pizzaQuantity = deliveryDetails.getPizza().getQuantity();
        double totalCost = deliveryDetails.getIsRush() ? pizzaPrice * pizzaQuantity * RUSH_MULTIPLIER : pizzaPrice * pizzaQuantity;
        return totalCost;
    }

    private double getPizzaPrice(String type) {
        double pizzaPrice = 0;
        switch (type) {
            case "bella":
            case "marinara":
            case "spianatacalabrese":
                pizzaPrice = 30;
            case "margherita":
                pizzaPrice = 22;
            case "trioformaggio":
                pizzaPrice = 25;
        }
        return pizzaPrice;
    }

    private synchronized String generateId(int numChars) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < numChars) {
            sb.append(Integer.toHexString(random.nextInt()));
        }

        return sb.toString().substring(0, numChars);

    }
    
}
