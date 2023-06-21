package ch.noseryoung.blj.restfoods.domain.menu.Exceptions;

public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(){
        super("Order not found");
    }
}
