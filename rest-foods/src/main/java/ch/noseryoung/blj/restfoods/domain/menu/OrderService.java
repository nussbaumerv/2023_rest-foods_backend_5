package ch.noseryoung.blj.restfoods.domain.menu;

import ch.noseryoung.blj.restfoods.domain.menu.Exceptions.ReservationNotFoundException;
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
    private final ReservationRepository repository;
    public ReservationService(ReservationRepository repository){
        this.repository = repository;
    }
    public List<Reservation> getAllReservations(){
        return repository.findAll();
    }

    public Reservation getReservationById(int id) throws ReservationNotFoundException {
        return repository.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    public ResponseEntity<Reservation> createReservation(Reservation reservation){
        repository.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
    public ResponseEntity<Reservation> updateReservation(Reservation reservationNew) throws ReservationNotFoundException {
        Reservation reservationOld = getReservationById(reservationNew.getReservationID());
        reservationOld.setCustomerID(reservationNew.getCustomerID());
        reservationOld.setShippingMethodID(reservationNew.getShippingMethodID());
        reservationOld.setReservationID(reservationNew.getReservationID());
        repository.save(reservationOld);
        return new ResponseEntity<>(reservationNew, HttpStatus.OK);
    }
    public ResponseEntity<Reservation> deleteReservation(Reservation reservation){
        repository.delete(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

}
