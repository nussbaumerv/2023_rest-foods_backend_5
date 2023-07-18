package ch.noseryoung.blj.restfoods.domain.user;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUserEmail(String useremail);
}
