package ch.noseryoung.blj.restfoods.domain.menu;
/** Represents an employee.
 * @author Valentin Nussbaumer
 * @version 1.0
 */

import ch.noseryoung.blj.restfoods.domain.menu.Exceptions.ProductNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class MenuService {
    @Autowired
    private final MenuRepository repository;

    /**
     * Constructs a new MenuService with the specified repository.
     *
     * @param repository The repository to use for accessing menu product data.
     */
    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all menu products.
     *
     * @return A list of all menu products.
     */
    public List<Menu> getAllProducts() {
        return repository.findAll();
    }

    /**
     * Retrieves a menu product by its ID.
     *
     * @param name The ID of the menu product to retrieve.
     * @return The menu product with the specified ID.
     * @throws ProductNotFoundException If the menu product with the specified ID is not found.
     */
    public Menu getProductByName(String name) throws ProductNotFoundException {
        return repository.findByName(name).orElseThrow(ProductNotFoundException::new);
    }

    /**
     * Creates a new menu product.
     *
     * @param newMenu The menu product to create.
     * @return The created menu product.
     */
    public ResponseEntity<Menu> createProduct(Menu newMenu) {
        repository.save(newMenu);
        return new ResponseEntity<>(newMenu, HttpStatus.OK);
    }

    /**
     * Updates an existing menu product.
     *
     * @param menuNew The updated menu product.
     * @return The updated menu product.
     * @throws ProductNotFoundException If the menu product with the specified ID is not found.
     */
    public ResponseEntity<Menu> updateProduct(Menu menuNew) throws ProductNotFoundException {
        Menu menuOld = getProductByName(menuNew.getName());
        // Update the fields of the old menu product with the new menu product's values
        menuOld.setName(menuNew.getName());
        menuOld.setRelevance(menuNew.getRelevance());
        menuOld.setPrice(menuNew.getPrice());
        menuOld.setVegetarian(menuNew.isVegetarian());
        menuOld.setDescription(menuNew.getDescription());
        menuOld.setImg_url(menuNew.getImg_url());
        repository.save(menuOld);
        return new ResponseEntity<>(menuNew, HttpStatus.OK);
    }

    /**
     * Deletes a menu product.
     *
     * @param menu The menu product to delete.
     * @return The deleted menu product.
     */
    public ResponseEntity<Menu> deleteProduct(Menu menu) {
        repository.delete(menu);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
}
