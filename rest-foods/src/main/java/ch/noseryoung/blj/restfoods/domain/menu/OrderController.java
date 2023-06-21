package ch.noseryoung.blj.restfoods.domain.menu;

import ch.noseryoung.blj.restfoods.domain.menu.Exceptions.OrderNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("/order-services/v1/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(summary = "Fetch all orders", description = "With this method you can fetch all the orders from the Database")
    @GetMapping("")
    public ResponseEntity<List<Order>> getOrders() {
        log.info("Fetching all orders from DB");
        return ResponseEntity.ok().body(service.getAllOrders());
    }

    @Operation(summary = "Fetch order with specific ID", description = "With this method you can fetch a specific order from the Database with the given ID")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderByIndex(@Valid @PathVariable("orderId") Integer orderId) throws OrderNotFoundException {
        log.info("Fetching a specific order with orderID = " + orderId + " from DB");
        return ResponseEntity.ok().body(service.getOrderById(orderId));
    }

    @Operation(summary = "Create order", description = "With this method you can create a order in the Database")
    @PostMapping("")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order) {
        log.info("Creating new Order with customerID = " + order.getCustomerID() + " in DB");
        return service.createOrder(order);
    }

    @Operation(summary = "Delete order", description = "With this method you can delete a specific order in the Database")
    @DeleteMapping("")
    public ResponseEntity<Order> dropOrder(@Valid @RequestBody Order order) {
        log.info("Deleting Order with orderID = " + order.getOrderID() + " in DB");
        return service.deleteOrder(order);
    }

    @Operation(summary = "Update order", description = "With this method you can update a specific order in the Database")
    @PutMapping("")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order) throws OrderNotFoundException {
        log.info("Updating a Order with orderID = " + order.getOrderID() + " in DB");
        return service.updateOrder(order);
    }

    @Operation(summary = "Delete order with specific ID", description = "With this method you can delete a specific order from the Database with the given ID")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> dropOrderById(@Valid @PathVariable("orderId") Integer orderId) throws OrderNotFoundException {
        log.info("Deleting Order with orderID = " + orderId + " in DB from the given ID");
        return service.deleteOrder(service.getOrderById(orderId));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
    }

}