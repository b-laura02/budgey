package hu.blaura.budgey.modules.transaction.repository;

import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
