package ch.noseryoung.blj.restfoods.domain.menu.Exceptions;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(){
        super("Product not found");
    }
}
