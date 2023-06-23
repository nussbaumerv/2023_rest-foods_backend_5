package ch.noseryoung.blj.restfoods.domain.customer.Exceptions;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(){
        super("Customer not found");
    }
}
