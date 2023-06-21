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
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationID;

    @Positive(message = "CostumerID Must be positive")
    @NotNull(message = "CostumerID can't be null")
    private Integer reserverID;

    private String dateAndTime;

    @Positive(message = "table number Must be positive")
    @NotNull(message = "table number can't be null")
    private Integer tableNumber;


}