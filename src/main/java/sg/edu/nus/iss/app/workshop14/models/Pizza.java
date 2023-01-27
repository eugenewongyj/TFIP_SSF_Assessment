package sg.edu.nus.iss.app.workshop14.models;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza implements Serializable {

    // From View 0 

    @NotNull(message="Must have a pizza selection")
    private String type;

    private String size;

    @Min(value=1, message="Minimum of 1 pizza")
    @Max(value=10, message="Maximum of 10 pizzas")
    private Integer quantity;

    public Pizza() {
        
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Pizza [type=" + type + ", size=" + size + ", quantity=" + quantity + "]";
    }

    

    

    
    
    
}
