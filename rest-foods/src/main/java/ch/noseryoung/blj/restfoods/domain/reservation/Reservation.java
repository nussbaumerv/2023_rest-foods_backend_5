package ch.noseryoung.blj.restfoods.domain.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Entity(name = "reservations")
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationID;

    @Positive(message = "reserver's ID must be positive")
    @NotNull(message = "reserver's ID can't be null")
    private Integer reserverID;

    @NotNull(message = "The date can't be null")
    private Date dateAndTime;

    @Positive(message = "table number Must be positive")
    @NotNull(message = "table number can't be null")
    private Integer tableNumber;

}