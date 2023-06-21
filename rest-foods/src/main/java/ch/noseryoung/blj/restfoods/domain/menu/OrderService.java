package ch.noseryoung.blj.restfoods.domain.menu;

import ch.noseryoung.blj.restfoods.domain.menu.Exceptions.OrderNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class OrderService {
    @Autowired
    private final OrderRepository repository;
    public OrderService(OrderRepository repository){
        this.repository = repository;
    }
    public List<Order> getAllOrders(){
        return repository.findAll();
    }

    public Order getOrderById(int id) throws OrderNotFoundException {
        return repository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public ResponseEntity<Order> createOrder(Order order){
        repository.save(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    public ResponseEntity<Order> updateOrder(Order orderNew) throws OrderNotFoundException {
        Order orderOld = getOrderById(orderNew.getOrderID());
        orderOld.setCustomerID(orderNew.getCustomerID());
        orderOld.setShippingMethodID(orderNew.getShippingMethodID());
        orderOld.setOrderID(orderNew.getOrderID());
        repository.save(orderOld);
        return new ResponseEntity<>(orderNew, HttpStatus.OK);
    }
    public ResponseEntity<Order> deleteOrder(Order order){
        repository.delete(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
