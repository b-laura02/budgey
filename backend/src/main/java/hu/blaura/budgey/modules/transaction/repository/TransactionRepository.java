package hu.blaura.budgey.modules.transaction.repository;

import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByOrderByDateDesc(Pageable pageable);

    Page<Transaction> findByUserOrderByDateDesc(User user, Pageable pageable);
    List<Transaction> findByUserAndDateBetween(User user, String startDate, String endDate);
}
