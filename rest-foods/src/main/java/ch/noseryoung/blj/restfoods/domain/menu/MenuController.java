package ch.noseryoung.blj.restfoods.domain.menu;
/** Represents an employee.
 * @author Valentin Nussbaumer
 * @version 1.0
*/

import ch.noseryoung.blj.restfoods.domain.menu.Exceptions.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Log4j2
@RestController
@RequestMapping("/rest-foods/v1/menu")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuController {
    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    /**
     * Fetches all products from the database.
     *
     * @return ResponseEntity containing the list of products
     */
    @Operation(summary = "Fetch all Products", description = "With this method you can fetch all the Products from the Database")
    @GetMapping("")
    public ResponseEntity<List<Menu>> getMenu() {
        log.info("Fetching all products from DB");
        return ResponseEntity.ok().body(service.getAllProducts());
    }

    /**
     * Sorts a list of menus based on the given attribute and order.
     *
     * @param toSort    the list of menus to be sorted
     * @param attribute the attribute to sort by (e.g., "relevance", "price", "name")
     * @param order     the sort order ("asc" for ascending, "desc" for descending)
     * @return the sorted list of menus
     */
    public List<Menu> sort(List<Menu> toSort, String attribute, String order) {

        try {
            Collections.sort(toSort, new Comparator<Menu>() {
                @Override
                public int compare(Menu m1, Menu m2) {
                    if(attribute.equals("relevance")){
                        if (order.equals("asc")) {
                            return Integer.compare(m1.getRelevance(), m2.getRelevance());
                        } else {
                            return Integer.compare(m2.getRelevance(), m1.getRelevance());
                        }
                    }
                    else if (attribute.equals("price")) {
                        if (order.equals("asc")) {
                            return Integer.compare(m1.getPrice(), m2.getPrice());
                        } else {
                            return Integer.compare(m2.getPrice(), m1.getPrice());
                        }
                    } else if (attribute.equals("name")) {
                        if (order.equals("desc")) {
                            return m2.getName().compareTo(m1.getName());
                        } else {
                            return m1.getName().compareTo(m2.getName());
                        }
                    }
                    return 0;
                }
            });

        } catch (Exception e) {
            log.error("Invalid Arguments");
        }
        return toSort;

    }

    /**
     * Filters a list of menus based on the given filter string.
     *
     * @param toFilter   the list of menus to be filtered
     * @param filterStr  the filter string to apply
     * @return the filtered list of menus
     */
    public List<Menu> filter(List<Menu> toFilter, String filterStr) {

        try {
            filterStr = filterStr.toLowerCase();

            String arr[] = null;
            arr = filterStr.split(";");

            List<Menu> filtertList = new ArrayList<>();

            for (int i = 0; i < toFilter.size(); i++) {
                if (arr[0].equals("null") || toFilter.get(i).getName().toLowerCase().contains(arr[0])) {
                    if (arr[1].equals("null") || String.valueOf(toFilter.get(i).getPrice()).toLowerCase().equals(arr[1])) {
                        if (arr[2].equals("null") || String.valueOf(toFilter.get(i).isVegetarian()).toLowerCase().equals(arr[2])) {
                            if (arr[3].equals("null") || toFilter.get(i).getType().toLowerCase().contains(arr[3])) {
                                filtertList.add(toFilter.get(i));
                            }
                        }
                    }
                }
            }

            return filtertList;
        } catch (Exception e) {
            log.error("Invalid Arguments");
            return toFilter;
        }
    }

    /**
     * Fetches all products from the database, sorted and filtered based on the given parameters.
     *
     * @param attribute the attribute to sort by (e.g., "relevance", "price", "name")
     * @param order     the sort order ("asc" for ascending, "desc" for descending)
     * @param filter    the filter string to apply
     * @return ResponseEntity containing the sorted and filtered list of products
     */
    @Operation(summary = "Fetch all Products", description = "With this method you can fetch all the Products from the Database")
    @GetMapping("/advanced")
    public ResponseEntity<List<Menu>> getMenuSorted(@Valid @RequestParam("attribute") String attribute,
                                                    @Valid @RequestParam("order") String order,
                                                    @Valid @RequestParam("filter") String filter) {
        log.info("Fetching sorted products from DB attributes: " + attribute + ", " + order + ", " + filter);

        List<Menu> listFiltered = filter(service.getAllProducts(), filter);
        List<Menu> listSorted = sort(listFiltered, attribute, order);

        return ResponseEntity.ok().body(listSorted);
    }

    /**
     * Fetches a specific product from the database based on the given ID.
     *
     * @param productId the ID of the product to fetch
     * @return ResponseEntity containing the product
     * @throws ProductNotFoundException if the product is not found
     */
    @Operation(summary = "Fetch Product with specific ID", description = "With this method you can fetch a specific Product from the Database with the given ID")
    @GetMapping("/{productId}")
    public ResponseEntity<Menu> getProductByIndex(@Valid @PathVariable("productId") Integer productId) throws ProductNotFoundException {
        log.info("Fetching a specific Product with productID = " + productId + " from DB");
        return ResponseEntity.ok().body(service.getProductById(productId));
    }

    /**
     * Creates a new product in the database.
     *
     * @param menu the product to be created
     * @return ResponseEntity containing the created product
     */
    @Operation(summary = "Create Product", description = "With this method you can create a Product in the Database")
    @PostMapping("")
    public ResponseEntity<Menu> addProduct(@Valid @RequestBody Menu menu) {
        log.info("Creating new Product with productID = " + menu.getMenuID() + " in DB");
        return service.createProduct(menu);
    }

    /**
     * Deletes a specific product from the database.
     *
     * @param menu the product to be deleted
     * @return ResponseEntity containing the deleted product
     */
    @Operation(summary = "Delete Product", description = "With this method you can delete a specific Product in the Database")
    @DeleteMapping("")
    public ResponseEntity<Menu> dropProduct(@Valid @RequestBody Menu menu) {
        log.info("Deleting Product with productID = " + menu.getMenuID() + " in DB");
        return service.deleteProduct(menu);
    }

    /**
     * Updates a specific product in the database.
     *
     * @param menu the updated product
     * @return ResponseEntity containing the updated product
     * @throws ProductNotFoundException if the product is not found
     */
    @Operation(summary = "Update Product", description = "With this method you can update a specific Product in the Database")
    @PutMapping("")
    public ResponseEntity<Menu> updateProdcut(@Valid @RequestBody Menu menu) throws ProductNotFoundException {
        log.info("Updating a Product with productID = " + menu.getMenuID() + " in DB");
        return service.updateProduct(menu);
    }

    /**
     * Deletes a specific product from the database based on the given ID.
     *
     * @param productId the ID of the product to be deleted
     * @return ResponseEntity containing the deleted product
     * @throws ProductNotFoundException if the product is not found
     */
    @Operation(summary = "Delete Product with specific ID", description = "With this method you can delete a specific Product from the Database with the given ID")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Menu> dropProductById(@Valid @PathVariable("productId") Integer productId) throws ProductNotFoundException {
        log.info("Deleting Product with productID = " + productId + " in DB from the given ID");
        return service.deleteProduct(service.getProductById(productId));
    }

    /**
     * Exception handler for handling ProductNotFoundException.
     *
     * @param e the ProductNotFoundException
     * @return ResponseEntity containing the error message
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    /**
     * Exception handler for handling MethodArgumentNotValidException.
     *
     * @param e the MethodArgumentNotValidException
     * @return ResponseEntity containing the error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
    }
}