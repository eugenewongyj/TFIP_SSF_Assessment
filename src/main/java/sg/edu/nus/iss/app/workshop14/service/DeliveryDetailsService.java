package sg.edu.nus.iss.app.workshop14.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.workshop14.models.DeliveryDetails;

@Service
public class DeliveryDetailsService {

    private static final double RUSH_MULTIPLIER = 2;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public DeliveryDetails saveDeliveryDetails(DeliveryDetails deliveryDetails) {
        deliveryDetails.setId(generateId(8));
        deliveryDetails.setTotalCost(calculateTotalCost(deliveryDetails));
        redisTemplate.opsForValue().set(deliveryDetails.getId(), deliveryDetails.toJsonObject().toString());
        return deliveryDetails;
    }

    public Optional<String> getDeliveryDetailsJsonById(String orderId) {
        String deliveryDetailsJson = (String) redisTemplate.opsForValue().get(orderId);
        if (deliveryDetailsJson != null) {
            return Optional.of(deliveryDetailsJson);
        }
        return Optional.empty();
    }

    private double calculateTotalCost(DeliveryDetails deliveryDetails) {
        double pizzaPrice = getPizzaPrice(deliveryDetails.getPizza().getType());
        double pizzaSizeMultiplier = getPizzaSizeMultiplier(deliveryDetails.getPizza().getSize());
        int pizzaQuantity = deliveryDetails.getPizza().getQuantity();
        double totalCost = deliveryDetails.getIsRush() ? pizzaPrice * pizzaSizeMultiplier * pizzaQuantity + RUSH_MULTIPLIER : pizzaPrice * pizzaSizeMultiplier * pizzaQuantity;
        return totalCost;
    }

    private double getPizzaSizeMultiplier(String size) {
        double pizzaSizeMultiplier = 0;
        switch (size) {
            case "sm":
                pizzaSizeMultiplier = 1;
                break;
            case "md":
                pizzaSizeMultiplier = 1.2;
                break;
            case "lg":
                pizzaSizeMultiplier = 1.5;
                break;

        }

        return pizzaSizeMultiplier;
    }

    private double getPizzaPrice(String type) {
        double pizzaPrice = 0;
        switch (type) {
            case "bella":
            case "marinara":
            case "spianatacalabrese":
                pizzaPrice = 30;
                break;
            case "margherita":
                pizzaPrice = 22;
                break;
            case "trioformaggio":
                pizzaPrice = 25;
                break;
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
