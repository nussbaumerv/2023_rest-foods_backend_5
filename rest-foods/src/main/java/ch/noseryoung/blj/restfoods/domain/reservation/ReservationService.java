package ch.noseryoung.blj.restfoods.domain.reservation;
/** Represents an employee.
 * @author Yan Pishchan
 * @version 1.0
 */

import ch.noseryoung.blj.restfoods.domain.reservation.Exceptions.ReservationNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ReservationService {
    @Autowired
    private final ReservationRepository repo;

    /**
     * Constructs a new ReservationService with the specified repository.
     *
     * @param repository The repository to use for accessing reservation data.
     */
    public ReservationService(ReservationRepository repository) {
        this.repo = repository;
    }

    /**
     * Retrieves all reservations.
     *
     * @return A list of all reservations.
     */
    public List<Reservation> getAllReservations() {
        return repo.findAll();
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param id The ID of the reservation to retrieve.
     * @return The reservation with the specified ID.
     * @throws ReservationNotFoundException If the reservation with the specified ID is not found.
     */
    public Reservation getReservationById(int id) throws ReservationNotFoundException {
        return repo.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    /**
     * Creates a new reservation.
     *
     * @param newReservation The reservation to create.
     * @return The created reservation.
     */
    public ResponseEntity<Reservation> createReservation(Reservation newReservation) {
        repo.save(newReservation);
        return new ResponseEntity<>(newReservation, HttpStatus.OK);
    }

    /**
     * Updates an existing reservation.
     *
     * @param newReservation The updated reservation.
     * @return The updated reservation.
     * @throws ReservationNotFoundException If the reservation with the specified ID is not found.
     */
    public ResponseEntity<Reservation> updateReservation(Reservation newReservation) throws ReservationNotFoundException {
        Reservation oldReservation = getReservationById(newReservation.getReservationID());
        // Update the fields of the old reservation with the new reservation's values
        oldReservation.setReservationID(newReservation.getReservationID());
        oldReservation.setFirstName(newReservation.getFirstName());
        oldReservation.setLastName(newReservation.getLastName());
        oldReservation.setVisitorCount(newReservation.getVisitorCount());
        oldReservation.setDate(newReservation.getDate());
        oldReservation.setTime(newReservation.getTime());
        oldReservation.setPhoneNumber(newReservation.getPhoneNumber());
        repo.save(oldReservation);
        return new ResponseEntity<>(newReservation, HttpStatus.OK);
    }

    /**
     * Deletes a reservation.
     *
     * @param reservation The reservation to delete.
     * @return The deleted reservation.
     */
    public ResponseEntity<Reservation> deleteReservation(Reservation reservation) {
        repo.delete(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
}