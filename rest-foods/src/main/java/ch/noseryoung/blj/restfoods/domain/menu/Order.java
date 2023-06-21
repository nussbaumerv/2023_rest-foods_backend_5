package ch.noseryoung.blj.restfoods.domain.menu;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;



@Entity(name = "rest-food-db")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;

    @Positive(message = "CostumerID Must be positive")
    @Max(value = 2147483647, message = "CustomerID can't be that big")
    @NotNull(message = "CostumerID can't be null")
    private Integer customerID;

    @Max(value = 10, message = "There only 10 Shipping Methods available")
    @PositiveOrZero(message = "shippingMethodID Must be positive or zero")
    private Integer shippingMethodID;
    @Max(value = 2147483647, message = "bookID can't be that big")
    @Positive(message = "bookID Must be positive")
    @NotNull(message = "bookID can't be null")
    private Integer bookID;


}