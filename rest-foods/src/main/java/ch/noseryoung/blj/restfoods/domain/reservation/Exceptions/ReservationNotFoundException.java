package ch.noseryoung.blj.restfoods.domain.reservation.Exceptions;

public class ReservationNotFoundException extends Exception{
    public ReservationNotFoundException(){
        super("Reservation not found");
    }
}
