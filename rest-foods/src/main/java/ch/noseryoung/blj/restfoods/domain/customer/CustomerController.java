package ch.noseryoung.blj.restfoods.domain.customer;

import ch.noseryoung.blj.restfoods.domain.customer.Exceptions.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("/customer-services/v1/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @Operation(summary = "Fetch all customers", description = "With this method you can fetch all the customers from the Database")
    @GetMapping("")
    public ResponseEntity<List<Customer>> getCustomers() {
        log.info("Fetching all customers from DB");
        return ResponseEntity.ok().body(service.getAllCustomers());
    }

    @Operation(summary = "Fetch customer with specific ID", description = "With this method you can fetch a specific customer from the Database with the given ID")
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerByIndex(@Valid @PathVariable("customerId") Integer customerId) throws CustomerNotFoundException {
        log.info("Fetching a specific customer with customerID = " + customerId + " from DB");
        return ResponseEntity.ok().body(service.getCustomerById(customerId));
    }

    @Operation(summary = "Create customer", description = "With this method you can create a customer in the Database")
    @PostMapping("")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
        log.info("Creating new Customer with reserver's name = " + customer.getCustomerID() + " in DB");
        return service.createCustomer(customer);
    }

    @Operation(summary = "Delete customer", description = "With this method you can delete a specific customer in the Database")
    @DeleteMapping("")
    public ResponseEntity<Customer> dropCustomer(@Valid @RequestBody Customer customer) {
        log.info("Deleting Customer with customerID = " + customer.getCustomerID() + " in DB");
        return service.deleteCustomer(customer);
    }

    @Operation(summary = "Update customer", description = "With this method you can update a specific customer in the Database")
    @PutMapping("")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) throws CustomerNotFoundException {
        log.info("Updating a Customer with customerID = " + customer.getCustomerID() + " in DB");
        return service.updateCustomer(customer);
    }

    @Operation(summary = "Delete customer with specific ID", description = "With this method you can delete a specific customer from the Database with the given ID")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> dropCustomerById(@Valid @PathVariable("customerId") Integer customerId) throws CustomerNotFoundException {
        log.info("Deleting Customer with customerID = " + customerId + " in DB from the given ID");
        return service.deleteCustomer(service.getCustomerById(customerId));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
    }

}