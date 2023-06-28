package ch.noseryoung.blj.restfoods.domain.reservation;

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
    public ReservationService(ReservationRepository repository){
        this.repo = repository;
    }
    public List<Reservation> getAllReservations(){
        return repo.findAll();
    }

    public Reservation getReservationById(int id) throws ReservationNotFoundException {
        return repo.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    public ResponseEntity<Reservation> createReservation(Reservation newReservation){
        repo.save(newReservation);
        return new ResponseEntity<>(newReservation, HttpStatus.OK);
    }
    public ResponseEntity<Reservation> updateReservation(Reservation newReservation) throws ReservationNotFoundException {
        Reservation oldReservation = getReservationById(newReservation.getReservationID());
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
    public ResponseEntity<Reservation> deleteReservation(Reservation reservation){
        repo.delete(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

}
