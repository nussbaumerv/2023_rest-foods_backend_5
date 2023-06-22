package ch.noseryoung.blj.restfoods.domain.reservation;

import ch.noseryoung.blj.restfoods.domain.reservation.Exceptions.ReservationNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("/reservation-services/v1/reservations")
public class ReservationController {
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @Operation(summary = "Fetch all reservations", description = "With this method you can fetch all the reservations from the Database")
    @GetMapping("")
    public ResponseEntity<List<Reservation>> getReservations() {
        log.info("Fetching all reservations from DB");
        return ResponseEntity.ok().body(service.getAllReservations());
    }

    @Operation(summary = "Fetch reservation with specific ID", description = "With this method you can fetch a specific reservation from the Database with the given ID")
    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationByIndex(@Valid @PathVariable("reservationId") Integer reservationId) throws ReservationNotFoundException {
        log.info("Fetching a specific reservation with reservationID = " + reservationId + " from DB");
        return ResponseEntity.ok().body(service.getReservationById(reservationId));
    }

    @Operation(summary = "Create reservation", description = "With this method you can create a reservation in the Database")
    @PostMapping("")
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody Reservation reservation) {
        log.info("Creating new Reservation with reserver's name = " + reservation.getReservationID() + " in DB");
        return service.createReservation(reservation);
    }

    @Operation(summary = "Delete reservation", description = "With this method you can delete a specific reservation in the Database")
    @DeleteMapping("")
    public ResponseEntity<Reservation> dropReservation(@Valid @RequestBody Reservation reservation) {
        log.info("Deleting Reservation with reservationID = " + reservation.getReservationID() + " in DB");
        return service.deleteReservation(reservation);
    }

    @Operation(summary = "Update reservation", description = "With this method you can update a specific reservation in the Database")
    @PutMapping("")
    public ResponseEntity<Reservation> updateReservation(@Valid @RequestBody Reservation reservation) throws ReservationNotFoundException {
        log.info("Updating a Reservation with reservationID = " + reservation.getReservationID() + " in DB");
        return service.updateReservation(reservation);
    }

    @Operation(summary = "Delete reservation with specific ID", description = "With this method you can delete a specific reservation from the Database with the given ID")
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Reservation> dropReservationById(@Valid @PathVariable("reservationId") Integer reservationId) throws ReservationNotFoundException {
        log.info("Deleting Reservation with reservationID = " + reservationId + " in DB from the given ID");
        return service.deleteReservation(service.getReservationById(reservationId));
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException(ReservationNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
    }

}