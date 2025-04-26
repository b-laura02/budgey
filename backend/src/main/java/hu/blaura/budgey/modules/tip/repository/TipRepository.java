package hu.blaura.budgey.modules.tip.repository;

import hu.blaura.budgey.modules.tip.model.Tip;
import hu.blaura.budgey.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {
    List<Tip> findByUserOrderByIsAiGenerated(User user);
}
