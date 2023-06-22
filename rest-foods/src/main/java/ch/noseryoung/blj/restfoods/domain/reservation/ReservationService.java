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

    public ResponseEntity<Reservation> createReservation(Reservation res){
        repo.save(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    public ResponseEntity<Reservation> updateReservation(Reservation reservationNew) throws ReservationNotFoundException {
        Reservation reservationOld = getReservationById(reservationNew.getReservationID());
        reservationOld.setReserverID(reservationNew.getReserverID());
        reservationOld.setDateAndTime(reservationNew.getDateAndTime());
        reservationOld.setReservationID(reservationNew.getReservationID());
        repo.save(reservationOld);
        return new ResponseEntity<>(reservationNew, HttpStatus.OK);
    }
    public ResponseEntity<Reservation> deleteReservation(Reservation reservation){
        repo.delete(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

}
