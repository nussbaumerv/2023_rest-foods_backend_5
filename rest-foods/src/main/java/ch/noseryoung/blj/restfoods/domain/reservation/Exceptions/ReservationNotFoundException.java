package ch.noseryoung.blj.restfoods.domain.menu.Exceptions;

public class ReservationNotFoundException extends Exception{
    public ReservationNotFoundException(){
        super("Reservation not found");
    }
}
