package ch.noseryoung.blj.restfoods.domain.reservation;
/** Represents an employee.
 * @author Yan Pishchan
 * @version 1.0
 */

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
@RequestMapping("/rest-foods/v1/reservations")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    /**
     * Fetches all reservations from the database.
     *
     * @return The ResponseEntity containing the list of reservations.
     */
    @Operation(summary = "Fetch all reservations", description = "With this method you can fetch all the reservations from the Database")
    @GetMapping("")
    public ResponseEntity<List<Reservation>> getReservations() {
        log.info("Fetching all reservations from DB");
        return ResponseEntity.ok().body(service.getAllReservations());
    }

    /**
     * Fetches a specific reservation from the database with the given ID.
     *
     * @param reservationId The ID of the reservation to fetch.
     * @return The ResponseEntity containing the fetched reservation.
     * @throws ReservationNotFoundException If the reservation with the given ID is not found.
     */
    @Operation(summary = "Fetch reservation with specific ID", description = "With this method you can fetch a specific reservation from the Database with the given ID")
    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationByIndex(@Valid @PathVariable("reservationId") Integer reservationId) throws ReservationNotFoundException {
        log.info("Fetching a specific reservation with reservationID = " + reservationId + " from DB");
        return ResponseEntity.ok().body(service.getReservationById(reservationId));
    }

    /**
     * Creates a new reservation in the database.
     *
     * @param reservation The reservation to be created.
     * @return The ResponseEntity containing the created reservation.
     */
    @Operation(summary = "Create reservation", description = "With this method you can create a reservation in the Database")
    @PostMapping("")
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody Reservation reservation) {
        log.info("Creating new Reservation with reserver's name = " + reservation.getReservationID() + " in DB");
        return service.createReservation(reservation);
    }

    /**
     * Deletes a specific reservation from the database.
     *
     * @param reservation The reservation to be deleted.
     * @return The ResponseEntity containing the deleted reservation.
     */
    @Operation(summary = "Delete reservation", description = "With this method you can delete a specific reservation in the Database")
    @DeleteMapping("")
    public ResponseEntity<Reservation> dropReservation(@Valid @RequestBody Reservation reservation) {
        log.info("Deleting Reservation with reservationID = " + reservation.getReservationID() + " in DB");
        return service.deleteReservation(reservation);
    }

    /**
     * Updates a specific reservation in the database.
     *
     * @param reservation The reservation to be updated.
     * @return The ResponseEntity containing the updated reservation.
     * @throws ReservationNotFoundException If the reservation to be updated is not found.
     */
    @Operation(summary = "Update reservation", description = "With this method you can update a specific reservation in the Database")
    @PutMapping("")
    public ResponseEntity<Reservation> updateReservation(@Valid @RequestBody Reservation reservation) throws ReservationNotFoundException {
        log.info("Updating a Reservation with reservationID = " + reservation.getReservationID() + " in DB");
        return service.updateReservation(reservation);
    }

    /**
     * Deletes a specific reservation from the database with the given ID.
     *
     * @param reservationId The ID of the reservation to be deleted.
     * @return The ResponseEntity containing the deleted reservation.
     * @throws ReservationNotFoundException If the reservation with the given ID is not found.
     */
    @Operation(summary = "Delete reservation with specific ID", description = "With this method you can delete a specific reservation from the Database with the given ID")
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Reservation> dropReservationById(@Valid @PathVariable("reservationId") Integer reservationId) throws ReservationNotFoundException {
        log.info("Deleting Reservation with reservationID = " + reservationId + " in DB from the given ID");
        return service.deleteReservation(service.getReservationById(reservationId));
    }

    /**
     * Handles the exception when a ReservationNotFoundException occurs.
     *
     * @param e The thrown ReservationNotFoundException.
     * @return The ResponseEntity containing the error message.
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException(ReservationNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    /**
     * Handles the exception when a MethodArgumentNotValidException occurs.
     *
     * @param e The thrown MethodArgumentNotValidException.
     * @return The ResponseEntity containing the error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
    }

}
