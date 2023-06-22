package ch.noseryoung.blj.restfoods.domain.customers.Exceptions;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(){
        super("Customer not found");
    }
}
