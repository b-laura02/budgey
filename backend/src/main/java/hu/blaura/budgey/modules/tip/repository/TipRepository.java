package hu.blaura.budgey.modules.tip.repository;

import hu.blaura.budgey.modules.tip.model.Tip;
import hu.blaura.budgey.modules.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {

}
