package sg.edu.nus.iss.app.workshop14.models;

import java.io.Serializable;
import java.util.Random;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DeliveryDetails implements Serializable {

    private String id;

    @NotBlank(message="Name is mandatory")
    @Size(min=3, message="Name must have a minimum of 3 characters")
    private String name;

    @NotBlank(message="Address is mandatory")
    private String address;

    @NotBlank(message="Phone number is mandatory")
    @Size(min=8, max=8, message="Phone number must be 8 digits")
    private String phone;

    private Boolean isRush;

    private String comments;

    private Double totalCost;

    private Pizza pizza;

    public DeliveryDetails() {

    }

    public DeliveryDetails(Pizza pizza) {
        this.pizza = pizza;
    }

    

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsRush() {
        return isRush;
    }

    public void setIsRush(Boolean isRush) {
        this.isRush = isRush;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    

    @Override
    public String toString() {
        return "DeliveryDetails [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone
                + ", isRush=" + isRush + ", comments=" + comments + ", totalCost=" + totalCost + ", pizza=" + pizza
                + "]";
    }

    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
                    .add("orderId", this.getId())
                    .add("name", this.getName())
                    .add("address", this.getAddress())
                    .add("rush", this.getIsRush())
                    .add("comments", this.getComments())
                    .add("pizza", this.getPizza().getType())
                    .add("size", this.getPizza().getSize())
                    .add("quantity", this.getPizza().getQuantity())
                    .add("total", this.getTotalCost())
                    .build();
    }



    




    
    
}
