package ch.noseryoung.blj.restfoods.domain.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;



@Entity(name = "customer")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerID;

    private String firstName;

    private String lastName;

    @Max(value = 100, message = "Age can't be that high")
    @PositiveOrZero(message = "Price must be positive or zero")
    private Integer age;

    private String gender;
}