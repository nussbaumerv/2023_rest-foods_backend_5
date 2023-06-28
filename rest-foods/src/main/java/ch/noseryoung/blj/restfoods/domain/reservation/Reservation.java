package ch.noseryoung.blj.restfoods.domain.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "The first name must be stated")
    private String firstName;

    @NotNull(message = "The last name must pe stated")
    private String lastName;

    @NotNull(message = "Tne number of visitors must be specified")
    private String visitorCount;

    @NotNull(message = "The date must be specified")
    private String date;

    @NotNull(message = "Time must be specified")
    private String time;

    private String phoneNumber;
}