package ch.noseryoung.blj.restfoods.domain.customer;

import ch.noseryoung.blj.restfoods.domain.customer.Exceptions.CustomerNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository repository;
    public CustomerService(CustomerRepository repository){
        this.repository = repository;
    }
    public List<Customer> getAllCustomers(){
        return repository.findAll();
    }

    public Customer getCustomerById(int id) throws CustomerNotFoundException{
        return repository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    public ResponseEntity<Customer> createCustomer(Customer customer){
        repository.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    public ResponseEntity<Customer> updateCustomer(Customer customerNew) throws CustomerNotFoundException{
        Customer customerOld = getCustomerById(customerNew.getCustomerID());
        customerOld.setFirstName(customerNew.getFirstName());
        customerOld.setAge(customerNew.getAge());
        repository.save(customerOld);
        return new ResponseEntity<>(customerNew, HttpStatus.OK);
    }
    public ResponseEntity<Customer> deleteCustomer(Customer customer){
        repository.delete(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
