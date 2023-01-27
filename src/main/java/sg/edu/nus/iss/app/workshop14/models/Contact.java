package sg.edu.nus.iss.app.workshop14.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact implements Serializable {
    // Eugene: Kenneth answer has serializable
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message="Name cannot be null")
    @Size(min=3, max=64, message="Name must be between 3 and 64 characters")
    private String name;

    @Email(message="Invalid email")
    private String email;

    @Size(min=7, message="Phone number must be at least 7 digit")
    private String phoneNumber;

    @NotNull(message="Date of birth cannot be null")
    @Past(message="Date of birth must be in the past")
    @DateTimeFormat(pattern="MM-dd-yyyy")
    private LocalDate dateOfBirth;

    // Eugene: Not sure why there is a need for age. 
    // Answer: For validation
    @NotNull(message="User's age cannot be null")
    @Min(value=10, message="Must be above 10 years old")
    @Max(value=100, message="Must be below 100 years old")
    private int age;

    // Used when creating a new contact
    public Contact() {
        this.id = generateId(8);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        int calculatedAge = 0;
        if (dateOfBirth != null) {
            calculatedAge = Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        this.age = calculatedAge;
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
