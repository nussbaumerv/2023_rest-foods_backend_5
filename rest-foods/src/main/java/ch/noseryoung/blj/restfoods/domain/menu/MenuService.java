package ch.noseryoung.blj.restfoods.domain.menu;

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
    public MenuService(MenuRepository repository){
        this.repository = repository;
    }
    public List<Menu> getAllOrders(){
        return repository.findAll();
    }

    public Menu getProductById(int id) throws ProductNotFoundException {
        return repository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public ResponseEntity<Menu> createOrder(Menu menu){
        repository.save(menu);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
    public ResponseEntity<Menu> updateOrder(Menu menuNew) throws ProductNotFoundException {
        Menu menuOld = getProductById(menuNew.getMenuID());
        menuOld.setName(menuNew.getName());
        menuOld.setPrice(menuNew.getPrice());
        menuOld.setVegetarian(menuNew.isVegetarian());
        repository.save(menuOld);
        return new ResponseEntity<>(menuNew, HttpStatus.OK);
    }
    public ResponseEntity<Menu> deleteOrder(Menu menu){
        repository.delete(menu);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

}
