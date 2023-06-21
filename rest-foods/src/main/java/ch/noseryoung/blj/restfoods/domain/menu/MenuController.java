package ch.noseryoung.blj.restfoods.domain.menu;

import ch.noseryoung.blj.restfoods.domain.menu.Exceptions.ProductNotFoundException;
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
public class MenuController {
    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @Operation(summary = "Fetch all Products", description = "With this method you can fetch all the Products from the Database")
    @GetMapping("")
    public ResponseEntity<List<Menu>> getMenu() {
        log.info("Fetching all orders from DB");
        return ResponseEntity.ok().body(service.getAllOrders());
    }

    @Operation(summary = "Fetch Product with specific ID", description = "With this method you can fetch a specific Product from the Database with the given ID")
    @GetMapping("/{orderId}")
    public ResponseEntity<Menu> getProductByIndex(@Valid @PathVariable("orderId") Integer orderId) throws ProductNotFoundException {
        log.info("Fetching a specific Product with orderID = " + orderId + " from DB");
            return ResponseEntity.ok().body(service.getProductById(orderId));
    }

    @Operation(summary = "Create Product", description = "With this method you can create a Product in the Database")
    @PostMapping("")
    public ResponseEntity<Menu> addProduct(@Valid @RequestBody Menu menu) {
        log.info("Creating new Product with customerID = " + menu.getMenuID() + " in DB");
        return service.createOrder(menu);
    }

    @Operation(summary = "Delete Product", description = "With this method you can delete a specific Product in the Database")
    @DeleteMapping("")
    public ResponseEntity<Menu> dropProduct(@Valid @RequestBody Menu menu) {
        log.info("Deleting Product with orderID = " + menu.getMenuID() + " in DB");
        return service.deleteOrder(menu);
    }

    @Operation(summary = "Update Product", description = "With this method you can update a specific Product in the Database")
    @PutMapping("")
    public ResponseEntity<Menu> updateProdcut(@Valid @RequestBody Menu menu) throws ProductNotFoundException {
        log.info("Updating a Product with orderID = " + menu.getMenuID() + " in DB");
        return service.updateOrder(menu);
    }

    @Operation(summary = "Delete Product with specific ID", description = "With this method you can delete a specific Product from the Database with the given ID")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Menu> dropProductById(@Valid @PathVariable("orderId") Integer orderId) throws ProductNotFoundException {
        log.info("Deleting Product with orderID = " + orderId + " in DB from the given ID");
        return service.deleteOrder(service.getProductById(orderId));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(ProductNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
    }

}