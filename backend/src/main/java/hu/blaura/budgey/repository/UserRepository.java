package hu.blaura.budgey.repository;

import hu.blaura.budgey.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
